package com.sumit.spid.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sumit.spid.R;
import com.sumit.spid.home.HomeData.PagerData;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerDataAdapter extends PagerAdapter {

    public Context mContext ;
    private List<PagerData> viewPagerData ;



    public ViewPagerDataAdapter(Context context, List<PagerData> viewPagerData)
    {
        this.mContext = context;
        this.viewPagerData = viewPagerData;
    }

    @Override
    public int getCount()
    {
        return viewPagerData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_item_home,null);

//        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slide_item_home, container, false);

        ImageView slideImg = slideLayout.findViewById(R.id.slide_img);
        TextView slideText = slideLayout.findViewById(R.id.slide_title);
        slideImg.setImageResource(viewPagerData.get(position).getImage());
        slideText.setText(viewPagerData.get(position).getTitle());
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(slideLayout, 0);
        return slideLayout;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }


}
