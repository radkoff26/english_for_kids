package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.DataBase;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.models.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FindRightPictureGame extends AppCompatActivity implements View.OnClickListener {

    private DataBase db;

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

        tracks = Arrays.asList(R.raw.first, R.raw.second, R.raw.third);

        music = new BackgroundMusic(tracks, this);
        music.start();

        indexes.add(0);
        indexes.add(1);
        indexes.add(2);
        indexes.add(3);

        db = new DataBase(this);

        pic1 = findViewById(R.id.pic1);
        pic2 = findViewById(R.id.pic2);
        pic3 = findViewById(R.id.pic3);
        pic4 = findViewById(R.id.pic4);

        label = findViewById(R.id.label);

        words = db.getWords();

        if (words.size() < 4) {
            Toast.makeText(getApplicationContext(), "You don't have enough learned words now!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.d("DEBUG", words.toString());

        Collections.shuffle(words);

        word = words.get(0);
        words.remove(word);

        Log.d("DEBUG", words.toString());

        int index = randomIndex(0, 4);

        indexes.remove(index);

        setResource(word, index, true);

        for (int i = 0; i < 3; i++) {
            setResource(words.get(i), indexes.get(i), false);
        }

        label.setText(word.getAnimal().toUpperCase());

    }

    private int randomIndex(int start, int end) {
        return (int) (start + (Math.random() * (end - start)));
    }

    public void setResource(Word word, int index, boolean isRight) {
        ImageView iv;
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
        iv.setImageResource(word.getRes());
        if (isRight) {
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restart();
                    Toast.makeText(getApplicationContext(), "Right answer!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            iv.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Wrong answer!", Toast.LENGTH_SHORT).show();
    }

    public void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        music.resumeMusic();
        super.onStart();
    }

    @Override
    protected void onStop() {
        music.pause();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}