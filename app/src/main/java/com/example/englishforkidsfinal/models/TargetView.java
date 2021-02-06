package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.GameSpace;

public class TargetView extends LinearLayout {

    private String word;
    private Context ctx;

    public TargetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void startSettings(Context context, String word) {
        this.word = word;
        ctx = context;
        setOrientation(LinearLayout.HORIZONTAL);

        LetterTextView tv;
        for (int i = 0; i < word.trim().length(); i++) {
            tv = new LetterTextView(getContext());
            addView(tv, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setTextSize(50f);
            tv.setText("_");
            tv.setPadding(0, 0, 20, 0);

            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!((LetterTextView) v).getText().toString().equals("_")) {
                        ((LettersView) ((GameSpace) ctx).findViewById(R.id.ll_letters))
                                .returnLetter(((LetterTextView) v).getLetter());
                        ((LetterTextView) v).setText("_");
                        ((LetterTextView) v).setLetter(null);
                    }
                }
            });
        }
    }

    public void addLetter(Letter letter, TextView v) {
        boolean f = false;
        String s = "";
        LetterTextView tv;

        for (int i = 0; i < getChildCount(); i++) {
            tv = (LetterTextView) getChildAt(i);
            if (tv.getText().toString().trim().equals("_") && !f) {
                tv.setText(letter.getLetter().toString());
                tv.setLetter(letter);
                v.setText("");
                f = true;
            }
        }

        for (int i = 0; i < getChildCount(); i++) {
            s += ((LetterTextView) getChildAt(i)).getText().toString();
        }

        if (word.toLowerCase().equals(s)) {
            ((GameSpace) ctx).restart();
        }
    }
}
