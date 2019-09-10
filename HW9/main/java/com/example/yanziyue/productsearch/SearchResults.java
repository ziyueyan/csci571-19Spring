package com.example.yanziyue.productsearch;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {

    private String search_url;
    private RequestQueue mQueue;
    private List<Item> Items;

    TextView noResult;
    TextView result_num;
    String keyword;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar progressBar;
    private TextView progress_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        result_num = (TextView) findViewById(R.id.result_num);
        noResult = (TextView) findViewById(R.id.no_result);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progress_text = (TextView) findViewById(R.id.progresstext);

        mQueue = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        Items = new ArrayList<>();
        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        String category = intent.getStringExtra("category");
        Boolean New = intent.getBooleanExtra("New", false);
        Boolean Used = intent.getBooleanExtra("Used", false);
        Boolean Unspecified = intent.getBooleanExtra("Unspecified", false);
        Boolean Local = intent.getBooleanExtra("Local", false);
        Boolean Free = intent.getBooleanExtra("Free", false);
        Boolean enable = intent.getBooleanExtra("enable", false);
        String miles = intent.getStringExtra("miles");
        Boolean here = intent.getBooleanExtra("here", false);
        Boolean other = intent.getBooleanExtra("other", false);
        String zipcode = intent.getStringExtra("zipcode");

        String categoryId = "-1";
        if(category.equals("Art")) categoryId = "550";
        else if(category.equals("Baby")) categoryId = "2984";
        else if(category.equals("Books")) categoryId = "267";
        else if(category.equals("Clothing, Shoes & Accessories")) categoryId = "11450";
        else if(category.equals("Computers, Tablets & Networking")) categoryId = "58058";
        else if(category.equals("Health & Beauty")) categoryId = "26395";
        else if(category.equals("Music")) categoryId = "11233";
        else if(category.equals("Video Games & Consoles")) categoryId = "1249";
        if(enable){
            //get zip
            if(miles.equals("")) miles = "10";
            if(here) zipcode = getZip();
        }

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                //.authority("csci571-hw8-yzy.us-east-2.elasticbeanstalk.com:8081")
                .encodedAuthority("HW9-android.us-east-2.elasticbeanstalk.com")
                .path("search")
                .appendQueryParameter("keyword", keyword)
                .appendQueryParameter("category", categoryId)
                .appendQueryParameter("zipcode",zipcode)
                .appendQueryParameter("miles", miles)
                .appendQueryParameter("localpickup", Local.toString())
                .appendQueryParameter("freeshipping", Free.toString())
                .appendQueryParameter("new", New.toString())
                .appendQueryParameter("used", Used.toString())
                .appendQueryParameter("unspecified", Unspecified.toString());
        search_url = builder.build().toString();
        System.out.println(search_url);
        search_result();

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public String getZip(){

        //TODO
        return "90007";
    }

    public void search_result(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, search_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("items");
                            if(jsonArray.length() == 0) {
                                noResult.setVisibility(View.VISIBLE);
                            }
                            else {
                                noResult.setVisibility(View.GONE);
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Item item = new Item(obj.getString("image"),obj.getString("title"), obj.getString("zipcode"),obj.getString("shippingcost"),obj.getString("handling"),obj.getString("condition"),obj.getString("price"),obj.getString("itemId"));
                                    Items.add(item);
                                }
                                String title = "<font>Showing </font><font color=#ff5100>"+ jsonArray.length() +"</font><font> results for </font><font color=#ff5100>" + keyword + "</font>";
                                result_num.setText(Html.fromHtml(title));
                                mAdapter = new SearchResultAdapter(getApplicationContext(), Items);
                                recyclerView.setAdapter(mAdapter);
                            }
                            progressBar.setVisibility(View.GONE);
                            progress_text.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            progress_text.setVisibility(View.GONE);
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

        mQueue.add(request);

    }

}
