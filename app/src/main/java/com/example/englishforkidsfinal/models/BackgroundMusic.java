package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.englishforkidsfinal.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_SETTINGS;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_SETTINGS_MUSIC;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_SETTINGS_MUSIC_DEFAULT;

public class BackgroundMusic extends Thread {

    // Declaration of variables
    private int idOfTrack;
    private List<Integer> tracks;
    private MediaPlayer mp;
    private Context ctx;
    private boolean isPlaying;
    private boolean isAccessedToPlay;
    private SharedPreferences sp;

    // Constructor
    public BackgroundMusic(List<Integer> tracks, Context ctx) {
        this.tracks = Arrays.asList(R.raw.first, R.raw.second, R.raw.third);
        this.ctx = ctx;

        // Setting id of random track
        idOfTrack = randomizeTrack();

        // Initializing of media player
        mp = MediaPlayer.create(ctx, tracks.get(idOfTrack));

        // Initializing SharedPreferences to get music settings
        sp = ctx.getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);

        // Updating access
        updateAccess();
    }

    // Method to set id of random track
    private int randomizeTrack() {
        return (int) (Math.random() * tracks.size());
    }

    // Method to manage media player to play music in a new thread
    @Override
    public void run() {
        updateAccess();
        if (isAccessedToPlay) {
            mp.start();
            isPlaying = true;
            while (isPlaying) {
                if (mp.getCurrentPosition() + 1500 > mp.getDuration()) {
                    nextTrack();
                }
            }
        }
    }

    // Method to pause music
    public void pause() {
        updateAccess();
        if (isAccessedToPlay) {
            isPlaying = false;
            if (mp != null && mp.isPlaying()) {
                mp.pause();
            }
        }
    }

    // Method to resume music
    public void resumeMusic() {
        updateAccess();
        if (isAccessedToPlay) {
            isPlaying = true;
            if (mp != null && !mp.isPlaying()) {
                mp.start();
            }
        }
    }

    // Method to set playing of music false
    public void stopPlaying() {
        isPlaying = false;
    }

    // Method to turn the next track with all checks
    public void nextTrack() {
        updateAccess();
        if (isAccessedToPlay) {
            if (mp != null) {
                mp.pause();
            }
            int id = idOfTrack;
            while (idOfTrack == id) {
                idOfTrack = randomizeTrack();
            }
            mp = MediaPlayer.create(ctx, tracks.get(idOfTrack));
            mp.start();
        } else {
            if (mp != null) {
                mp.pause();
            }
            stopPlaying();
        }
    }

    // Method to update access to play music
    public void updateAccess() {
        isAccessedToPlay = sp.getBoolean(CACHE_SETTINGS_MUSIC, CACHE_SETTINGS_MUSIC_DEFAULT);
    }
}
