package com.example.shop.store;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.shop.PushNotification;
import com.example.shop.R;
import com.example.shop.SelectingActivity;
import com.example.shop.sinch.LoginActivity;
import com.example.shop.store.more.MoreFragment;
import com.example.shop.store.scanfunctionality.ScanFragment;
import com.example.shop.store.stock.ParcelStockFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.TextView;

public class StoreMainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    public static Context storeMainActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storeMainActivityContext = this;
        new PushNotification(StoreMainActivity.this,new StoreUser(StoreMainActivity.this).getPhone());


        Intent callIntent = new Intent(StoreMainActivity.this, LoginActivity.class);
        callIntent.putExtra("extraNumber","");
        callIntent.putExtra("myExtraNumber",new StoreUser(StoreMainActivity.this).getPhone());
        startActivity(callIntent);

        //  loadFragment(new HomeFragment());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    new ScanFragment()).commit();
        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId())
            {
                case R.id.navigation_scan:
                    selectedFragment = new ScanFragment();
                    break;
                case R.id.navigation_stock:
                   selectedFragment=new ParcelStockFragment();
                   break;

//                case R.id.navigation_home:
//                    selectedFragment = new HomeFragment();
//                    break;

                case R.id.navigation_more_options:
                  selectedFragment = new MoreFragment(StoreMainActivity.this);
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
            return true;
        }
    };


}
