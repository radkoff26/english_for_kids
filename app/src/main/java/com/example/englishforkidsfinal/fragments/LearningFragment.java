package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.LearnedWordsDataBase;
import com.example.englishforkidsfinal.models.db_models.Word;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LearningFragment extends Fragment {

    // Declaration of variable
    private MaterialButton start, test;
    private LearnedWordsDataBase db;
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialization of main view
        View v = inflater.inflate(R.layout.fragment_learning, container, false);

        // Initializing view
        start = v.findViewById(R.id.learn);
        test = v.findViewById(R.id.test);
        tv = v.findViewById(R.id.number_of_words);

        db = new LearnedWordsDataBase(getContext());

        List<Word> words = db.getWords();

        tv.setText("Выучено: " + words.size() + " слов");

        test.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment, new MainContestFragment()).commit();
        });

        // Setting OnClickListener to the button to transact to MainLearningFragment
        start.setOnClickListener(v1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment, new MainLearningFragment()).commit();
        });

        return v;
    }

    @Override
    public void onPause() {
        db.close();
        super.onPause();
    }
}