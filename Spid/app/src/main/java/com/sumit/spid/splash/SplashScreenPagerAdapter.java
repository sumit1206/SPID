package com.sumit.spid.splash;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.sumit.spid.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreenPagerAdapter extends PagerAdapter {
    Context mContext ;
    List<ScreenItem> mListScreen;

    public SplashScreenPagerAdapter(Context mContext, List<ScreenItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.splash_layout_screen,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(imgSlide);
        TextView title = layoutScreen.findViewById(R.id.intro_title);
        TextView description = layoutScreen.findViewById(R.id.intro_description);

        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getDescription());
        Glide.with(mContext).load(mListScreen.get(position).getScreenImg()).into(imageViewTarget);
        container.addView(layoutScreen);

        return layoutScreen;


    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);

    }
}
