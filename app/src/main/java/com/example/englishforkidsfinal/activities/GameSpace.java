package com.example.englishforkidsfinal.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.DataBase;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.models.LettersView;
import com.example.englishforkidsfinal.models.TargetView;
import com.example.englishforkidsfinal.models.Word;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GameSpace extends AppCompatActivity {

    // Declaration of variables
    private TargetView targetView;
    private LettersView lettersView;
    private ImageView iv;
    private BackgroundMusic music;
    private List<Integer> tracks;
    private DataBase db;
    private List<Word> words;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_space);

        // Initialization of Database
        db = new DataBase(this);

        // Receiving all words from Database
        words = db.getWords();

        // Checking if there are any learned words unless access won't be gained
        if (words.size() == 0) {
            Toast.makeText(getApplicationContext(), "You need to learn at least one word!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Background music
        // Tracks
        tracks = Arrays.asList(R.raw.first, R.raw.second, R.raw.third);
        // Music
        music = new BackgroundMusic(tracks, this);
        // Starting music
        music.start();

        // Initializing all views
        targetView = (TargetView) findViewById(R.id.ll_target);
        lettersView = (LettersView) findViewById(R.id.ll_letters);
        iv = (ImageView) findViewById(R.id.iv);

        // Randomizing words received from Database
        shuffle();
    }

    // Method to randomize words from Database and to set default adjustments
    public void shuffle() {
        Collections.shuffle(words);

        targetView.startSettings(this, words.get(0).getAnimal());
        lettersView.startSettings(words.get(0).getAnimal(), targetView);

        iv.setImageResource(words.get(0).getRes());
    }

    // Method to restart activity and to randomize words
    public void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        // Resuming background music if activity wasn't destroyed
        music.resumeMusic();
        super.onStart();
    }

    @Override
    protected void onStop() {
        // Stopping if activity is going to be stopped
        music.pause();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // Closing Database when activity is going to be destroyed
        db.close();
        super.onDestroy();
    }
}