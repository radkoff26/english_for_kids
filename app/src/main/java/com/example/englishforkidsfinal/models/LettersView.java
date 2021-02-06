package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class LettersView extends LinearLayout {
    private String word;
    private TargetView targetView;
    private ArrayList<Letter> letters;

    public LettersView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void startSettings(String word, TargetView targetView) {
        this.word = word;
        this.targetView = targetView;

        setOrientation(LinearLayout.HORIZONTAL);

        int length = word.length() + 3;

        letters = new ArrayList<Letter>();

        for (int i = 0; i < length; i++) {
            if (i < word.length()) {
                letters.add(new Letter(word.charAt(i)));
            } else {
                letters.add(new Letter());
            }
        }

        Collections.shuffle(letters);

        for (int i = 0; i < letters.size(); i++) {
            letters.get(i).setIndex(i);
        }

        TextView tv;

        for (int i = 0; i < letters.size(); i++) {

            tv = new TextView(getContext());
            addView(tv, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setText(letters.get(i).getLetter().toString());
            tv.setTextSize(35f);
            tv.setPadding(0, 0, 25, 0);

            int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!((TextView) v).getText().toString().isEmpty()) {
                        targetView.addLetter(letters.get(finalI), (TextView) v);
                    }
                }
            });
        }
    }

    public void returnLetter(Letter letter) {
        ((TextView) getChildAt(letter.getIndex())).setText(letter.getLetter().toString());
    }
}
