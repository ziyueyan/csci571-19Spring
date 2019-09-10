package com.example.yanziyue.productsearch;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailResults extends AppCompatActivity {

    private DetailsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;



    String title;
    String itemId;
    String shippingcost;
    String handling;
    private String detail_url;
    private String photo_url;
    private String similar_url;
    private RequestQueue mQueue;

    private InfoTabFragment infoTabFragment;
    private ShippingTabFragment shippingTabFragment;
    private PhotoTabFragment photoTabFragment;
    private SimilarTabFragment similarTabFragment;

    TextView noResult;
    ProgressBar progressBar;
    TextView progress_text;

    TabLayout tabLayout;

    public Bundle infoArgs;
    public Bundle shippingArgs;
    public Bundle photosArgs;
    public Bundle similarArgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_results);
        noResult = (TextView) findViewById(R.id.no_result);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progress_text = (TextView) findViewById(R.id.progresstext);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        itemId = intent.getStringExtra("itemId");
        shippingcost = intent.getStringExtra("shippingcost");
        handling = intent.getStringExtra("handling");

        mSectionsPagerAdapter = new DetailsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        infoTabFragment = new InfoTabFragment();
        shippingTabFragment = new ShippingTabFragment();
        photoTabFragment = new PhotoTabFragment();
        similarTabFragment = new SimilarTabFragment();

        infoArgs = new Bundle();
        shippingArgs = new Bundle();
        photosArgs = new Bundle();
        similarArgs = new Bundle();

        mQueue = Volley.newRequestQueue(this);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                //.authority("csci571-hw8-yzy.us-east-2.elasticbeanstalk.com:8081")
                //.encodedAuthority("10.0.2.2:8081")
                .encodedAuthority("HW9-android.us-east-2.elasticbeanstalk.com")
                .path("detail")
                .appendQueryParameter("itemId", itemId);
        detail_url = builder.build().toString();

        Uri.Builder builder2 = new Uri.Builder();
        builder2.scheme("http")
                //.authority("csci571-hw8-yzy.us-east-2.elasticbeanstalk.com:8081")
                //.encodedAuthority("10.0.2.2:8081")
                .encodedAuthority("HW9-android.us-east-2.elasticbeanstalk.com")
                .path("photo")
                .appendQueryParameter("q", title);
        photo_url = builder2.build().toString();

        Uri.Builder builder3 = new Uri.Builder();
        builder3.scheme("http")
                //.authority("csci571-hw8-yzy.us-east-2.elasticbeanstalk.com:8081")
                //.encodedAuthority("10.0.2.2:8081")
                .encodedAuthority("HW9-android.us-east-2.elasticbeanstalk.com")
                .path("similar")
                .appendQueryParameter("itemId", itemId);
        similar_url = builder3.build().toString();

        System.out.println(detail_url);
        System.out.println(photo_url);
        System.out.println(similar_url);

        search_detail();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        getSupportActionBar().setTitle(title);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), title+"was added to widh list", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        tab();
    }

    public void tab(){
        LinearLayout tabInfoLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
        TextView tabInfo = (TextView) tabInfoLayout.findViewById(R.id.mytab);
        tabInfo.setText("PRODUCT");
        tabInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.information_variant, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabInfo);

        LinearLayout tabShipLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
        TextView tabShip = (TextView) tabShipLayout.findViewById(R.id.mytab);
        tabShip.setText("SHIPPING");
        tabShip.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.truck_delivery,  0, 0);
        tabLayout.getTabAt(1).setCustomView(tabShip);

        LinearLayout tabPhotoLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
        TextView tabPhoto = (TextView) tabPhotoLayout.findViewById(R.id.mytab);
        tabPhoto.setText("PHOTOS");
        tabPhoto.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.google,  0, 0);
        tabLayout.getTabAt(2).setCustomView(tabPhoto);

        LinearLayout tabSimilarLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
        TextView tabSimilar = (TextView) tabSimilarLayout.findViewById(R.id.mytab);
        tabSimilar.setText("SIMILAR");
        tabSimilar.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.equal, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabSimilar);
    }


    public void search_detail(){
        JsonObjectRequest detailrequest = new JsonObjectRequest(Request.Method.GET, detail_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray pictureArray = response.getJSONArray("picture");
                            ArrayList<String>  pics = new ArrayList<>();
                            for(int i = 0; i < pictureArray.length(); i++) {
                                pics.add(pictureArray.getString(i));
                            }
                            JSONArray specArray = response.getJSONArray("spec");
                            ArrayList<String>  specs = new ArrayList<>();
                            for(int i = 0; i < specArray.length(); i++) {
                                specs.add(specArray.getString(i));
                            }
                            infoArgs.putStringArrayList("picture",pics);
                            infoArgs.putString("subtitle", response.getString("subtitle"));
                            infoArgs.putString("title", title);
                            infoArgs.putString("price", response.getString("price"));
                            infoArgs.putString("shippingcost",shippingcost);
                            infoArgs.putString("brand", response.getString("brand"));
                            infoArgs.putStringArrayList("spec",specs);
                            infoTabFragment.setArguments(infoArgs);
                            mSectionsPagerAdapter.addFragment(infoTabFragment, "PRODUCT");

                            shippingArgs.putString("store",response.getString("store"));
                            shippingArgs.putString("storeurl", response.getString("storeurl"));
                            shippingArgs.putString("star",response.getString("star"));
                            shippingArgs.putString("score",response.getString("score"));
                            shippingArgs.putString("popular",response.getString("popular"));
                            shippingArgs.putString("policy",response.getString("policy"));
                            shippingArgs.putString("within",response.getString("within"));
                            shippingArgs.putString("mode",response.getString("mode"));
                            shippingArgs.putString("shipby",response.getString("shipby"));
                            shippingArgs.putString("global",response.getString("global"));
                            shippingArgs.putString("condition",response.getString("condition"));
                            shippingArgs.putString("shippingcost",shippingcost);
                            shippingArgs.putString("handling",handling);
                            shippingTabFragment.setArguments(shippingArgs);
                            mSectionsPagerAdapter.addFragment(shippingTabFragment , "SHIPPING");
                            search_photo();

                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            progress_text.setVisibility(View.GONE);
                            noResult.setText("Error");
                            noResult.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                progress_text.setVisibility(View.GONE);
                noResult.setText("Error");
                noResult.setVisibility(View.VISIBLE);
                error.printStackTrace();
            }
        });

        mQueue.add(detailrequest);
    }

    public void search_photo(){
        JsonObjectRequest photorequest = new JsonObjectRequest(Request.Method.GET, photo_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray photoArray = response.getJSONArray("photo");
                            ArrayList<String>  photos = new ArrayList<>();
                            for(int i = 0; i < photoArray.length(); i++) {
                                photos.add(photoArray.getString(i));
                            }
                            photosArgs.putStringArrayList("photos",photos);
                            photoTabFragment.setArguments(photosArgs);
                            mSectionsPagerAdapter.addFragment(photoTabFragment, "PHOTOS");
                            search_similar();

                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            progress_text.setVisibility(View.GONE);
                            noResult.setText("Error");
                            noResult.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                progress_text.setVisibility(View.GONE);
                noResult.setText("Error");
                noResult.setVisibility(View.VISIBLE);
                error.printStackTrace();
            }
        });

        mQueue.add(photorequest);

    }

    public void search_similar(){
        JsonObjectRequest similarrequest = new JsonObjectRequest(Request.Method.GET, similar_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray similarArray = response.getJSONArray("similar");
                            ArrayList<SimilarItem> similarItemArrayList = new ArrayList<>();
                            for(int i = 0; i < similarArray.length(); i++) {
                                JSONObject obj = similarArray.getJSONObject(i);
                                SimilarItem item = new SimilarItem(obj.getString("image"),obj.getString("url"), obj.getString("title"), obj.getString("shippingcost"),obj.getString("price"),obj.getString("time"));
                                similarItemArrayList.add(item);
                            }
                            similarArgs.putSerializable("similarList", similarItemArrayList);
                            similarTabFragment.setArguments(similarArgs);
                            mSectionsPagerAdapter.addFragment(similarTabFragment, "SIMILAR");
                            mViewPager.setAdapter(mSectionsPagerAdapter);
                            tab();
                            progressBar.setVisibility(View.GONE);
                            progress_text.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            progress_text.setVisibility(View.GONE);
                            noResult.setText("Error");
                            noResult.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                progress_text.setVisibility(View.GONE);
                noResult.setText("Error");
                noResult.setVisibility(View.VISIBLE);
                error.printStackTrace();
            }
        });

        mQueue.add(similarrequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class DetailsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public DetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "PRODUCT";
            }
            else if(position == 1) {
                return "SHIPPING";
            }
            else if(position == 2) {
                return "PHOTOS";
            }
            else {
                return "SIMILAR";
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
