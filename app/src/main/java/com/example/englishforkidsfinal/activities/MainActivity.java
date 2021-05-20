package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.BigAnimalDatabase;
import com.example.englishforkidsfinal.fragments.AlphabetFragment;
import com.example.englishforkidsfinal.fragments.AlphabetLetterFragment;
import com.example.englishforkidsfinal.fragments.CategoriesFragment;
import com.example.englishforkidsfinal.fragments.CategoryFragment;
import com.example.englishforkidsfinal.fragments.ContestFragment;
import com.example.englishforkidsfinal.fragments.GamesFragment;
import com.example.englishforkidsfinal.fragments.HomeFragment;
import com.example.englishforkidsfinal.fragments.LearningFragment;
import com.example.englishforkidsfinal.fragments.MainContestFragment;
import com.example.englishforkidsfinal.fragments.MainLearningFragment;
import com.example.englishforkidsfinal.fragments.SettingsFragment;
import com.example.englishforkidsfinal.fragments.FragmentTutorial;
import com.example.englishforkidsfinal.models.BackgroundMusic;
import com.example.englishforkidsfinal.models.Tools;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import static com.example.englishforkidsfinal.models.contractions.CacheContractions.*;

public class MainActivity extends AppCompatActivity {

    // Declaration of variables
    private BottomNavigationView bnv;
    private BackgroundMusic music;
    public static Typeface typeface;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("DEBUG", new BigAnimalDatabase(this).getBigAnimals().toString());

        sp = getSharedPreferences(CACHE_CACHE, MODE_PRIVATE);

        // Background music
        updateTrack();

        typeface = Typeface.createFromAsset(getAssets(), "fonts/FuturaRoundBold.ttf");

        // Initializing views
        bnv = findViewById(R.id.bottom_navigation);

        updateColors();

        if (!sp.getBoolean(CACHE_HAD_TUTORIAL, CACHE_HAD_TUTORIAL_DEFAULT)) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new FragmentTutorial())
                    .commit();
            removeBesidesLast();
            return;
        }

        // When main activity starts, home fragment appears in the foreground
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, new HomeFragment())
                .commit();

        loadNavigation();
    }

    public void restart() {
        startActivity(getIntent());
        finish();
    }

    public void updateColors() {
        Tools.setBackgroundMain(bnv, this);
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_pressed}
        };

        int color = Tools.getFont(this);

        int[] colors = new int[]{
                color,
                color,
                color,
                color
        };

        bnv.setItemTextColor(new ColorStateList(states, colors));
        bnv.setItemIconTintList(new ColorStateList(states, colors));
    }

    public void clearNavigation() {
        bnv.setOnNavigationItemSelectedListener(null);
    }

    public void loadTheOnlyNavigation(int id, Callable<Void> callable) {
        bnv.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == id) {
                try {
                    callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        });
    }

    public void loadNavigation() {
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
                default:
                    return true;
            }
            removeBesidesLast();
            return true;
        });
    }

    public void removeBesidesLast() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragments.get(i))
                    .commit();
        }
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
        if (sp.getBoolean(CACHE_HAD_TUTORIAL, CACHE_HAD_TUTORIAL_DEFAULT)) {
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
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        // Watching Back Presses to transact to right fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.get(fragments.size() - 1).getClass() == HomeFragment.class) {
            music.pause();
            finish();
            return;
        } else if (fragments.get(fragments.size() - 1).getClass() == MainLearningFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new LearningFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.learning);
        } else if (fragments.get(fragments.size() - 1).getClass() == MainContestFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new ContestFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.learning);
        } else if (fragments.get(fragments.size() - 1).getClass() == AlphabetLetterFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new AlphabetFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.alphabet);
        } else if (fragments.get(fragments.size() - 1).getClass() == SettingsFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new HomeFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.home);
        } else if (fragments.get(fragments.size() - 1).getClass() == CategoriesFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new LearningFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.learning);
        } else if (fragments.get(fragments.size() - 1).getClass() == CategoryFragment.class) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new CategoriesFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.learning);
        } else if (fragments.get(fragments.size() - 1).getClass() == FragmentTutorial.class) {
            return;
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, new HomeFragment())
                    .commit();
            bnv.setSelectedItemId(R.id.home);
        }
        removeBesidesLast();
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
        music.start();
    }
}