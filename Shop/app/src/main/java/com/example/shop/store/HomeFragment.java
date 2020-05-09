package com.example.shop.store;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView homeFragmentRecyclerView;
    private List<HomeFragmentdata> homeListArray = new ArrayList<>();
    HomeFragmentAdapter homeFragmentAdapter;
    ProgressBar progressBar;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        homeFragmentRecyclerView = view.findViewById(R.id.home_recycler_view);
        progressBar = view.findViewById(R.id.suggestion_progress);
        homeListArray=new ArrayList<>();
        homeFragmentAdapter= new HomeFragmentAdapter(getContext(),homeListArray);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        homeFragmentRecyclerView.setLayoutManager(mLayoutManager);
        homeFragmentRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        homeFragmentRecyclerView.setAdapter(homeFragmentAdapter);

//        String URL_LOADWILSUG="http://197.189.202.8:8080/courserv/wilFeatchSug.php";
//        final String stn = new User(getActivity()).getStnName();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                URL_LOADWILSUG,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
//                            int flagLgn = jsonObject.getInt("success");
//                            if(flagLgn==1){
//
//                                JSONArray jsonArray = jsonObject.getJSONArray("details");
//                                for (int i=0;i<jsonArray.length();i++){
//                                    JSONObject post=jsonArray.getJSONObject(i);
//                                    String event_id=post.getString("event_id");
//                                    String sender_ph=post.getString("sender_ph");
//                                    String carrier_ph=post.getString("carrier_ph");
//                                    String rec_ph=post.getString("reciver_ph");
//                                    String reciver_name=post.getString("reciver_name");
//                                    String ari_timestamp=post.getString("ari_timestamp");
//                                    String dep_timestamp=post.getString("dep_timestamp");
//                                    String status=post.getString("status");
//                                    String progress=post.getString("progress");
//                                    String otp=post.getString("otp");
//                                    String packet_id=post.getString("packet_id");
//
//                                    HomeFragmentdata homeFragmentdata = new HomeFragmentdata("NDLS", "HWH", dep_timestamp, "12:00", ari_timestamp, "00:12", "12311", packet_id, progress);
//                                    homeListArray.add(homeFragmentdata);
//                                    homeFragmentAdapter.notifyDataSetChanged();
//
//                                    if(!homeListArray.isEmpty()){
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//
//
//                                    //Toast.makeText(context,pnr +" "+train_no+" "+name+" "+coach_type+" "+seat_no+" "+travel_fare+" "+arival_date+" "+arival_time+" "+departure_date+" "+departure_time+" "+to_stn+" "+frm_stn+" "+travel_status+" "+to_stn_code+" "+from_stn_code,Toast.LENGTH_SHORT).show();
//
//                                }
//                                //Toast.makeText(getApplicationContext(),details,Toast.LENGTH_SHORT).show();
//
//                            }else {
////                                Toast.makeText(getActivity(), "Oops! something went wrong.", Toast.LENGTH_LONG).show();
//                            }
//
//                        }catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("stn", stn);
//
//
//                return params;
//            }
//        };
//        RequestQueue req = (RequestQueue) Volley.newRequestQueue(getActivity());
//        req.add(stringRequest);
//


//        homeDataArray();
        return view;
    }

//    private void homeDataArray() {
//        HomeFragmentdata homeFragmentdata = new HomeFragmentdata("NDLS", "HWH", "01/01/19", "12:00",
//                "01/01/19", "00:12", "12311", "123456", "pending");
//        homeListArray.add(homeFragmentdata);
//
//        homeFragmentdata = new HomeFragmentdata("NDLS", "MMCT", "01/01/19", "12:00",
//                "01/01/19", "00:12", "12322", "123456", "delivered");
//        homeListArray.add(homeFragmentdata);
//
//        homeFragmentdata = new HomeFragmentdata("NDLS", "HWH", "01/01/19", "12:00",
//                "01/01/19", "00:12", "12322", "123456", "pending");
//        homeListArray.add(homeFragmentdata);
//
//        homeFragmentdata = new HomeFragmentdata("NDLS", "MMCT", "01/01/19", "12:00",
//                "01/01/19", "00:12", "12322", "123456", "delivered");
//        homeListArray.add(homeFragmentdata);
//
//        homeFragmentdata = new HomeFragmentdata("NDLS", "PNB", "01/01/19", "12:00",
//                "01/01/19", "00:12", "12322", "123456", "canceled");
//        homeListArray.add(homeFragmentdata);
//
//        homeFragmentAdapter.notifyDataSetChanged();
//    }
}
