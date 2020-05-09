package com.sumit.spid.explorestores;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumit.spid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreShopInMapsFragment extends Fragment {


    public ExploreShopInMapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore_shop_in_maps, container, false);
    }

}
