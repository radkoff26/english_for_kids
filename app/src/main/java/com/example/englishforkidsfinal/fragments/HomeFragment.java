package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.models.SpeechImageView;

public class HomeFragment extends Fragment {

    private SpeechImageView animal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        animal = view.findViewById(R.id.animal);

        animal.setWordToSpeak("giraffe");

        return view;
    }
}