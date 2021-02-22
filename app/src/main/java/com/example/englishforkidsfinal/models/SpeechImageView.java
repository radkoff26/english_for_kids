package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.R;

import java.util.Locale;

public class SpeechImageView extends androidx.appcompat.widget.AppCompatImageView {

    private String wordToSpeak;
    private TextToSpeech tts;
    private int anim = -1;

    public SpeechImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.tts = new TextToSpeech(context, status -> {});
        this.tts.setLanguage(Locale.UK);
        this.setOnClickListener(v -> {
            if (wordToSpeak != null) {
                tts.speak(wordToSpeak, TextToSpeech.QUEUE_ADD, null);
            }
            if (anim != -1) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), anim);
                startAnimation(animation);
            }
        });
    }

    public void setWordToSpeak(String wordToSpeak) {
        this.wordToSpeak = wordToSpeak;
    }
    public void setAnimation(int anim) {
        this.anim = anim;
    }
}
