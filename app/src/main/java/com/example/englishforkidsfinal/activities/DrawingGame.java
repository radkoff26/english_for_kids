package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.models.DrawingSurfaceView;

import java.util.Arrays;
import java.util.List;

public class DrawingGame extends AppCompatActivity {

    // Declaration of variables
    private BackgroundMusic music;
    private List<Integer> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawingSurfaceView(this));

        // Initialization
        tracks = Arrays.asList(R.raw.first, R.raw.second, R.raw.third);
        music = new BackgroundMusic(tracks, this);

        // Starting background music
        music.start();
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
}