package com.example.englishforkidsfinal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;

import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST_GROUP;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST_GROUP_DEFAULT;

public class ContestFragment extends Fragment {

    // Declaration of variables
    private AppCompatButton try_it;
    private SharedPreferences sp;
    private int gr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initializing variables
        View view = inflater.inflate(R.layout.fragment_contest, container, false);
        sp = getActivity().getSharedPreferences(CACHE_CONTEST, Context.MODE_PRIVATE);
        gr = sp.getInt(CACHE_CONTEST_GROUP, CACHE_CONTEST_GROUP_DEFAULT);

        // Initializing view
        try_it = view.findViewById(R.id.try_it);

        try_it.setTypeface(MainActivity.typeface);

        // Setting OnClickListener to the button to transact to MainContestFragment
        try_it.setOnClickListener(v -> {
            if (gr < 3) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment, new MainContestFragment())
                        .commit();
            }
        });

        return view;
    }
}