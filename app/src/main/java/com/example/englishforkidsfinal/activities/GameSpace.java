package com.example.englishforkidsfinal.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.LettersView;
import com.example.englishforkidsfinal.models.Model;
import com.example.englishforkidsfinal.models.TargetView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameSpace extends AppCompatActivity {

    private TargetView targetView;
    private LettersView lettersView;
    private ImageView iv;

    public List<Model> words = Arrays.asList(
            new Model("dog", R.drawable.dog),
            new Model("pig", R.drawable.pig),
            new Model("frog", R.drawable.frog)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetView = (TargetView) findViewById(R.id.ll_target);
        lettersView = (LettersView) findViewById(R.id.ll_letters);
        iv = (ImageView) findViewById(R.id.iv);

        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(words);

        targetView.startSettings(this, words.get(0).getAnimal());
        lettersView.startSettings(words.get(0).getAnimal(), targetView);

        iv.setImageResource(words.get(0).getRes());
    }

    public void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}