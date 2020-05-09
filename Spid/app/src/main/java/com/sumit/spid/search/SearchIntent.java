package com.sumit.spid.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sumit.spid.R;

import java.util.ArrayList;
import java.util.List;

public class SearchIntent extends AppCompatActivity {

    SearchView searchWhereTo;
    ListView suggestionListView;
    ArrayList addressList;
    ArrayAdapter suggestionAdapter;
    String addressText;
    List<Address> addresses;
    RelativeLayout progressLayout;
    ProgressBar loading;
    RelativeLayout progressFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_search);
        searchWhereTo = findViewById(R.id.search_edit_text);
        suggestionListView = findViewById(R.id.where_to_list_view);
        progressLayout = findViewById(R.id.progress_layout);
        loading = findViewById(R.id.progress_circular);
        progressFailed = findViewById(R.id.progress_failed);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        addressList = new ArrayList();
        suggestionAdapter = new ArrayAdapter<String>(SearchIntent.this,android.R.layout.simple_list_item_1, addressList);
        suggestionListView.setAdapter(suggestionAdapter);
        addresses = null;

        searchWhereTo.requestFocus();
        searchWhereTo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                Toast.makeText(SearchIntent.this,"On submit"+s,Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                new Searcher().execute(s);
                return false;
            }
        });

        suggestionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(SearchActivity.this,String.valueOf(i),Toast.LENGTH_LONG).show();
                success(i);
            }
        });

    }

    class Searcher extends AsyncTask<String,Void,Void> {

//        Boolean isSuccess;

        @Override
        protected void onPreExecute() {
            progressFailed.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            addressList.clear();
            String g = params[0];
            Log.println(Log.VERBOSE,"In g:==================",g);

            Geocoder geocoder = new Geocoder(getBaseContext());

            try {
                // Getting a maximum of 3 Address that matches the input
                // text
                addresses = geocoder.getFromLocationName(g, 3);
                if (addresses != null && !addresses.equals("")) {
                    for (Address address : addresses) {
                        addressText = address.getAddressLine(0).trim();
                        Log.println(Log.VERBOSE,"In addressText:========",addressText);
                        addressList.add(addressText);
                    }
                }else {
                }

            } catch (Exception e) {
                Log.println(Log.ERROR,"Catch exception:",e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            loading.setVisibility(View.GONE);
            if(addressList.isEmpty()) {
                progressFailed.setVisibility(View.VISIBLE);
            }else {
                progressFailed.setVisibility(View.GONE);
            }
            suggestionAdapter.notifyDataSetChanged();
            super.onPostExecute(aVoid);
        }
    }

    void success(int i){
        Intent returnIntent = new Intent();
        Address a = addresses.get(i);
        returnIntent.putExtra("address",a);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}
