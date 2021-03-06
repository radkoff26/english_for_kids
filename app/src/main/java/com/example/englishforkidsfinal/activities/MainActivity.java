package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declaration of variables
    private AppCompatButton games, alphabet, learning, contest;
    private View view;
    private List<Integer> tracks;
    private BackgroundMusic music;


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
        // Tracks
        tracks = Arrays.asList(R.raw.first, R.raw.second, R.raw.third);
        // Music
        music = new BackgroundMusic(tracks, this);
        // Starting music
        music.start();

        // Initializing views
        games = (AppCompatButton) findViewById(R.id.games);
        alphabet = (AppCompatButton) findViewById(R.id.alphabet);
        learning = (AppCompatButton) findViewById(R.id.learning);
        contest = (AppCompatButton) findViewById(R.id.contest);

        // Initializing listener for buttons in the Navigation Bar
        NavigateListener listener = new NavigateListener();

        // Setting OnClickListeners to the buttons
        games.setOnClickListener(listener);
        alphabet.setOnClickListener(listener);
        learning.setOnClickListener(listener);
        contest.setOnClickListener(listener);
    }

    // NavigationListener class to get rid of repeating of code (DRY)
    class NavigateListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // Clear colors of buttons
            clearButtons();

            // Initializing transaction
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Choosing right fragment to transact to
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
                case R.id.contest:
                    transaction.add(R.id.fragment, new ContestFragment());
                    break;
            }

            // Setting color to right button
            ((AppCompatButton) v).setBackgroundColor(Color.GREEN);

            // Committing transaction
            transaction.commit();
        }
    }

    // Method to reset all buttons' background
    public void clearButtons() {
        games.setBackgroundColor(Color.WHITE);
        alphabet.setBackgroundColor(Color.WHITE);
        learning.setBackgroundColor(Color.WHITE);
        contest.setBackgroundColor(Color.WHITE);
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setting OnClickListener to settings fragment button to transact to
        menuItem.setOnMenuItemClickListener(item -> {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new SettingsFragment()).commit();
            clearButtons();
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
        } else if (fragments.get(fragments.size()-1).getClass() == MainContestFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new ContestFragment())
                    .commit();
        } else if (fragments.get(fragments.size()-1).getClass() == AlphabetLetterFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new AlphabetFragment())
                    .commit();
        } else if (fragments.get(fragments.size()-1).getClass() == SettingsFragment.class) {
            clearButtons();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new HomeFragment())
                    .commit();
        } else {
            clearButtons();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new HomeFragment())
                    .commit();
        }
    }

    // Method to update access and to turn the next track up
    public void nextTrack() {
        music.updateAccess();
        music.nextTrack();
    }
}