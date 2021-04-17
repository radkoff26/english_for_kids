package com.example.englishforkidsfinal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.englishforkidsfinal.db.AllWordsDataBase;
import com.example.englishforkidsfinal.db.LearnedWordsDataBase;
import com.example.englishforkidsfinal.models.db_models.Word;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import static com.example.englishforkidsfinal.db.contractions.DBModelContractions.NUMBER_OF_WORDS_IN_GROUP;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST_GROUP;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST_GROUP_DEFAULT;

public class LearningFragment extends Fragment {

    // Declaration of variable
    private MaterialButton start, test, vocabulary;
    private AllWordsDataBase db;
    private TextView tv, add_tv;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialization of main view
        View v = inflater.inflate(R.layout.fragment_learning, container, false);

        sp = getActivity().getSharedPreferences(CACHE_CONTEST, Context.MODE_PRIVATE);

        // Initializing view
        start = v.findViewById(R.id.learn);
        test = v.findViewById(R.id.test);
        vocabulary = v.findViewById(R.id.vocabulary);
        tv = v.findViewById(R.id.number_of_words);
        add_tv = v.findViewById(R.id.add_tv);

        start.setTypeface(MainActivity.typeface);
        test.setTypeface(MainActivity.typeface);
        tv.setTypeface(MainActivity.typeface);
        add_tv.setTypeface(MainActivity.typeface);
        vocabulary.setTypeface(MainActivity.typeface);

        vocabulary.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new CategoriesFragment())
                    .commit();
            ((MainActivity) getActivity()).removeBesidesLast();
        });

        db = new AllWordsDataBase(getContext());

        List<Word> words = db.getWords(null);

        int level = sp.getInt(CACHE_CONTEST_GROUP, CACHE_CONTEST_GROUP_DEFAULT);
        level = level == 0 ? 1 : level;

        tv.setText(getResources().getString(R.string.amount_of_words, (level - 1) * NUMBER_OF_WORDS_IN_GROUP));

        int finalLevel = level;
        test.setOnClickListener(view -> {
            if (words.size() >= finalLevel * NUMBER_OF_WORDS_IN_GROUP) {
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment, new MainContestFragment()).commit();
            }
        });

        // Setting OnClickListener to the button to transact to MainLearningFragment
        start.setOnClickListener(v1 -> {
            if (words.size() >= finalLevel * NUMBER_OF_WORDS_IN_GROUP) {
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