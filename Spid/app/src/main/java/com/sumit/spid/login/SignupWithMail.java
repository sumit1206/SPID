package com.sumit.spid.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sumit.spid.MainActivity;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.local.CookiesAdapter;
import com.sumit.spid.databasesupport.remote.RemoteDataCheck;
import com.sumit.spid.databasesupport.remote.VolleyCallback;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class SignupWithMail extends AppCompatActivity {

    EditText mailId;
    Button signUp;
    RemoteDataCheck remoteDataCheck;
    String mailIdString, phoneString, nameString, imageUrlString;
    User user;
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "GMAIL_SIGNIN";
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    CookiesAdapter cookiesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_with_mail);

        remoteDataCheck = new RemoteDataCheck(SignupWithMail.this);

//        mailId = findViewById(R.id.mail_id_input);
//        signUp = findViewById(R.id.sign_up_with_mail);


        phoneString = getIntent().getExtras().getString("phoneNumber");
        user = new User(SignupWithMail.this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                mailIdString = mailId.getText().toString().trim();
//            }
//        });
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(SignupWithMail.this,e.getMessage(),Toast.LENGTH_LONG).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"done..",Toast.LENGTH_LONG).show();
                            mailIdString = firebaseUser.getEmail();
                            nameString = firebaseUser.getDisplayName();
                            imageUrlString = firebaseUser.getPhotoUrl().toString();
                            newRegistrationProcess();
//                            startActivity(new Intent(MainActivity.this,Home.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"failed..",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    void newRegistrationProcess(){
        remoteDataCheck.allLogin(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                int flag = (int) result;
                if(flag == 0){
                    //mail id not exist
                    userRegistration("005");
                    /**insert new data*/
                    //TODO: 005 = new registration
                }else if(flag == 1){
                    //mail id exist
                    ///////////////////////////
                    //ask here for merging
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignupWithMail.this);
                    alertDialog.setTitle("Mail already exists");
                    alertDialog.setMessage("Do you want to merge your account?");
                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            userRegistration("004");
                            /**Merge two accounts*/
                            //TODO: 004 = merge two accounts
                        }
                    });
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            userRegistration("005");
                            /**insert new data*/
                            //TODO: 005 = new registration
                        }
                    });
                    alertDialog.show();
                    ///////////////////////////
                }else {
                    Toast.makeText(SignupWithMail.this,"An error occured",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {

            }

            @Override
            public void onCatch(JSONException e) {
                Toast.makeText(SignupWithMail.this,e.getMessage(),Toast.LENGTH_LONG);
            }

            @Override
            public void onError(VolleyError e) {
                Toast.makeText(SignupWithMail.this,e.getMessage(),Toast.LENGTH_LONG);
            }
        }, "", mailIdString, "003");
        /**check if mail existing*/
        //TODO: 003 = mail exist check
    }

    void userRegistration(String requestCode){
        remoteDataCheck.allLogin(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                int success = (int) result;
                if(success == 1){

                    cookiesAdapter = new CookiesAdapter(SignupWithMail.this);
                    cookiesAdapter.createDatabase();
                    cookiesAdapter.openWritable();
                    cookiesAdapter.setNewUser(phoneString,mailIdString,nameString);
                    cookiesAdapter.close();
                    user.setPhoneNumber(phoneString);
                    new Downloader().execute(imageUrlString);
                    startActivity(new Intent(SignupWithMail.this, MainActivity.class));
                }else {
                    Toast.makeText(SignupWithMail.this,"an error occurred",Toast.LENGTH_LONG);
                }
            }

            @Override
            public void noDataFound() {

            }

            @Override
            public void onCatch(JSONException e) {
                Toast.makeText(SignupWithMail.this,e.getMessage(),Toast.LENGTH_LONG);
            }

            @Override
            public void onError(VolleyError e) {
                Toast.makeText(SignupWithMail.this,e.getMessage(),Toast.LENGTH_LONG);
            }
        }, phoneString, mailIdString, requestCode);
    }

    class Downloader extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String src = params[0];
                java.net.URL url = new java.net.URL(src);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.WEBP,100,byteArrayOutputStream);
                byte[] imgBytes = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(imgBytes,Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            cookiesAdapter.openWritable();
            cookiesAdapter.updateProfileValue(getString(R.string.attributeCookiesPhoto),result,phoneString);
            cookiesAdapter.close();
            super.onPostExecute(result);
        }
    }


}
