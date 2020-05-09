package com.sumit.spid.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.sumit.spid.MainActivity;
import com.sumit.spid.ParseImage;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.local.CookiesAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    View mIndicator;
    private int indicatorWidth;
    private ImageView cover;
    private ImageView photo;
    private TextView name_user;
    CookiesAdapter cookies;
    User user;
    String nameString,photoString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        photo = findViewById(R.id.photo);
        cover = findViewById(R.id.cover);
        name_user = findViewById(R.id.profile_activity_name);
        cookies = new CookiesAdapter(ProfileActivity.this);
        user = new User(ProfileActivity.this);
        nameString = "";
        loadProfileData();


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Profile");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Toast.makeText(ProfileActivity.this, "On Create Called", Toast.LENGTH_SHORT).show();
        mIndicator = findViewById(R.id.indicator);

        viewPager = findViewById(R.id.viewPager);
        setupViewPager();

        tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {

                indicatorWidth = tabLayout.getWidth() / tabLayout.getTabCount();
                //Assign new width
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                mIndicator.setLayoutParams(indicatorParams);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset =  (positionOffset+position) * indicatorWidth ;
                params.leftMargin = (int) translationOffset;
                mIndicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Profile Photo clicked", Toast.LENGTH_SHORT).show();
            }
        });

//        collapsing Toolbar
//        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);

//        try {
//            Bitmap bitmap = ((BitmapDrawable) photo.getDrawable()).getBitmap();
//            Toast.makeText(ProfileActivity.this, "On try", Toast.LENGTH_SHORT).show();
//            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//                @SuppressWarnings("ResourceType")
//                @Override
//                public void onGenerated(Palette palette) {
//                    int vibrantColor = palette.getVibrantColor(R.color.colorPrimary);
//                    int vibrantDarkColor = palette.getDarkVibrantColor(R.color.colorPrimaryDark);
//                    collapsingToolbarLayout.setContentScrimColor(vibrantColor);
//                    collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
//                }
//            });
//
//        } catch (Exception e) {
//            // if Bitmap fetch fails, fallback to primary colors
//            Toast.makeText(ProfileActivity.this, "On Catch", Toast.LENGTH_SHORT).show();
//            collapsingToolbarLayout.setContentScrimColor(
//                    ContextCompat.getColor(this, R.color.colorPrimary)
//            );
//            collapsingToolbarLayout.setStatusBarScrimColor(
//                    ContextCompat.getColor(this, R.color.colorPrimaryDark)
//            );
//        }

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setupViewPager()
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ProfileActivityPersonalFragment(), "Personal");
        adapter.addFrag(new ProfileActivityBankDetailsFragment(), "Account");
        adapter.addFrag(new KycFragment(), "kYC");
        viewPager.setAdapter(adapter);
    }

    void loadProfileData(){
        cookies.openReadable();
        nameString = cookies.getProfileValue(user.getPhoneNumber(),"Name");
        photoString = cookies.getProfileValue(user.getPhoneNumber(),"Photo");
        cookies.close();
        name_user.setText(nameString);
        if(photoString != null){
            ParseImage parsePhotoImage = new ParseImage(photo);
            parsePhotoImage.setImageString(photoString);
            ParseImage parseCoverImage = new ParseImage(cover);
            parseCoverImage.setImageString(photoString);
        }
        Toast.makeText(ProfileActivity.this,"Name :"+nameString,Toast.LENGTH_LONG).show();
//        Log.println(Log.ERROR,"NAME",nameString);
        Toast.makeText(ProfileActivity.this,"Nametest",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
    }
}
