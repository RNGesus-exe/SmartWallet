package com.rngesus.smartwallet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {

    private ArrayList<Slider>sliderModellist;

    public SliderAdapter(ArrayList<Slider> sliderModellist) {
        this.sliderModellist = sliderModellist;
    }

    @Override
    public int getCount() {
        return sliderModellist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout,container,false);
        ImageView imageView=view.findViewById(R.id.bannerslider);
        imageView.setImageResource(sliderModellist.get(position).getBanner());
        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
