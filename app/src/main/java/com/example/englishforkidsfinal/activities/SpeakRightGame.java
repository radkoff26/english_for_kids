package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.db_sql_models.SpeakRight;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.db_models.SpeakRightModel;

import java.util.ArrayList;
import java.util.List;

public class SpeakRightGame extends AppCompatActivity {

    // Declaration of variables
    private SpeechRecognizer recognizer;
    private ImageView animal;
    private AppCompatButton answer;
    private SpeakRightModel word;
    private SpeakRight speakRight;
    private List<SpeakRightModel> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak_right_game);

        getActionBar().hide();

        // Checking if recording audio is allowed
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    1
            );
        }

        speakRight = new SpeakRight(this);

        models = speakRight.getWords();

        // Initializing views
        animal = findViewById(R.id.animal);
        answer = findViewById(R.id.answer);

        answer.setTypeface(MainActivity.typeface);

        // Setting speech recognizer up
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizer = SpeechRecognizer
                .createSpeechRecognizer(this.getApplicationContext());

        // Initializing id of random right word
        randomize();

        // Setting OnClickListener to button to launch recognition
        answer.setOnClickListener(v -> {
            recognizer.startListening(intent);
            answer.setClickable(false);
            answer.setFocusable(false);
        });
    }

    // Method to randomize
    public void randomize() {
        int id = randomId();
        word = models.get(id);

        // Making and setting OnClickListener up to speak
        RecognitionListener listener = new RecognitionListener() {
            @Override
            public void onResults(Bundle results) {
                // Initializing vibrator and speech results
                Vibrator v = (Vibrator) getApplication().getSystemService(Context.VIBRATOR_SERVICE);
                ArrayList<String> voiceResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                // Resolving results
                if (voiceResults == null) {
                    Toast.makeText(getApplicationContext(), "You haven't said any words!", Toast.LENGTH_SHORT).show();
                } else {
                    if (compare(word.getEng().toLowerCase(), voiceResults.get(0).toLowerCase())) {
                        // If answer is right
                        restart();
                    } else {
                        // If answer is wrong
                        v.vibrate(100);
                    }
                }

                // Enabling activating button
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

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                // Enabling activating button
                answer.setClickable(true);
                answer.setFocusable(true);
            }
        };
        recognizer.setRecognitionListener(listener);

        // Setting right word's resources to content view
        Tools.LoadImageFromWebOperations(word.getUrl(), animal);
    }

    // Method to randomize indexes of list of words
    public int randomId() {
        return (int) (Math.random() * models.size());
    }

    // Method to restart activity and to randomize words
    public void restart() {
        randomize();
    }

    // Method to maximally avoid disadvantages of speech recognizer in comparing of results
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

    @Override
    protected void onDestroy() {
        // Destroying recognizer when activity destroys
        recognizer.destroy();
        super.onDestroy();
    }
}