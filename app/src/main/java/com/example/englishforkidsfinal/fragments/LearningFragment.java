package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.englishforkidsfinal.R;

public class LearningFragment extends Fragment {

    // Declaration of variable
    private ImageButton start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialization of main view
        View v = inflater.inflate(R.layout.fragment_learning, container, false);

        // Initializing view
        start = v.findViewById(R.id.start_learning);

        // Setting OnClickListener to the button to transact to MainLearningFragment
        start.setOnClickListener(v1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment, new MainLearningFragment()).commit();
        });

        return v;
    }
}