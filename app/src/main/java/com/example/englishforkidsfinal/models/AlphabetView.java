package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class AlphabetView extends TableLayout {

    private List<String> alphabet = Alphabet.getUpperAlphabet();
    private static final int ROWS = 5;

    public AlphabetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOrientation(VERTICAL);
        TextView tv;
        LinearLayout ll;
        int w = 0;
        for (int i = 0; i < alphabet.size() / ROWS; i++) {
            ll = new LinearLayout(getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));

            for (int j = 0; j < ROWS; j++) {
                tv = new TextView(getContext());
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(50f);
                tv.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
                tv.setText(alphabet.get(w));

                w++;

                ll.addView(tv);
            }

            addView(ll);
        }
        ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
        while (w < 26) {
            tv = new TextView(getContext());
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(50f);
            tv.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
            tv.setText(alphabet.get(w));

            w++;

            ll.addView(tv);
        }
        addView(ll);
    }
}