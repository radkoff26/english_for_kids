package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.List;

public class BackgroundMusic extends Thread {

    private int idOfTrack;
    private List<Integer> tracks;
    private MediaPlayer mp;
    private Context ctx;
    private boolean isPlaying;
    private boolean isAccessedToPlay;
    private SharedPreferences sp;

    public BackgroundMusic(List<Integer> tracks, Context ctx) {
        this.tracks = tracks;
        this.ctx = ctx;
        randomizeTrack();
        mp = MediaPlayer.create(ctx, tracks.get(idOfTrack));
        sp = ctx.getSharedPreferences("settings", Context.MODE_PRIVATE);
        updateAccess();
    }

    private void randomizeTrack() {
        this.idOfTrack = (int) Math.floor(Math.random() * 3);
    }

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

    public void pause() {
        updateAccess();
        if (isAccessedToPlay) {
            isPlaying = false;
            if (mp != null && mp.isPlaying()) {
                mp.pause();
            }
        }
    }

    public void resumeMusic() {
        updateAccess();
        if (isAccessedToPlay) {
            isPlaying = true;
            if (mp != null && !mp.isPlaying()) {
                mp.start();
            }
        }
    }

    public void stopPlaying() {
        isPlaying = false;
    }

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

    public void updateAccess() {
        isAccessedToPlay = sp.getBoolean("music", true);
    }
}
