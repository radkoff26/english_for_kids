package com.example.englishforkidsfinal.models.view_models;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.CollectWord;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.models.Letter;
import com.example.englishforkidsfinal.models.view_models.LetterTextView;
import com.example.englishforkidsfinal.models.view_models.LettersView;

public class TargetView extends LinearLayout {

    // Declaration of variables
    private String word;
    private Context ctx;

    // Constructor
    public TargetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // Method to set start adjustments
    public void startSettings(Context context, String word) {
        removeAllViews();
        this.word = word.toLowerCase();
        ctx = context;
        setOrientation(LinearLayout.HORIZONTAL);

        LetterTextView tv;
        for (int i = 0; i < word.trim().length(); i++) {
            tv = new LetterTextView(getContext());
            addView(tv, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setTextSize(50f);
            tv.setTextColor(getResources().getColor(R.color.font_color));
            tv.setText("_");
            tv.setTypeface(MainActivity.typeface);
            tv.setPadding(0, 0, 20, 0);

            tv.setOnClickListener(v -> {
                if (!((LetterTextView) v).getText().toString().equals("_")) {
                    ((LettersView) ((CollectWord) ctx).findViewById(R.id.ll_letters))
                            .returnLetter(((LetterTextView) v).getLetter());
                    ((LetterTextView) v).setText("_");
                    ((LetterTextView) v).setTypeface(MainActivity.typeface);
                    ((LetterTextView) v).setLetter(null);
                }
            });
        }
    }

    // Method to add letter to the view
    public void addLetter(Letter letter, TextView v) {
        boolean f = false;
        String s = "";
        LetterTextView tv;

        for (int i = 0; i < getChildCount(); i++) {
            tv = (LetterTextView) getChildAt(i);
            if (tv.getText().toString().trim().equals("_") && !f) {
                tv.setText(letter.getLetter().toString().toUpperCase());
                tv.setLetter(letter);
                tv.setTypeface(MainActivity.typeface);
                tv.setTextColor(getResources().getColor(R.color.font_color));
                v.setText("");
                f = true;
            }
        }

        for (int i = 0; i < getChildCount(); i++) {
            s += ((LetterTextView) getChildAt(i)).getText().toString();
        }

        if (word.toLowerCase().equals(s.toLowerCase())) {
            ((CollectWord) ctx).next(true);
        }
    }
}
