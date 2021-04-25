package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.adapters.AlphabetRecyclerViewAdapter;
import com.example.englishforkidsfinal.models.CarouselPageTransformer;

public class AlphabetFragment extends Fragment {

    private ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alphabet, container, false);

        viewPager2 = view.findViewById(R.id.viewPager2);

        AlphabetRecyclerViewAdapter adapter = new AlphabetRecyclerViewAdapter(getContext());

        viewPager2.setAdapter(adapter);

        viewPager2.setPageTransformer(new CarouselPageTransformer());

        return view;
    }
}