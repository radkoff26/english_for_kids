package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.Word;

import java.util.ArrayList;

import static com.example.englishforkidsfinal.models.TestModels.words;

public class SpeakRightGame extends AppCompatActivity {

    private SpeechRecognizer recognizer;
    private ImageView animal;
    private AppCompatButton answer;
    private Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak_right_game);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1);
        }

        animal = (ImageView) findViewById(R.id.animal);
        answer = (AppCompatButton) findViewById(R.id.answer);

        int id = randomId();
        word = words.get(id);

        animal.setImageResource(word.getRes());

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        recognizer = SpeechRecognizer
                .createSpeechRecognizer(this.getApplicationContext());
        Log.d("DEBUG", SpeechRecognizer.isRecognitionAvailable(this)+"");
        RecognitionListener listener = new RecognitionListener() {
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> voiceResults = results
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (voiceResults == null) {
                    Toast.makeText(getApplicationContext(), "You haven't said any words!", Toast.LENGTH_SHORT).show();
                } else {
                    if (compare(word.getAnimal().toLowerCase(), voiceResults.get(0).toLowerCase())) {
                        restart();
                    } else {
                        Toast.makeText(getApplicationContext(), voiceResults.get(0), Toast.LENGTH_SHORT).show();
                    }
                }
                answer.setClickable(true);
                answer.setFocusable(true);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }

            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d(getString(R.string.log_label), "Ready for speech");
            }

            @Override
            public void onError(int error) {
                Log.d(getString(R.string.log_label),
                        "Error listening for speech: " + error);
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d(getString(R.string.log_label), "Speech starting");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                answer.setClickable(true);
                answer.setFocusable(true);
            }
        };
        recognizer.setRecognitionListener(listener);

        answer.setOnClickListener(v -> {
            recognizer.startListening(intent);
            answer.setClickable(false);
            answer.setFocusable(false);
        });
    }

    public int randomId() {
        return (int) (Math.random() * words.size());
    }

    public void restart() {
        finish();
        startActivity(getIntent());
    }

    public boolean compare(String main, String word) {
        if (word.length() < 3 || main.equals(word)) {
            return main.equals(word);
        } else if (word.length() < main.length()) {
            return false;
        }
        int size = main.length() / 4 * 3;
        if (main.length() == 3) {
            size = 2;
        }
        for (int i = 0; i <= main.length() - size; i++) {
            if (word.contains(main.substring(i, i + size))) {
                return true;
            }
        }
        return false;
    }
}