package com.example.englishforkidsfinal.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private ImageView iv, cheer;
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
        cheer = findViewById(R.id.cheer);

        // Randomizing words received from Database
        next(false);
    }

    // Method to randomize words from Database and to set default adjustments
    public void next(boolean isWon) {
        if (isWon) {
            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cheer_scale);
            cheer.setVisibility(View.VISIBLE);
            cheer.startAnimation(anim);
            iv.setImageDrawable(null);
            new RemoveAnimation().execute();
        }
        if (wordsStack.isEmpty()) {
            Collections.shuffle(words);
            for (int i = 0; i < words.size(); i++) {
                wordsStack.push(words.get(i));
            }
        }

        currentWord = wordsStack.pop();

        if (!isWon) {
            Tools.loadImageFromStorage(currentWord.getEng(), getApplicationContext(), iv);
        }

        if (!isWon) {
            targetView.startSettings(this, currentWord.getEng());
            lettersView.startSettings(currentWord.getEng(), targetView);
        }
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

    class RemoveAnimation extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            cheer.clearAnimation();
            cheer.setVisibility(View.GONE);
            Tools.loadImageFromStorage(currentWord.getEng(), getApplicationContext(), iv);
            targetView.startSettings(CollectWord.this, currentWord.getEng());
            lettersView.startSettings(currentWord.getEng(), targetView);
            super.onPostExecute(s);
        }
    }
}