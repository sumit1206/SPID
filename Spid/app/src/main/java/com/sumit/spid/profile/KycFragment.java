package com.sumit.spid.profile;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sumit.spid.MainActivity;
import com.sumit.spid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KycFragment extends Fragment {

    Button continueButton;

    public KycFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view=inflater.inflate(R.layout.fragment_kyc, container, false);

        continueButton = view.findViewById(R.id.continue_send_parcel_kyc);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent continueIntent = new Intent (getContext(), MainActivity.class);
                startActivity(continueIntent);
            }
        });
        return view;

    }


}
