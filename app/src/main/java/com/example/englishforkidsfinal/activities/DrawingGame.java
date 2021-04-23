package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.ListView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.adapters.DrawingAdapter;
import com.example.englishforkidsfinal.models.view_models.DrawingView;
import com.example.englishforkidsfinal.models.Picture;
import com.example.englishforkidsfinal.models.TestModels;

public class DrawingGame extends AppCompatActivity {

    // Declaration of variables
    private DrawingView draw;
    private ListView lv_colors;
    private DrawingAdapter adapter;
    private AppCompatButton back;
    private Picture picture;
    private BackgroundMusic music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_game);

        // Initialization of BackgroundMusic and starting it
        music = new BackgroundMusic(this);
        music.start();

        // Setting random picture
        picture = TestModels.getRandomPicture();

        // Initialization of views and setting it up
        draw = findViewById(R.id.draw);
        lv_colors = findViewById(R.id.colors);
        back = findViewById(R.id.back);

        draw.setPicture(picture);

        // Initialization of adapter
        adapter = new DrawingAdapter(this, R.layout.color_item, picture.getColors());

        // Setting the first color by default
        draw.setColor(picture.getColors().get(0).getIntColor());

        // Setting adapter to ListView
        lv_colors.setAdapter(adapter);

        // Setting OnItemClickListener to the ListView to change current color
        lv_colors.setOnItemClickListener((parent, view, position, id) -> {
            draw.setColor(picture.getColors().get(position).getIntColor());
        });

        // Setting OnItemClickListener to the button to call last drawing action off
        back.setOnClickListener(v -> {
            draw.back();
        });
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