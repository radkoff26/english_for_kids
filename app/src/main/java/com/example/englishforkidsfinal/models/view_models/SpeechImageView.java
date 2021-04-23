package com.example.englishforkidsfinal.models.view_models;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

public class SpeechImageView extends androidx.appcompat.widget.AppCompatImageView {

    // Declaration of variables
    private String wordToSpeak;
    private TextToSpeech tts;
    private int anim = -1;

    // Constructor
    public SpeechImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // Initialization and setting up of Text-to-Speech
        this.tts = new TextToSpeech(context, status -> {});
        this.tts.setLanguage(Locale.ENGLISH);

        // Setting OnClickListener to manage Text-to-Speech to speak
        this.setOnClickListener(v -> {
            // Checking if text is given
            if (wordToSpeak != null) {
                tts.speak(wordToSpeak, TextToSpeech.QUEUE_ADD, null);
            }

            // Checking if animation is given and managing it to animate image
            if (anim != -1) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), anim);
                startAnimation(animation);
            }
        });
    }

    // Method to set word to speak
    public void setWordToSpeak(String wordToSpeak) {
        this.wordToSpeak = wordToSpeak;
    }

    // Method to set animation
    public void setAnimation(int anim) {
        this.anim = anim;
    }
}
