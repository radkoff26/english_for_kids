package com.example.englishforkidsfinal.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.adapters.GamesRecyclerViewAdapter;
import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.CarouselPageTransformer;
import com.google.android.material.slider.Slider;

public class GamesFragment extends Fragment {

    // Declaration of variables
    private ViewPager2 viewPager2;
    private Slider slider;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initializing variables
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        viewPager2 = view.findViewById(R.id.viewPager2);
        slider = view.findViewById(R.id.slider);

        GamesRecyclerViewAdapter adapter = new GamesRecyclerViewAdapter(getContext());

        viewPager2.setAdapter(adapter);

        viewPager2.setPageTransformer(new CarouselPageTransformer());

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                slider.setValue(position);
                super.onPageSelected(position);
            }
        });

        slider.addOnChangeListener((slider, value, fromUser) -> {
            viewPager2.setCurrentItem((int) value, true);
        });

        return view;
    }
}