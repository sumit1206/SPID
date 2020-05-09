package com.sumit.spid.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.sumit.spid.MainActivity;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.local.CookiesAdapter;
import com.sumit.spid.databasesupport.remote.RemoteDataCheck;
import com.sumit.spid.databasesupport.remote.VolleyCallback;

import org.json.JSONException;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    private EditText editTextInputPhone,verifyEditText;
    ConstraintLayout getOtpLayout,verifyOtpLayout;
    Button getOtpBtn,verifyOtpBtn;
    String number,validNumber,phoneNumber;
    private String verificationId;
    private FirebaseAuth mAuth;
    ProgressDialog loading;
    LinearLayout viewMain;
    String country_code ="+91";
    User user;
    RemoteDataCheck remoteDataCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        user = new User(OtpActivity.this);
        remoteDataCheck = new RemoteDataCheck(OtpActivity.this);
        viewMain = findViewById(R.id.view);
        mAuth = FirebaseAuth.getInstance();

        loading = new ProgressDialog(this, R.style.ProgressDialog);
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            editTextInputPhone = findViewById(R.id.phone_input_verify);
            verifyEditText = findViewById(R.id.textOtp);
            getOtpLayout = findViewById(R.id.getOTPLayout);
            verifyOtpLayout = findViewById(R.id.verifyOTPLayout);
            getOtpBtn = findViewById(R.id.buttonGetOTP);
            verifyOtpBtn = findViewById(R.id.buttonVerify);
            verifyOtpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String code = verifyEditText.getText().toString().trim();

                    if (code.isEmpty() || code.length() < 6) {

                        verifyEditText.setError("Enter code...");
                        verifyEditText.requestFocus();
                        return;
                    }
                    verifyCode(code);
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    loading.show();
                }
            });

            editTextInputPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                    String code = country_code;
                    number = editTextInputPhone.getText().toString().trim();


                    if (number.isEmpty() || number.length() < 10) {
                        editTextInputPhone.setError("Valid number is required");
                        editTextInputPhone.requestFocus();
                        return;
                    }
                    phoneNumber = "+" + code + number;
                }
            });

            getOtpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    number = editTextInputPhone.getText().toString().trim();


                    if (number.length() != 10) {
                        editTextInputPhone.setError("Valid number is required");
                        editTextInputPhone.requestFocus();
                    } else {
                        validNumber = number;
                        phoneNumber = "+91"+number;
                        sendVerificationCode(phoneNumber);
                        Toast.makeText(getApplicationContext(), "Mobile Number"  + number, Toast.LENGTH_LONG).show();
                        getOtpLayout.setVisibility(View.GONE);
                        verifyOtpLayout.setVisibility(View.VISIBLE);
                        loading.show();
                    }
                }
            });

    }

    private void verifyCode(String code) {

        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithCredential(credential);
        }catch (Exception e){
            Toast.makeText(OtpActivity.this,"Wrong verification code" +e,Toast.LENGTH_LONG).show();
        }

    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),"Verification Complete",Toast.LENGTH_LONG).show();
                            //////////////////////////////////////////////////////////////////////////////////
                            loading.dismiss();

                            //////////////////////////////////////////////////////////////////////////////////
                            remoteDataCheck.allLogin(new VolleyCallback() {
                                @Override
                                public void onSuccess(Object result) {
                                    int flag = (int) result;
                                    if(flag == 1) {

                                        CookiesAdapter cookiesAdapter = new CookiesAdapter(OtpActivity.this);
                                        cookiesAdapter.createDatabase();
                                        cookiesAdapter.openWritable();
                                        cookiesAdapter.setNewUser(number,"","");
                                        cookiesAdapter.close();
                                        user.setPhoneNumber(number);


                                        Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        finish();
                                    }else if(flag == 0) {
                                        Intent intent = new Intent(OtpActivity.this, LoginByMail.class);
                                        intent.putExtra("phoneNumber",number);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        finish();
                                    }else if(flag == -1){
                                        Intent intent = new Intent(OtpActivity.this, SignupWithMail.class);
                                        intent.putExtra("phoneNumber",number);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Snackbar.make(viewMain,"An error occurred!",Snackbar.LENGTH_INDEFINITE).show();
                                    }

                                }

                                @Override
                                public void noDataFound() {
                                    Snackbar.make(viewMain," No data",Snackbar.LENGTH_INDEFINITE).show();
                                }

                                @Override
                                public void onCatch(JSONException e) {
                                    Snackbar.make(viewMain," catch login",Snackbar.LENGTH_INDEFINITE).show();
                                }

                                @Override
                                public void onError(VolleyError e) {
                                    Snackbar.make(viewMain,e.getMessage(),Snackbar.LENGTH_INDEFINITE).show();
                                }
                            }, number, "", "001");
                            /**check if remotely active*/
                            //TODO: 001 = remotely active check
                            } else {
                            loading.dismiss();
                            Snackbar.make(viewMain,"Invalid OTP!",Snackbar.LENGTH_INDEFINITE).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
//            Toast.makeText(getApplicationContext(),"message token"+s,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifyEditText.setText(code);
                verifyCode(code);
            }
            loading.dismiss();
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpActivity.this,"verification is not success"+ e.getMessage(), Toast.LENGTH_LONG).show();
            loading.dismiss();
        }
    };
    //    @Override
    public void onBackPressed() {
        Intent kill = new Intent(Intent.ACTION_MAIN);
        kill.addCategory(Intent.CATEGORY_HOME);
        kill.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(kill);
    }

    @Override
    protected void onResume() {
        user.checkInternetConnection(viewMain);
        super.onResume();
    }
}
