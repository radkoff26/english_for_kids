package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.adapters.DrawingAdapter;
import com.example.englishforkidsfinal.models.DefaultData;
import com.example.englishforkidsfinal.models.Drawing;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.view_models.DrawingImageView;
import com.example.englishforkidsfinal.models.view_models.DrawingView;
import com.example.englishforkidsfinal.models.Picture;
import com.example.englishforkidsfinal.models.TestModels;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

public class DrawingGame extends AppCompatActivity implements View.OnClickListener {

    // Declaration of variables
    private DrawingImageView[] drawings;
    private BackgroundMusic music;
    private ArrayList<String> right_answers = new ArrayList<>();
    private ImageButton done;
    private MaterialTextView words;
    private ImageView cheer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_game);

        // Initialization of BackgroundMusic and starting it
        music = new BackgroundMusic(this);
        music.start();

        done = findViewById(R.id.done);
        cheer = findViewById(R.id.cheer);
        words = findViewById(R.id.words);
        drawings = new DrawingImageView[] {
                findViewById(R.id.animal_1),
                findViewById(R.id.animal_2),
                findViewById(R.id.animal_3)
        };

        words.setTypeface(MainActivity.typeface);

        done.setOnClickListener(v -> {
            int c = 0;
            boolean wrong = false;
            for (int i = 0; i < drawings.length; i++) {
                if (drawings[i].isPainted() && right_answers.contains(drawings[i].getWord())) {
                    c++;
                }
                if (!drawings[i].isPainted() && !right_answers.contains(drawings[i].getWord())) {
                    wrong = true;
                }
            }
            if (c == right_answers.size() && wrong) {
                done.setVisibility(View.GONE);
                words.setVisibility(View.GONE);
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cheer_scale);
                cheer.setVisibility(View.VISIBLE);
                cheer.startAnimation(anim);
                for (DrawingImageView drawing : drawings) {
                    drawing.setOnClickListener(null);
                }
                Callable<Void> callable = () -> {
                    cheer.clearAnimation();
                    cheer.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                    words.setVisibility(View.VISIBLE);
                    setup();
                    return null;
                };
                new Tools.CountDown(callable).execute(1000);
            } else {
                Toast.makeText(getApplicationContext(), "Неправильно! Попробуй ещё раз :)", Toast.LENGTH_SHORT).show();
            }
        });

        setup();

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

    public void setup() {
        ArrayList<Drawing> drawingsList = DefaultData.drawings;
        right_answers.clear();

        for (int i = 0; i < drawings.length; i++) {
            drawings[i].setPainted(false);
        }

        int[] indexes = random2Indexes(drawingsList.size());
        int[] indexesToInsert = random2Indexes(drawings.length);

        for (int i = 0; i < 2; i++) {
            drawings[indexesToInsert[i]].setPaintedId(drawingsList.get(indexes[i]).getPainted());
            drawings[indexesToInsert[i]].setUnpaintedId(drawingsList.get(indexes[i]).getUnpainted());
            drawings[indexesToInsert[i]].setWord(drawingsList.get(indexes[i]).getWord());
            drawings[indexesToInsert[i]].setImageResource(drawingsList.get(indexes[i]).getUnpainted());
            drawings[indexesToInsert[i]].setOnClickListener(this);
            right_answers.add(drawingsList.get(indexes[i]).getWord());
        }

        StringBuilder s = new StringBuilder("РАСКРАСЬ: ");
        for (int i = 0; i < right_answers.size(); i++) {
            s.append(right_answers.get(i));
            if (i != right_answers.size() - 1) {
                s.append(", ");
            }
        }

        words.setText(s.toString().toUpperCase());

        int index = drawings.length - indexesToInsert[0] - indexesToInsert[1];
        Drawing rest = getRandomExcluding(indexes[0], indexes[1]);
        drawings[index].setWord(rest.getWord());
        drawings[index].setPaintedId(rest.getPainted());
        drawings[index].setUnpaintedId(rest.getUnpainted());
        drawings[index].setImageResource(rest.getUnpainted());
        drawings[index].setOnClickListener(this);
    }

    private int[] random2Indexes(int length) {
        int first = (int) (Math.random() * length), second = (int) (Math.random() * length);

        while (second == first) {
            second = (int) (Math.random() * length);
        }

        return new int[] {first, second};
    }

    private Drawing getRandomExcluding(int first, int second) {
        int index = (int) (Math.random() * DefaultData.drawings.size());

        while (index == first || index == second) {
            index = (int) (Math.random() * DefaultData.drawings.size());
        }

        return DefaultData.drawings.get(index);
    }

    @Override
    public void onClick(View v) {
        ((DrawingImageView) v).change();
    }
}