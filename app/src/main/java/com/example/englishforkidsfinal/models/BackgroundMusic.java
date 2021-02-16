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
    }

    public void pause() {
        if (mp != null && mp.isPlaying()) {
            mp.pause();
        }
    }

    public void resumeMusic() {
        if (mp != null && !mp.isPlaying()) {
            mp.start();
        }
    }
}
