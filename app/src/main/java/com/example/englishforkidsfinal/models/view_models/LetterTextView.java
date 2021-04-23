package com.example.englishforkidsfinal.models.view_models;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.Letter;

// Plain Old Java Object
public class LetterTextView extends AppCompatTextView {

    private Letter letter;

    public LetterTextView(Context context) {
        super(context);
        setTextColor(getResources().getColor(R.color.font_color));
    }

    public Letter getLetter() {
        return this.letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }
}
