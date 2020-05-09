package com.sumit.spid.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.local.CookiesAdapter;

public class Settings extends AppCompatActivity {

    Switch earningButton;
    CookiesAdapter cookiesAdapter;
    User user;
    String earningMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        earningButton = findViewById(R.id.earning_button);
        cookiesAdapter = new CookiesAdapter(Settings.this);
        user = new User(Settings.this);


    }

    @Override
    protected void onResume() {
        cookiesAdapter.openReadable();
        earningMode = cookiesAdapter.getProfileValue(user.getPhoneNumber(),getString(R.string.attributeCookiesCarrierStatus));
        cookiesAdapter.close();
        if(earningMode.equals("1")){
            earningButton.setChecked(true);
            earningButton.setText("On");
            earningButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cookiesAdapter.openWritable();
                    cookiesAdapter.updateProfileValue(getString(R.string.attributeCookiesCarrierStatus),"0",user.getPhoneNumber());
                    cookiesAdapter.close();
                    earningButton.setText("Off");
                }
            });
        }else{
            earningButton.setChecked(false);
            earningButton.setText("Off");
            earningButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cookiesAdapter.openWritable();
                    cookiesAdapter.updateProfileValue(getString(R.string.attributeCookiesCarrierStatus),"1",user.getPhoneNumber());
                    cookiesAdapter.close();
                    earningButton.setText("On");
                }
            });
        }
        super.onResume();
    }
}
