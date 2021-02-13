package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.models.DrawingSurfaceView;

import java.util.Arrays;
import java.util.List;

public class DrawingGame extends AppCompatActivity {

    private BackgroundMusic music;
    private List<Integer> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawingSurfaceView(this));

        tracks = Arrays.asList(R.raw.first, R.raw.second, R.raw.third);

        music = new BackgroundMusic(tracks, this);
        music.start();
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
}