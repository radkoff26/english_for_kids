package com.example.englishforkidsfinal.models;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;

public class LetterTextView extends AppCompatTextView {

    private Letter letter;

    public LetterTextView(Context context) {
        super(context);
    }

    public Letter getLetter() {
        return this.letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }
}
