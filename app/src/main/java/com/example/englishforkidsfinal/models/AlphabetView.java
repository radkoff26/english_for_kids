package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.fragments.AlphabetLetterFragment;

import java.util.List;

import static android.content.Context.WINDOW_SERVICE;

public class AlphabetView extends TableLayout {

    // Initializing variables
    private List<String> alphabet = Alphabet.getUpperAlphabet();
    private static final int ROWS = 5;

    // Constructor
    public AlphabetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Applying adjustments to the Alphabet view when inflating is finishing
        setOrientation(VERTICAL);
        TextView tv;
        LinearLayout ll;
        float y = Resources.getSystem().getDisplayMetrics().heightPixels * 0.25f;
        float textSize = ((float) y)/6;

        // Initializing number of letters inserted
        int w = 0;

        // Adding all views to Alphabet View
        for (int i = 0; i < alphabet.size() / ROWS; i++) {
            // Initializing and setting up Linear Layout that contains letters
            ll = new LinearLayout(getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));

            for (int j = 0; j < ROWS; j++) {
                // Initializing and setting up Text View that contains letter
                tv = new TextView(getContext());
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(textSize);
                tv.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
                tv.setText(alphabet.get(w));

                // Setting OnClickListener to the letter to transact to AlphabetLetterFragment
                TextView finalTv = tv;
                tv.setOnClickListener(v -> {
                    transact(finalTv);
                });

                // Incrementing number of added letters
                w++;

                // Adding Text View to Linear Layout
                ll.addView(tv);
            }

            // Adding Linear Layout to Alphabet View
            addView(ll);
        }

        // The same scheme of adding with left letters
        ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
        while (w < 26) {
            tv = new TextView(getContext());
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(textSize);
            tv.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
            tv.setText(alphabet.get(w));

            TextView finalTv = tv;
            tv.setOnClickListener(v -> {
                transact(finalTv);
            });

            w++;

            ll.addView(tv);
        }
        addView(ll);
    }

    // Method to transact to AlphabetLetterFragment with letter argument
    private void transact(TextView tv) {
        AlphabetLetterFragment fragment = new AlphabetLetterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("letter", tv.getText().toString());
        fragment.setArguments(bundle);
        ((MainActivity) getContext())
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, fragment)
                .commit();
    }
}
