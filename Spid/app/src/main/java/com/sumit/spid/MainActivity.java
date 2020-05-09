package com.sumit.spid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.sumit.spid.contactus.ContactUs;
import com.sumit.spid.databasesupport.local.CookiesAdapter;
import com.sumit.spid.faq.FaqActivity;
import com.sumit.spid.history.History;
import com.sumit.spid.home.Home;
import com.sumit.spid.login.OtpActivity;
import com.sumit.spid.mydelivery.MyDeliveries;
import com.sumit.spid.notification.Notification;
import com.sumit.spid.profile.ProfileActivity;
import com.sumit.spid.search.SearchActivity;
import com.sumit.spid.sinch.PlaceCallActivity;
import com.sumit.spid.wallet.Wallet;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    User user;
    DrawerLayout viewMain;
    TextView profile_activity_name_layout,navigationName;
    ImageView navigationImage;
    CookiesAdapter cookiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);


        user = new User(MainActivity.this);
        cookiesAdapter = new CookiesAdapter(MainActivity.this);
        viewMain = findViewById(R.id.drawer_layout);

        new PushNotification(MainActivity.this);

        loadFragment(new Home());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        profile_activity_name_layout = headerview. findViewById(R.id.profile_activity_name_layout);
        navigationName = headerview.findViewById(R.id.navigation_name);
        navigationImage = headerview.findViewById(R.id.navigation_image_view);

        Log.println(Log.ASSERT,"spid started","----------------------");
//        new SinchInitialize(MainActivity.this,"");

        profile_activity_name_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(profileIntent);
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.send_icon) {

            Intent search_intent = new Intent(MainActivity.this,SearchActivity.class);
            startActivity(search_intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

        @Override
        public boolean onNavigationItemSelected (MenuItem item)
        {
            int id = item.getItemId();

            switch (id){
                case R.id.nav_wallet:
                    Intent walletIntent = new Intent(MainActivity.this, Wallet.class);
                    startActivity(walletIntent);
                    break;

//                case R.id.nav_earning:
//                    break;

                case R.id.nav_history:
                    Intent historyIntent = new Intent(MainActivity.this, History.class);
                    startActivity(historyIntent);
                    break;

//                case R.id.nav_review:
//                    break;
//
//                case R.id.nav_settings:
//                    Intent settingsIntent = new Intent(MainActivity.this, Settings.class);
//                    startActivity(settingsIntent);
//                    break;
                case R.id.nav_faq:
                    Intent faqIntent = new Intent(MainActivity.this, FaqActivity.class);
                    startActivity(faqIntent);
                    break;
                case R.id.nav_share:
                    new BugReport(this).shareApp();
                    break;
                case R.id.nav_help:
                    Intent helpIntent = new Intent(MainActivity.this, ContactUs.class);
                    startActivity(helpIntent);
                    break;
                case R.id.nav_report:
                    new BugReport(this).sendEmail();
                    break;
                case R.id.nav_suggestion:
                    new BugReport(this).sendEmail();
                    break;
                case  R.id.legal:
                    break;
                case R.id.privacy:
                    break;
                case R.id.nav_logout:
                    final AlertDialog.Builder conformationDialog = new AlertDialog.Builder(MainActivity.this);
//                    conformationDialog.setCancelable(true);
                    conformationDialog.setMessage("Logout will result in re-verification of phone number!");
                    conformationDialog.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            logout();
                        }
                    });
                    conformationDialog.setNegativeButton("Stay in app", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface,int i) {

                        }
                    });
                    conformationDialog.show();
                    break;
                default:
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

            }
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //toolbar.setTitle("Home");
                    fragment = new Home();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notification:
                    //toolbar.setTitle("MoreFragment");
                    fragment = new Notification();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_my_deliveries:
                    //  toolbar.setTitle("History");
                    fragment = new MyDeliveries();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    //Loading home fragment
    private void loadFragment(Fragment fragment) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    void logout(){
        Intent sinchLogoutIntent = new Intent(MainActivity.this, PlaceCallActivity.class);
        sinchLogoutIntent.putExtra("logout","");
        startActivity(sinchLogoutIntent);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient;
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent logoutIntent = new Intent(MainActivity.this, OtpActivity.class);
                        user.removeUser();
                        startActivity(logoutIntent);
                    }
                });
    }

    protected void onResume()
    {
        user.checkInternetConnection(viewMain);
        cookiesAdapter.openReadable();
        String name,photoString;
        name = cookiesAdapter.getProfileValue(user.getPhoneNumber(),getString(R.string.attributeCookiesName));
        photoString = cookiesAdapter.getProfileValue(user.getPhoneNumber(),getString(R.string.attributeCookiesPhoto));
        cookiesAdapter.close();
        if(name != null){
            String n[] = name.split(" ");
            name = n[0];
            navigationName.setText(name);}
        if(photoString != null){
            ParseImage parseImage = new ParseImage(navigationImage);
            parseImage.setImageString(photoString);
        }
        super.onResume();
    }
}
