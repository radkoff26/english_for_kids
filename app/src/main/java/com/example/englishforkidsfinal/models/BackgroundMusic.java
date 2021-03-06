package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import java.util.List;

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
        this.tracks = tracks;
        this.ctx = ctx;

        // Setting id of random track
        randomizeTrack();

        // Initializing of media player
        mp = MediaPlayer.create(ctx, tracks.get(idOfTrack));

        // Initializing SharedPreferences to get music settings
        sp = ctx.getSharedPreferences("settings", Context.MODE_PRIVATE);

        // Updating access
        updateAccess();
    }

    // Method to set id of random track
    private void randomizeTrack() {
        this.idOfTrack = (int) Math.floor(Math.random() * 3);
    }

    // Method to manage media player to play music in new thread
    @Override
    public void run() {
        updateAccess();
        if (isAccessedToPlay) {
            mp.start();
            isPlaying = true;
            while (isPlaying) {
                if (mp.getCurrentPosition() + 10 > mp.getDuration()) {
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
            int id = idOfTrack;
            while (idOfTrack == id) {
                randomizeTrack();
            }
            if (mp != null) {
                mp.pause();
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
        isAccessedToPlay = sp.getBoolean("music", true);
    }
}
