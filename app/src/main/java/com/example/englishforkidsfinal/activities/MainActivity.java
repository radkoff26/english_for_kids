package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.BackgroundMusic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton games, alphabet, learning;
    private View view;
    private List<Integer> tracks;
    private BackgroundMusic music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tracks = Arrays.asList(R.raw.first, R.raw.second, R.raw.third);

        music = new BackgroundMusic(tracks, this);
        music.start();

        games = (AppCompatButton) findViewById(R.id.games);
        alphabet = (AppCompatButton) findViewById(R.id.alphabet);
        learning = (AppCompatButton) findViewById(R.id.learning);

        view = findViewById(R.id.fragment);

        NavigateListener listener = new NavigateListener();

        games.setOnClickListener(listener);
        alphabet.setOnClickListener(listener);
        learning.setOnClickListener(listener);
    }

    class NavigateListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            clearButtons();
            switch (v.getId()) {
                case R.id.games:
                    Navigation.findNavController(view).navigate(R.id.gamesFragment);;
                    break;
                case R.id.alphabet:
                    Navigation.findNavController(view).navigate(R.id.alphabetFragment);
                    break;
                case R.id.learning:
                    Navigation.findNavController(view).navigate(R.id.learningFragment);
                    break;
            }
            ((AppCompatButton) v).setBackgroundColor(Color.GREEN);
        }
    }

    public void clearButtons() {
        games.setBackgroundColor(Color.WHITE);
        alphabet.setBackgroundColor(Color.WHITE);
        learning.setBackgroundColor(Color.WHITE);
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
    public void onBackPressed() {
        Navigation.findNavController(view).navigate(R.id.homeFragment);
        clearButtons();
    }
}