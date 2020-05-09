package com.example.shop.store.more;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.shop.BugReport;
import com.example.shop.ContactUs;
import com.example.shop.R;
import com.example.shop.SelectingActivity;
import com.example.shop.sinch.PlaceCallActivity;
import com.example.shop.store.StoreTransaction.StoreTransaction;
import com.example.shop.store.StoreTransaction.StoreTransactionItemDetails;
import com.example.shop.store.loginregister.LoginActivity;
import com.example.shop.store.loginregister.WheelerRegistration;
import com.example.shop.store.profile.ProfileWheeler;
import com.example.shop.store.StoreUser;
import com.example.shop.store.StoreWallet.StoreWallet;
import com.example.shop.store.remote.DbHelper;
import com.example.shop.store.remote.VolleyCallbackStore;

import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    Activity mainActivity;
    TextView bugReport,logoutButton,view_profile_button,my_wallet,myHistory,contact_company;
    Switch isOpenSwitch;
    StoreUser storeUser;
    ProgressDialog loading;

    public MoreFragment(Activity mainActivity) {
        this.mainActivity = mainActivity;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_more_wheeler,container,false);

        storeUser = new StoreUser(getContext());
        bugReport = view.findViewById(R.id.report);
        isOpenSwitch = view.findViewById(R.id.is_open_switch);
        logoutButton = view.findViewById(R.id.store_logout_button);
        my_wallet = view.findViewById(R.id.my_Wallets);
        contact_company = view.findViewById(R.id.contact_company);
        contact_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactIntent = new Intent(getContext(), ContactUs.class);
                startActivity(contactIntent);
            }
        });

//        myHistory = view.findViewById(R.id.my_history);
//        myHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent transactionIntent = new Intent (getContext(), StoreTransaction.class);
//                startActivity(transactionIntent);
//            }
//        });

        /**progress dialog initialization*/
        loading = new ProgressDialog(getContext(), R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        my_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent walletIntent = new Intent(getContext(), StoreWallet.class);
                startActivity(walletIntent);
            }
        });

        if(storeUser.isOpen()){
            isOpenSwitch.setChecked(true);
        }

        bugReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BugReport(getContext(),"").sendEmail();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new StoreUser(getActivity()).removeUser();
                Intent logoutSinch = new Intent (getContext(), PlaceCallActivity.class);
                logoutSinch.putExtra("logout","1");
                Log.println(Log.ASSERT, "SINCH:", "Requesting sinch logout");
                startActivity(logoutSinch);
                mainActivity.finish();
            }
        });

        view_profile_button = view.findViewById(R.id.view_profile_text);
        view_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity(), ProfileWheeler.class);
                startActivity(intent);
            }
        });

        isOpenSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpenSwitch.isChecked()){
                    setAcceptingStatus("1");
//                    storeUser.setOpen();
                }else{
                    setAcceptingStatus("0");
//                    storeUser.setClose();
                }
            }
        });

        return view;
    }

    void setAcceptingStatus(final String status){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        loading.show();
        new DbHelper(getContext()).setAcceptingStatus(new VolleyCallbackStore() {
            @Override
            public void onSuccess(Object result) {
                if(status.equals("0")){
                    storeUser.setClose();
                    alertDialog.setTitle("Store Close");
                    alertDialog.setMessage("Store Close successfully");
                }else if(status.equals("1")){
                    storeUser.setOpen();
                    alertDialog.setTitle("Store Open");
                    alertDialog.setMessage("Store open successfully");
                }
                loading.dismiss();
                alertDialog.show();

//                Toast.makeText(getContext(),"Done",Toast.LENGTH_LONG).show();
            }

            @Override
            public void noDataFound() {
                if(status.equals("0")){
                    isOpenSwitch.setChecked(true);
                }else if(status.equals("1")){
                    isOpenSwitch.setChecked(false);
                }
                loading.dismiss();
                alertDialog.setTitle("Failed");
                alertDialog.setMessage("Status updating failed");
                alertDialog.show();
            }

            @Override
            public void onCatch(JSONException e) {
                if(status.equals("0")){
                    isOpenSwitch.setChecked(true);
                }else if(status.equals("1")){
                    isOpenSwitch.setChecked(false);
                }
                loading.dismiss();
                alertDialog.setTitle("Failed");
                alertDialog.setMessage("Status updating failed");
                alertDialog.show();
            }

            @Override
            public void onError(VolleyError e) {
                if(status.equals("0")){
                    isOpenSwitch.setChecked(true);
                }else if(status.equals("1")){
                    isOpenSwitch.setChecked(false);
                }
                loading.dismiss();
                alertDialog.setTitle("Failed");
                alertDialog.setMessage("Status updating failed");
                alertDialog.show();
            }
        },"043",storeUser.getPhone(),status);
    }
}
