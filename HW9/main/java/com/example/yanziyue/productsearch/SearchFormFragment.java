package com.example.yanziyue.productsearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SearchFormFragment extends Fragment {
    private static final String Tag = "SearchForm";

    EditText keyword, miles, zipcode;
    Spinner category;
    CheckBox New, Used, Unspecified, Local, Free, enable;
    TextView keyword_err, zipcode_err;
    RadioGroup radioGroup;
    RadioButton radioButton, here, other;
    Button search, clear;
    RelativeLayout nearby_search;

    String keyword_s, zipcode_s;

    public SearchFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search_form, container, false);

        keyword = (EditText) view.findViewById(R.id.Keyword);
        keyword_err = (TextView) view.findViewById(R.id.keyword_error);
        category = (Spinner) view.findViewById(R.id.Category);
        New = (CheckBox) view.findViewById(R.id.New);
        Used = (CheckBox) view.findViewById(R.id.Used);
        Unspecified = (CheckBox) view.findViewById(R.id.Unspecified);
        Local = (CheckBox) view.findViewById(R.id.Local);
        Free = (CheckBox) view.findViewById(R.id.Free);

        enable = (CheckBox) view.findViewById(R.id.nearby);
        nearby_search = (RelativeLayout) view.findViewById(R.id.nearby_search);
        miles = (EditText) view.findViewById(R.id.miles);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio);
        here = (RadioButton) view.findViewById(R.id.here);
        other = (RadioButton) view.findViewById(R.id.other);
        zipcode = (EditText) view.findViewById(R.id.zipcode);
        zipcode_err = (TextView) view.findViewById(R.id.zip_error);

        search = (Button) view.findViewById(R.id.searchBut);
        clear = (Button) view.findViewById(R.id.clearBut);

        enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    System.out.println("nearby search enabled");
                    nearby_search.setVisibility(View.VISIBLE);
                }
                else {
                    System.out.println("nearby search disabled");
                    nearby_search.setVisibility(View.GONE);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioButton = (RadioButton) radioGroup.findViewById(i);
                String butS = radioButton.getText().toString();

                if(butS.equals("Current Location")){
                    System.out.println("zipcode disabled");
                    zipcode.setEnabled(false);
                    zipcode.setText("");
                }
                else {
                    System.out.println("zipcode enabled");
                    zipcode.setEnabled(true);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(view);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword.setText("");
                category.setSelection(0);
                New.setChecked(false);
                Used.setChecked(false);
                Unspecified.setChecked(false);
                Local.setChecked(false);
                Free.setChecked(false);
                enable.setChecked(false);
                miles.setText("");
                here.setChecked(true);
                other.setChecked(false);
                zipcode.setEnabled(false);
                zipcode.setText("");
            }
        });

        return view;
    }

    public void search(View view){
        boolean flag = false;
        keyword_s = keyword.getText().toString();
        String key_pattern = "\\s*\\S.*";
        if(!keyword_s.matches(key_pattern)) {
            flag = true;
            keyword_err.setVisibility(View.VISIBLE);
        }
        else {
            keyword_err.setVisibility(View.GONE);
        }
        if(other.isChecked()){
            String zip_pattern = "^\\d{5}$";
            zipcode_s = zipcode.getText().toString();
            if(!zipcode_s.matches(zip_pattern)){
                flag = true;
                zipcode_err.setVisibility(View.VISIBLE);
            }
            else {
                zipcode_err.setVisibility(View.GONE);
            }
        }
        else{
            zipcode_err.setVisibility(View.GONE);
        }

        if(flag) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Please fix all fields with errors", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            System.out.println("valid form submit");
            Intent search_result = new Intent(SearchFormFragment.this.getActivity(), SearchResults.class);
            search_result.putExtra("keyword", keyword.getText().toString());
            search_result.putExtra("category", category.getSelectedItem().toString());
            search_result.putExtra("New", New.isChecked());
            search_result.putExtra("Used", Used.isChecked());
            search_result.putExtra("Unspecified", Unspecified.isChecked());
            search_result.putExtra("Local", Local.isChecked());
            search_result.putExtra("Free", Free.isChecked());
            search_result.putExtra("enable", enable.isChecked());
            search_result.putExtra("miles", miles.getText().toString());
            search_result.putExtra("here", here.isChecked());
            search_result.putExtra("other", other.isChecked());
            search_result.putExtra("zipcode", zipcode.getText().toString());

            startActivity(search_result);


        }
    }













}
