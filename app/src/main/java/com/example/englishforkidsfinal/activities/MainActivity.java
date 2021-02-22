package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.fragments.AlphabetFragment;
import com.example.englishforkidsfinal.fragments.GamesFragment;
import com.example.englishforkidsfinal.fragments.HomeFragment;
import com.example.englishforkidsfinal.fragments.LearningFragment;
import com.example.englishforkidsfinal.fragments.MainLearningFragment;
import com.example.englishforkidsfinal.models.BackgroundMusic;

import java.util.Arrays;
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

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, new HomeFragment())
                .commit();

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
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (v.getId()) {
                case R.id.games:
                    transaction.add(R.id.fragment, new GamesFragment());
                    break;
                case R.id.alphabet:
                    transaction.add(R.id.fragment, new AlphabetFragment());
                    break;
                case R.id.learning:
                    transaction.add(R.id.fragment, new LearningFragment());
                    break;
            }
            ((AppCompatButton) v).setBackgroundColor(Color.GREEN);
            transaction.commit();
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
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.get(fragments.size()-1).getClass() == HomeFragment.class) {
            music.pause();
            finish();
        } else if (fragments.get(fragments.size()-1).getClass() == MainLearningFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new LearningFragment())
                    .commit();
        } else {
            clearButtons();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new HomeFragment())
                    .commit();
        }
    }
}