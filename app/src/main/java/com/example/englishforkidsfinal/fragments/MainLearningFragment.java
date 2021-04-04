package com.example.englishforkidsfinal.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.AllWordsDataBase;
import com.example.englishforkidsfinal.models.OnSwipeTouchListener;
import com.example.englishforkidsfinal.models.SpeechImageView;
import com.example.englishforkidsfinal.models.TestModels;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.db_models.Word;

import java.util.List;

import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST_GROUP;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST_GROUP_DEFAULT;

public class MainLearningFragment extends Fragment {

    // Declaration of variables
    private ImageView picture;
    private SpeechImageView repeat;
    private LinearLayout ll;
    private Word currentModel;
    private TextView eng;
    private TextView ru;
    private SharedPreferences sp;
    private int gr;
    private AllWordsDataBase db;
    private List<Word> words;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialization of main view
        View v = inflater.inflate(R.layout.fragment_main_learning, container, false);

        // Initializing view
        picture = v.findViewById(R.id.picture);
        repeat = v.findViewById(R.id.repeat);
        eng = v.findViewById(R.id.eng);
        ru = v.findViewById(R.id.ru);
        ll = v.findViewById(R.id.ll_to_swipe);

        db = new AllWordsDataBase(getContext());

        sp = getActivity().getSharedPreferences(CACHE_CONTEST, Context.MODE_PRIVATE);

        gr = sp.getInt(CACHE_CONTEST_GROUP, CACHE_CONTEST_GROUP_DEFAULT);

        words = db.getWords(gr);

        // Invoking method to turn the next word up
        next();

        // Setting OnClickListener to the Linear Layout (main layout of fragment) to change words by swiping it left
        ll.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                next();
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        db.close();
        super.onDestroyView();
    }

    // Method to change the word
    public void next() {
        currentModel = getRandomWord(words);

        Tools.LoadImageFromWebOperations(currentModel.getUrl(), picture);

        eng.setText(currentModel.getEng().toUpperCase());
        ru.setText(currentModel.getRu().toUpperCase());

        repeat.setWordToSpeak(currentModel.getEng());
    }

    private Word getRandomWord(List<Word> words) {
        return words.get((int) (Math.random() * words.size()));
    }
}