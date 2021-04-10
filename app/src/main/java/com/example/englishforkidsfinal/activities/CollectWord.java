package com.example.englishforkidsfinal.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
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
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;
import java.util.Stack;


public class CollectWord extends AppCompatActivity {

    // Declaration of variables
    private TargetView targetView;
    private LettersView lettersView;
    private ImageView iv;
    private BackgroundMusic music;
    private Word currentWord;
    private LearnedWordsDataBase db;
    private List<Word> words;
    private Stack<Word> wordsStack;
    private Bitmap picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_space);

        // Initialization of Database
        db = new LearnedWordsDataBase(this);

        // Receiving all words from Database
        words = db.getWords();
        wordsStack = new Stack<>();

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
        next();
    }

    // Method to randomize words from Database and to set default adjustments
    public void next() {
        if (wordsStack.isEmpty()) {
            Collections.shuffle(words);
            for (int i = 0; i < words.size(); i++) {
                wordsStack.push(words.get(i));
            }
        }

        currentWord = wordsStack.pop();

        Tools.loadImageFromStorage(currentWord.getEng(), getApplicationContext(), iv);

        targetView.startSettings(this, currentWord.getEng());
        lettersView.startSettings(currentWord.getEng(), targetView);
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