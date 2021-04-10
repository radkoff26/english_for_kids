package com.example.englishforkidsfinal.fragments;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.AllWordsDataBase;
import com.example.englishforkidsfinal.models.SpeechImageView;
import com.example.englishforkidsfinal.models.Tools;
import com.squareup.picasso.Picasso;

import java.io.File;

public class HomeFragment extends Fragment {

    // Declaration of variable
    private SpeechImageView animal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialization of main view
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initializing Text-to-Speech Image view
        animal = view.findViewById(R.id.animal);

        AllWordsDataBase allWordsDB = new AllWordsDataBase(getContext());

        // Setting view up
        animal.setWordToSpeak("giraffe");
        animal.setAnimation(R.anim.animal_scale);

        return view;
    }
}