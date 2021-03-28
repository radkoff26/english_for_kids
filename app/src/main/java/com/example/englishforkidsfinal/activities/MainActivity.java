package com.example.englishforkidsfinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.fragments.AlphabetFragment;
import com.example.englishforkidsfinal.fragments.AlphabetLetterFragment;
import com.example.englishforkidsfinal.fragments.ContestFragment;
import com.example.englishforkidsfinal.fragments.GamesFragment;
import com.example.englishforkidsfinal.fragments.HomeFragment;
import com.example.englishforkidsfinal.fragments.LearningFragment;
import com.example.englishforkidsfinal.fragments.MainContestFragment;
import com.example.englishforkidsfinal.fragments.MainLearningFragment;
import com.example.englishforkidsfinal.fragments.SettingsFragment;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declaration of variables
    private BottomNavigationView bnv;
    private BackgroundMusic music;
    public static int currentPosition = 0;
    public static int currentTrack = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // When main activity starts, home fragment appears in the foreground
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, new HomeFragment())
                .commit();


        // Background music
        updateTrack();

        // Initializing views
        bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bnv.setOnNavigationItemSelectedListener(item -> {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment, new HomeFragment())
                            .commit();
                    getSupportActionBar().setTitle(R.string.home);
                    break;
                case R.id.alphabet:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment, new AlphabetFragment())
                            .commit();
                    getSupportActionBar().setTitle(R.string.alphabet);
                    break;
                case R.id.games:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment, new GamesFragment())
                            .commit();
                    getSupportActionBar().setTitle(R.string.games);
                    break;
                case R.id.learning:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment, new LearningFragment())
                            .commit();
                    getSupportActionBar().setTitle(R.string.learning);
                    break;
            }
            return true;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Setting Options Menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.settings);
        getSupportActionBar().setTitle(R.string.home);

        // Setting OnClickListener to settings fragment button to transact to
        menuItem.setOnMenuItemClickListener(item -> {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new SettingsFragment()).commit();
            getSupportActionBar().setTitle(R.string.settings);
            return true;
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        // Watching Back Presses to transact to right fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.get(fragments.size()-1).getClass() == HomeFragment.class) {
            music.pause();
            finish();
        } else if (fragments.get(fragments.size()-1).getClass() == MainLearningFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new LearningFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.learning);
        } else if (fragments.get(fragments.size()-1).getClass() == MainContestFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new ContestFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.learning);
        } else if (fragments.get(fragments.size()-1).getClass() == AlphabetLetterFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new AlphabetFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.alphabet);
        } else if (fragments.get(fragments.size()-1).getClass() == SettingsFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new HomeFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.home);
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new HomeFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.home);
        }
    }

    // Method to update access and to turn the next track
    public void nextTrack() {
        music.updateAccess();
        music.nextTrack();
    }

    // Method to update track
    public void updateTrack() {
        if (music != null) {
            music.pause();
        }
        music = new BackgroundMusic(this);
        music.setCurrentPosition(currentPosition);
        music.start();
    }
}