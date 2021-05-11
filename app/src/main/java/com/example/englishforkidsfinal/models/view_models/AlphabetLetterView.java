package com.example.englishforkidsfinal.models.view_models;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.models.RestAlphabetLetter;

public class AlphabetLetterView extends androidx.appcompat.widget.AppCompatImageView {

    private RestAlphabetLetter letter;

    public AlphabetLetterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RestAlphabetLetter getLetter() {
        return letter;
    }

    public void setLetter(RestAlphabetLetter letter) {
        this.letter = letter;
    }
}
