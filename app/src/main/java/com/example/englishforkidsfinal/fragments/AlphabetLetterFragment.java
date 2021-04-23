package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.Alphabet;
import com.example.englishforkidsfinal.models.AlphabetLetter;
import com.example.englishforkidsfinal.models.view_models.SpeechImageView;

public class AlphabetLetterFragment extends Fragment {

    // Declaration of variables
    private String letter;
    private AlphabetLetter alphabetLetter;
    private SpeechImageView iv_letter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initializing variables
        View v = inflater.inflate(R.layout.fragment_alphabet_letter, container, false);

        // Getting letter from arguments
        letter = getArguments().getString("letter");

        // Getting alphabet letter by given letter
        alphabetLetter = Alphabet.getByWord(letter);

        // Initializing view
        iv_letter = v.findViewById(R.id.letter_picture);

        // Setting view up
        iv_letter.setAnimation(alphabetLetter.getAnim());
        iv_letter.setImageResource(alphabetLetter.getRes());
        iv_letter.setWordToSpeak(letter);

        return v;
    }
}