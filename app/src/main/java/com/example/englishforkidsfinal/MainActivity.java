package com.example.englishforkidsfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    AppCompatButton games, alphabet, learning;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        games = (AppCompatButton) findViewById(R.id.games);
        alphabet = (AppCompatButton) findViewById(R.id.alphabet);
        learning = (AppCompatButton) findViewById(R.id.learning);

        view = findViewById(R.id.fragment);

        NavigateListener listener = new NavigateListener();

        games.setOnClickListener(listener);
        alphabet.setOnClickListener(listener);
        learning.setOnClickListener(listener);
    }

    class NavigateListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            clearButtons();
            switch (v.getId()) {
                case R.id.games:
                    Navigation.findNavController(view).navigate(R.id.gamesFragment);;
                    break;
                case R.id.alphabet:
                    Navigation.findNavController(view).navigate(R.id.alphabetFragment);
                    break;
                case R.id.learning:
                    Navigation.findNavController(view).navigate(R.id.learningFragment);
                    break;
            }
            ((AppCompatButton) v).setBackgroundColor(Color.GREEN);
        }
    }

    public void clearButtons() {
        games.setBackgroundColor(Color.WHITE);
        alphabet.setBackgroundColor(Color.WHITE);
        learning.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onBackPressed() {
        Navigation.findNavController(view).navigate(R.id.homeFragment);
        clearButtons();
    }
}