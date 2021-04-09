package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.db.LearnedWordsDataBase;
import com.example.englishforkidsfinal.models.db_models.Word;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class LearningFragment extends Fragment {

    // Declaration of variable
    private MaterialButton start, test;
    private LearnedWordsDataBase db;
    private TextView tv, add_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialization of main view
        View v = inflater.inflate(R.layout.fragment_learning, container, false);

        // Initializing view
        start = v.findViewById(R.id.learn);
        test = v.findViewById(R.id.test);
        tv = v.findViewById(R.id.number_of_words);
        add_tv = v.findViewById(R.id.add_tv);

        start.setTypeface(MainActivity.typeface);
        test.setTypeface(MainActivity.typeface);
        tv.setTypeface(MainActivity.typeface);
        add_tv.setTypeface(MainActivity.typeface);

        db = new LearnedWordsDataBase(getContext());

        List<Word> words = db.getWords();

        tv.setText(getResources().getString(R.string.amount_of_words, words.size()));

        test.setOnClickListener(view -> {
            if (words.size() < 10) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment, new MainContestFragment()).commit();
            }
        });

        // Setting OnClickListener to the button to transact to MainLearningFragment
        start.setOnClickListener(v1 -> {
            if (words.size() < 10) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment, new MainLearningFragment()).commit();
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        db.close();
        super.onDestroyView();
    }
}