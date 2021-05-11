package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.models.Alphabet;
import com.example.englishforkidsfinal.models.AlphabetLetter;
import com.example.englishforkidsfinal.models.RestAlphabetLetter;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.view_models.SpeechImageView;

import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.LETTER;

public class AlphabetLetterFragment extends Fragment {

    // Declaration of variables
    private RestAlphabetLetter letter;
    private SpeechImageView iv_letter;
    private TextView letter_text_view, word;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initializing variables
        View v = inflater.inflate(R.layout.fragment_alphabet_letter, container, false);

        // Getting letter from arguments
        letter = (RestAlphabetLetter) getArguments().getSerializable(LETTER);

        // Initializing view
        iv_letter = v.findViewById(R.id.letter_picture);
        letter_text_view = v.findViewById(R.id.letter);
        word = v.findViewById(R.id.word);

        // Setting view up
        iv_letter.setAnimation(R.anim.letter_up_down);
        Tools.loadImageFromStorage(letter.getPicture_uri(), getContext(), iv_letter);
        iv_letter.setWordToSpeak(letter.getLetter() + " " + letter.getPicture_uri());
        letter_text_view.setTypeface(MainActivity.typeface);
        word.setTypeface(MainActivity.typeface);
        word.setText(letter.getPicture_uri().toUpperCase());
        letter_text_view.setText(letter.getLetter().toUpperCase() + " " + letter.getLetter().toLowerCase());

        return v;
    }
}