package com.sumit.spid.profile;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumit.spid.R;
import com.sumit.spid.profile.adapter.ProfilePersonalDetailsAdapter;
import com.sumit.spid.profile.profiledata.ProfileDetailsData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileActivityPersonalFragment extends Fragment {

    private List<ProfileDetailsData> profileDetailsList;
    private RecyclerView profileRecyclerView;
    private ProfilePersonalDetailsAdapter profileDetailsAdapter;
    public ProfileActivityPersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_activity_personal,container,false);
        profileRecyclerView = view.findViewById(R.id.profile_details_recycler_view);
        profileDetailsList = new ArrayList<>();
        profileDetailsAdapter = new ProfilePersonalDetailsAdapter(getContext(),profileDetailsList);
        return view;
    }

    private void prepareProfileData()
    {
        ProfileDetailsData profileDetailsData;

        //mobile  number
        profileDetailsData = new ProfileDetailsData("Add number",getString(R.string.attributeCookiesMobileNo),R.drawable.smartphone,"Phone_number");
        profileDetailsList.add(profileDetailsData);

        //maiil id
        profileDetailsData = new ProfileDetailsData("Add email id",getString(R.string.attributeCookiesEmailId),R.drawable.mail,"Mail_id");
        profileDetailsList.add(profileDetailsData);

//        address
        profileDetailsData = new ProfileDetailsData("Add address",getString(R.string.attributeCookiesAddress),R.drawable.address_pin,"Address");
        profileDetailsList.add(profileDetailsData);

//        city
        profileDetailsData = new ProfileDetailsData("Add city",getString(R.string.attributeCookiesCity),R.drawable.city,"City");
        profileDetailsList.add(profileDetailsData);

//        state
        profileDetailsData = new ProfileDetailsData("Add state",getString(R.string.attributeCookiesState),R.drawable.home,"State");
        profileDetailsList.add(profileDetailsData);

//        gender
//        profileDetailsData = new ProfileDetailsData("Add gender","Gender",R.drawable.gender,"Gender");
//        profileDetailsList.add(profileDetailsData);

//        aadhar number
        profileDetailsData = new ProfileDetailsData("Add aadhar number",getString(R.string.attributeCookiesAadharNumber),R.drawable.credit_card,"Aadhar_number");
        profileDetailsList.add(profileDetailsData);
        profileDetailsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        profileDetailsList.clear();
        profileDetailsAdapter = new ProfilePersonalDetailsAdapter(getContext(),profileDetailsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        profileRecyclerView.setLayoutManager(layoutManager);
        profileRecyclerView.setAdapter(profileDetailsAdapter);
        profileRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        prepareProfileData();
        super.onResume();
    }
}
