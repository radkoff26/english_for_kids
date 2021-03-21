package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.LearnedWordsDataBase;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.db_models.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.englishforkidsfinal.activities.MainActivity.currentPosition;

public class FindRightPictureGame extends AppCompatActivity implements View.OnClickListener {

    // Declaration of variables grouped by type
    private LearnedWordsDataBase db;

    private ImageView pic1;
    private ImageView pic2;
    private ImageView pic3;
    private ImageView pic4;

    private TextView label;

    private List<Word> words;

    private Word word;

    private BackgroundMusic music;

    private ArrayList<Integer> indexes = new ArrayList<>();

    private List<Integer> tracks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_right_picture_game);

        // Initialization
        // Background music
        music = new BackgroundMusic(this);

        // Starting music
        music.start();

        // Adding indexes to indexes list to make work with right and wrong words easier
        indexes.add(0);
        indexes.add(1);
        indexes.add(2);
        indexes.add(3);

        // Database
        db = new LearnedWordsDataBase(this);

        // Image Views
        pic1 = findViewById(R.id.pic1);
        pic2 = findViewById(R.id.pic2);
        pic3 = findViewById(R.id.pic3);
        pic4 = findViewById(R.id.pic4);

        // Text View with title of right word
        label = findViewById(R.id.label);

        // Receiving all words from Database
        words = db.getWords();

        // Checking if the number of words is okay to gain access
        if (words.size() < 4) {
            Toast.makeText(getApplicationContext(), "You need to know at least 4 words to play this game!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        randomize();

    }

    public void randomize() {
        // Randomizing list of all words
        Collections.shuffle(words);

        // Choose one right word
        word = words.get(0);

        // Removing right word from list of all words to make work with wrong words easier
        words.remove(word);

        // Declaring and initializing index of right word
        int index = randomIndex(0, 4);

        // Removing right word's index from list of all indexes to make work with wrong words' indexes easier
        indexes.remove(index);

        // Inserting into content view the right word
        setResource(word, index, true);

        // Inserting into content view the wrong words
        for (int i = 0; i < 3; i++) {
            setResource(words.get(i), indexes.get(i), false);
        }

        // Setting right word up in the content view
        label.setText(word.getEng().toUpperCase());

        indexes.add(index);
    }

    // Method to simplify indexes' randomizing
    private int randomIndex(int start, int end) {
        return (int) (start + (Math.random() * (end - start)));
    }

    // Method to automatically set resource to content view
    public void setResource(Word word, int index, boolean isRight) {

        // Initializing Image View
        ImageView iv;

        // Setting word to particular Image View
        switch (index) {
            case 0:
                iv = pic1;
                break;
            case 1:
                iv = pic2;
                break;
            case 2:
                iv = pic3;
                break;
            case 3:
                iv = pic4;
                break;
            default:
                return;
        }
        Tools.LoadImageFromWebOperations(word.getUrl(), iv);

        // Setting OnClickListeners to right and wrong words
        if (isRight) {
            iv.setOnClickListener(v -> {
                restart();
            });
        } else {
            iv.setOnClickListener(this);
        }
    }

    // OnClick method to notify that chosen word is wrong
    @Override
    public void onClick(View view) {
        Vibrator v = (Vibrator) getApplication().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(150);
    }

    // Method to randomize word
    public void restart() {
        randomize();
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