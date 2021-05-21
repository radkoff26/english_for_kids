package com.example.englishforkidsfinal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.db.AllWordsDataBase;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.db_models.Word;

import java.util.Collections;
import java.util.List;

import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.RESULT;
import static com.example.englishforkidsfinal.models.contractions.CacheContractions.*;

public class MainContestFragment extends Fragment {

    // Declaration of variables
    private ImageView picture;
    private AppCompatButton w1, w2;
    private List<Word> mWords;
    private SharedPreferences sp;
    private int group, right, wrong, current = 0, score;
    private AllWordsDataBase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialization of main view
        View view = inflater.inflate(R.layout.fragment_main_contest, container, false);

        // Initializing score by zero to increment it in case of right answers
        score = 0;

        // Initializing SharedPreferences and its Editor
        sp = getActivity().getSharedPreferences(CACHE_CONTEST, Context.MODE_PRIVATE);
        group = sp.getInt(CACHE_CONTEST_GROUP, CACHE_CONTEST_GROUP_DEFAULT);
        group = group == 0 ? 1 : group;

        db = new AllWordsDataBase(getContext());

        // Initializing list of words
        mWords = db.getWords(group);

        // Randomizing list of words
        Collections.shuffle(mWords);

        // Initializing views
        picture = view.findViewById(R.id.picture);
        w1 = view.findViewById(R.id.w1);
        w2 = view.findViewById(R.id.w2);

        w1.setTypeface(MainActivity.typeface);
        w2.setTypeface(MainActivity.typeface);

        // Invoking method to turn the next couple of words
        next();

        return view;
    }

    // Method to get random index from 0 to 1
    private int randomIndexFrom0To1() {
        double res = Math.random();
        if (res > 0.5) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void onDestroyView() {
        db.close();
        super.onDestroyView();
    }

    // Method to turn the next couple of words
    private void next() {
        // Getting random wrong word
        Word wrongWord = getWrong();

        // Getting index of right word to be appeared in fragment with
        right = randomIndexFrom0To1();

        // Getting index of wrong word to be appeared in fragment with
        wrong = 1 - right;

        // Setting resources to views
        Tools.loadImageFromStorage(mWords.get(current).getEng(), getContext(), picture);

        if (right == 0) {
            w1.setText(mWords.get(current).getEng());
            w2.setText(wrongWord.getEng());
        } else {
            w1.setText(wrongWord.getEng());
            w2.setText(mWords.get(current).getEng());
        }

        // Setting OnClickListeners to the buttons
        w1.setOnClickListener(v -> {
            // If answer is right, score is being increased
            if (right == 0) {
                score++;
            }

            // If the word is last, it is being transacted to ResultContestFragment with score in arguments
            if (current == mWords.size() - 1) {
                ResultContestFragment fragment = new ResultContestFragment();

                Bundle args = new Bundle();
                args.putInt(RESULT, score);
                fragment.setArguments(args);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment, fragment)
                        .commit();
            } else {
                // Otherwise, current index of right word will be increased and the next couple of words will be turned up
                current++;
                next();
            }
        });
        w2.setOnClickListener(v -> {
            // If answer is right, score is being increased
            if (right == 1) {
                score++;
            }

            // If the word is last, it is being transacted to ResultContestFragment with score in arguments
            if (current == mWords.size() - 1) {
                ResultContestFragment fragment = new ResultContestFragment();

                Bundle args = new Bundle();
                args.putInt(RESULT, score);
                fragment.setArguments(args);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment, fragment)
                        .commit();
            } else {
                // Otherwise, current index of right word will be increased and the next couple of words will be turned up
                current++;
                next();
            }
        });
    }

    // Method to randomize index of word
    private int randomIndex(int start, int end) {
        return (int) (start + (Math.random() * (end - start)));
    }

    // Method to get wrong word
    private Word getWrong() {
        int i = randomIndex(0, mWords.size());
        while (i == current) {
            i = randomIndex(0, mWords.size());
        }
        return mWords.get(i);
    }

}