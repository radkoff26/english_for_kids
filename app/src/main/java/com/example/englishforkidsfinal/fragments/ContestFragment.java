package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.R;

public class ContestFragment extends Fragment {

    // Declaration of variables
    private AppCompatButton try_it;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initializing variables
        View view = inflater.inflate(R.layout.fragment_contest, container, false);

        // Initializing view
        try_it = view.findViewById(R.id.try_it);

        // Setting OnClickListener to tha button to transact to MainContestFragment
        try_it.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new MainContestFragment())
                    .commit();
        });

        return view;
    }
}