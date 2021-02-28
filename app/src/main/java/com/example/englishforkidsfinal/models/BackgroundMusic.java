package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.List;

public class BackgroundMusic extends Thread {

    private int idOfTrack;
    private List<Integer> tracks;
    private MediaPlayer mp;
    private Context ctx;
    private boolean isPlaying;

    public BackgroundMusic(List<Integer> tracks, Context ctx) {
        this.tracks = tracks;
        this.ctx = ctx;
        randomizeTrack();
        mp = MediaPlayer.create(ctx, tracks.get(idOfTrack));
    }

    private void randomizeTrack() {
        this.idOfTrack = (int) Math.floor(Math.random() * 3);
    }

    @Override
    public void run() {
        mp.start();
        isPlaying = true;
        while (isPlaying) {
            if (mp.getCurrentPosition() + 10 > mp.getDuration()) {
                nextTrack();
            }
        }
    }

    public void pause() {
        isPlaying = false;
        if (mp != null && mp.isPlaying()) {
            mp.pause();
        }
    }

    public void resumeMusic() {
        isPlaying = true;
        if (mp != null && !mp.isPlaying()) {
            mp.start();
        }
    }

    public void stopPlaying() {
        isPlaying = false;
    }

    // test void
    public void nextTrack() {
        int id = idOfTrack;
        while (idOfTrack == id) {
            randomizeTrack();
        }
        mp.pause();
        mp = MediaPlayer.create(ctx, tracks.get(idOfTrack));
        mp.start();
    }
}
