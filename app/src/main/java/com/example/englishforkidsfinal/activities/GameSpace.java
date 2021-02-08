package com.example.englishforkidsfinal.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.DataBase;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.models.LettersView;
import com.example.englishforkidsfinal.models.Word;
import com.example.englishforkidsfinal.models.TargetView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameSpace extends AppCompatActivity {

    private TargetView targetView;
    private LettersView lettersView;
    private ImageView iv;
    private BackgroundMusic music;
    private List<Integer> tracks;
    private DataBase db;

    public List<Word> words = Arrays.asList(
            new Word(0, "dog", "собака", R.drawable.dog),
            new Word( 0, "pig", "свинья", R.drawable.pig),
            new Word(0, "frog", "лягушка", R.drawable.frog)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_space);

        db = new DataBase(this);

        tracks = Arrays.asList(R.raw.first, R.raw.second, R.raw.third);

        music = new BackgroundMusic(tracks, this);
        music.start();

        targetView = (TargetView) findViewById(R.id.ll_target);
        lettersView = (LettersView) findViewById(R.id.ll_letters);
        iv = (ImageView) findViewById(R.id.iv);

        shuffle();

        if (db.isInDB(words.get(0))) {
            targetView.setBackgroundColor(Color.GREEN);
        } else {
            targetView.setBackgroundColor(Color.RED);
        }


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

    public void add() {
        db.add(words.get(0));
    }

    @Override
    protected void onStart() {
        super.onStart();
        music.resumeMusic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        music.pause();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}