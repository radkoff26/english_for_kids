package com.example.englishforkidsfinal.models;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class CarouselPageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        float scaleFactor = 0.5f;
        float rotationFactor = 20;

        if (position < 0) {
            page.setRotationY(rotationFactor * -position);
            float scale = 1 + scaleFactor * position;
            page.setScaleX(scale);
            page.setScaleY(scale);

        } else {
            page.setRotationY(rotationFactor * -position);
            float scale = 1 - scaleFactor * position;
            page.setScaleX(scale);
            page.setScaleY(scale);
        }
    }
}