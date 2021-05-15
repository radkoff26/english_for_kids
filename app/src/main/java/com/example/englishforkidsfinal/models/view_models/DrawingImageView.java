package com.example.englishforkidsfinal.models.view_models;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class DrawingImageView extends AppCompatImageView {

    private boolean isPainted = false;
    private int paintedId, unpaintedId;
    private String word;

    public DrawingImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setPaintedId(int paintedId) {
        this.paintedId = paintedId;
    }

    public void setUnpaintedId(int unpaintedId) {
        this.unpaintedId = unpaintedId;
    }

    public boolean isPainted() {
        return isPainted;
    }

    public String getWord() {
        return word;
    }

    public void setPainted(boolean painted) {
        isPainted = painted;
    }

    public void change() {
        if (isPainted) {
            setImageResource(unpaintedId);
        } else {
            setImageResource(paintedId);
        }
        isPainted = !isPainted;
    }
}
