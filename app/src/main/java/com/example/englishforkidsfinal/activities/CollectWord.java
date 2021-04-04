package com.example.englishforkidsfinal.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.LearnedWordsDataBase;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.models.LettersView;
import com.example.englishforkidsfinal.models.TargetView;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.db_models.Word;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.englishforkidsfinal.activities.MainActivity.currentPosition;
import static com.example.englishforkidsfinal.models.ArgumentsContractions.MUSIC_CURRENT_POSITION;


public class CollectWord extends AppCompatActivity {

    // Declaration of variables
    private TargetView targetView;
    private LettersView lettersView;
    private ImageView iv;
    private BackgroundMusic music;
    private List<Integer> tracks;
    private LearnedWordsDataBase db;
    private List<Word> words;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_space);

        // Initialization of Database
        db = new LearnedWordsDataBase(this);

        // Receiving all words from Database
        words = db.getWords();

        // Checking if there are any learned words unless access won't be gained
        if (words.size() == 0) {
            Toast.makeText(getApplicationContext(), "You need to learn at least one word!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Background music
        music = new BackgroundMusic(this);
        // Starting music
        music.start();

        // Initializing all views
        targetView = findViewById(R.id.ll_target);
        lettersView = findViewById(R.id.ll_letters);
        iv = findViewById(R.id.iv);

        // Randomizing words received from Database
        shuffle();
    }

    // Method to randomize words from Database and to set default adjustments
    public void shuffle() {
        Collections.shuffle(words);

        targetView.startSettings(this, words.get(0).getEng());
        lettersView.startSettings(words.get(0).getEng(), targetView);

        Tools.LoadImageFromWebOperations(words.get(0).getUrl(), iv);
    }

    // Method to randomize words
    public void restart() {
        shuffle();
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