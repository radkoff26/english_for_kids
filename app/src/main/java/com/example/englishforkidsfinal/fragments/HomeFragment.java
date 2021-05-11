package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.AllWordsDataBase;
import com.example.englishforkidsfinal.db.BigAnimalDatabase;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.db_models.BigAnimal;
import com.example.englishforkidsfinal.models.view_models.SpeechImageView;

import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    // Declaration of variable
    private SpeechImageView animal;
    private ImageView bg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialization of main view
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initializing Text-to-Speech Image view
        animal = view.findViewById(R.id.animal);
        bg = view.findViewById(R.id.bg);

        BigAnimalDatabase db = new BigAnimalDatabase(getContext());

        if (db.isEmpty()) {
            // Setting view up
            animal.setWordToSpeak("giraffe");
            bg.setImageResource(R.drawable.bg_portrait);
        } else {
            List<BigAnimal> bigAnimals = db.getBigAnimals();
            Collections.shuffle(bigAnimals);
            Tools.loadImageFromStorage(bigAnimals.get(0).getUri(), getContext(), animal);
            Tools.loadImageFromStorage(bigAnimals.get(0).getUri_bg(), getContext(), bg);
            animal.setWordToSpeak(bigAnimals.get(0).getWord());
        }

        animal.setAnimation(R.anim.animal_scale);

        return view;
    }
}