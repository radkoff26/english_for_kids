package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class SayThread extends Thread {

    private boolean isSpeaking = false;
    private Context ctx;
    private String word;
    private TextToSpeech tts;

    public SayThread(Context context, String word) {
        this.ctx = context;
        this.word = word;
        this.tts = new TextToSpeech(ctx, status -> {});
        tts.setLanguage(Locale.UK);
    }

    @Override
    public void run() {
        isSpeaking = true;
        while (isSpeaking) {
            tts.speak(word.toLowerCase(), TextToSpeech.QUEUE_ADD, null);
            stopSpeaking();
        }
    }

    public void stopSpeaking() {
        isSpeaking = false;
    }
}