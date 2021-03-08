package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.models.ColorsPaint;
import com.example.englishforkidsfinal.adapters.DrawingAdapter;
import com.example.englishforkidsfinal.models.DrawingView;
import com.example.englishforkidsfinal.models.Picture;
import com.example.englishforkidsfinal.models.TestModels;

import java.util.Arrays;
import java.util.List;

public class DrawingGame extends AppCompatActivity {

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

        music = new BackgroundMusic(Arrays.asList(R.raw.first, R.raw.second, R.raw.third), this);
        music.start();

        picture = TestModels.getRandomPicture();

        draw = findViewById(R.id.draw);
        lv_colors = findViewById(R.id.colors);
        back = findViewById(R.id.back);

        draw.setPicture(picture);

        adapter = new DrawingAdapter(this, R.layout.color_item, picture.getColors());

        draw.setColor(picture.getColors().get(0).getIntColor());

        lv_colors.setAdapter(adapter);

        lv_colors.setOnItemClickListener((parent, view, position, id) -> {
            draw.setColor(picture.getColors().get(position).getIntColor());
        });

        back.setOnClickListener(v -> {
            draw.back();
        });
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
}