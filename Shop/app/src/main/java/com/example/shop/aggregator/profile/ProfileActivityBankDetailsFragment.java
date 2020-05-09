package com.example.shop.aggregator.profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;
import com.example.shop.aggregator.profile.adapter.BankDetailsAdapter;
import com.example.shop.aggregator.profile.profiledata.BankDetailsData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileActivityBankDetailsFragment extends Fragment {


//    private List<BankDetailsData> bankDetailsList = new ArrayList<>();
    private RecyclerView bankRecyclerView;
    private BankDetailsAdapter bankDetailsAdapter;

    public ProfileActivityBankDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrier_bank, container, false);


//        bankRecyclerView = view.findViewById(R.id.bank_details_recycler_view);
//        bankDetailsAdapter = new BankDetailsAdapter(getContext(),bankDetailsList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        bankRecyclerView.setLayoutManager(layoutManager);
//        bankRecyclerView.setAdapter(bankDetailsAdapter);
//        prepareProfileBankData();
        return view;
    }


//    private void prepareProfileBankData() {
//        BankDetailsData bankDetailsData;
//        //Bank name
//        bankDetailsData = new BankDetailsData("Sumit mondal","Holder name",R.drawable.account_circle,R.drawable.add_circle_outline);
//        bankDetailsList.add(bankDetailsData);
//
//        //Bank name
//        bankDetailsData = new BankDetailsData("State Bank Of India","Bank name",R.drawable.account_box,R.drawable.add_circle_outline);
//        bankDetailsList.add(bankDetailsData);
//
//        //Bank account no
//        bankDetailsData = new BankDetailsData("11223408979", "Account no", 1, R.drawable.add_circle_outline);
//        bankDetailsList.add(bankDetailsData);
//
//        //IFSC No
//        bankDetailsData = new BankDetailsData("sbi6001", "IFSC no", 1, R.drawable.add_circle_outline);
//        bankDetailsList.add(bankDetailsData);
//
//        //branch name
//        bankDetailsData = new BankDetailsData("Delhi","Branch name",1,R.drawable.add_circle_outline);
//        bankDetailsList.add(bankDetailsData);
//
//        bankDetailsAdapter.notifyDataSetChanged();
//
//    }
}
