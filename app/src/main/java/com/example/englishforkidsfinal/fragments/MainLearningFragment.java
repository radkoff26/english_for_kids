package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.SpeechImageView;
import com.example.englishforkidsfinal.models.TestModels;
import com.example.englishforkidsfinal.models.Word;

import java.util.Locale;

public class MainLearningFragment extends Fragment {

    private ImageView picture;
    private SpeechImageView repeat;
    private AppCompatButton next;
    private Word currentModel;
    private TextView eng;
    private TextView ru;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_learning, container, false);

        picture = v.findViewById(R.id.picture);
        repeat = v.findViewById(R.id.repeat);
        next = v.findViewById(R.id.next);
        eng = v.findViewById(R.id.eng);
        ru = v.findViewById(R.id.ru);

        next();

        next.setOnClickListener(view -> {
            next();
        });

        return v;
    }

    public void next() {
        currentModel = TestModels.randomWord();

        picture.setImageResource(currentModel.getRes());

        eng.setText(currentModel.getAnimal());
        ru.setText(currentModel.getTranslation());

        repeat.setWordToSpeak(currentModel.getAnimal());
    }
}