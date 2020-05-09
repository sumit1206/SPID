package com.sumit.spid.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sumit.spid.MainActivity;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.local.CookiesAdapter;

public class LoginByMail extends AppCompatActivity {

    TextView dontHaveAccount;
    Button login;
    User user;
    String phoneNumberString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_mail);
        dontHaveAccount = findViewById(R.id.dont_have_account);
        phoneNumberString = getIntent().getExtras().getString("phoneNumber");
        login = findViewById(R.id.login_by_mail);
        user = new User(LoginByMail.this);

        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginByMail.this,SignupWithMail.class);
                intent.putExtra("phoneNumber",phoneNumberString);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CookiesAdapter cookiesAdapter = new CookiesAdapter(LoginByMail.this);
                cookiesAdapter.createDatabase();
                cookiesAdapter.openWritable();
                cookiesAdapter.setNewUser(phoneNumberString,"","");
                cookiesAdapter.close();
                user.setPhoneNumber(phoneNumberString);

                Intent intent = new Intent(LoginByMail.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
