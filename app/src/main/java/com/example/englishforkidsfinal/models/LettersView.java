package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.R;

import java.util.ArrayList;
import java.util.Collections;

public class LettersView extends LinearLayout {

    // Declaration of variables
    private String word;
    private TargetView targetView;
    private ArrayList<Letter> letters;

    // Constructor
    public LettersView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // Method to set up default adjustments
    public void startSettings(String word, TargetView targetView) {
        removeAllViews();
        this.word = word.toLowerCase();
        this.targetView = targetView;

        setOrientation(LinearLayout.HORIZONTAL);

        int length = word.length() + 3;

        letters = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            letters.add(new Letter(word.charAt(i)));
        }

        Collections.shuffle(letters);

        for (int i = 0; i < letters.size(); i++) {
            letters.get(i).setIndex(i);
        }

        TextView tv;

        for (int i = 0; i < letters.size(); i++) {

            tv = new TextView(getContext());
            addView(tv, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setText(letters.get(i).getLetter().toString().toUpperCase());
            tv.setTextSize(35f);
            tv.setTextColor(getResources().getColor(R.color.font_color));
            tv.setPadding(0, 0, 25, 0);

            int finalI = i;
            tv.setOnClickListener(v -> {
                if (!((TextView) v).getText().toString().isEmpty()) {
                    targetView.addLetter(letters.get(finalI), (TextView) v);
                }
            });
        }
    }

    public void returnLetter(Letter letter) {
        ((TextView) getChildAt(letter.getIndex())).setText(letter.getLetter().toString().toUpperCase());
    }
}
