package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.OnSwipeTouchListener;
import com.example.englishforkidsfinal.models.SpeechImageView;
import com.example.englishforkidsfinal.models.TestModels;
import com.example.englishforkidsfinal.models.Word;

public class MainLearningFragment extends Fragment {

    // Declaration of variables
    private ImageView picture;
    private SpeechImageView repeat;
    private LinearLayout ll;
    private Word currentModel;
    private TextView eng;
    private TextView ru;

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

    // Method to change the word
    public void next() {
        currentModel = TestModels.randomWord();

        picture.setImageResource(currentModel.getRes());

        eng.setText(currentModel.getAnimal());
        ru.setText(currentModel.getTranslation());

        repeat.setWordToSpeak(currentModel.getAnimal());
    }
}