package com.example.englishforkidsfinal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.google.android.material.button.MaterialButton;

import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.COLOR;
import static com.example.englishforkidsfinal.models.contractions.CacheContractions.*;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    // Declaration of variables
    private AppCompatButton saveChanges;
    private SwitchCompat music;
    private SharedPreferences sp;
    private boolean isTurned;
    public static final int MAIN = 1, MAIN_SECONDARY = 2, FONT = 3;
    private MaterialButton main, main_secondary, font, default_settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialization of main view
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initializing SharedPreferences
        sp = getActivity().getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);

        // Initializing views
        saveChanges = view.findViewById(R.id.save_changes);
        music = view.findViewById(R.id.switch_music);
        main = view.findViewById(R.id.main);
        main_secondary = view.findViewById(R.id.main_secondary);
        font = view.findViewById(R.id.font);
        default_settings = view.findViewById(R.id.default_settings);

        main.setOnClickListener(this);
        main_secondary.setOnClickListener(this);
        font.setOnClickListener(this);

        default_settings.setOnClickListener(v -> {
            SharedPreferences sp = getActivity().getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(CACHE_SETTINGS_COLOR_MAIN, getResources().getColor(R.color.main));
            editor.putInt(CACHE_SETTINGS_COLOR_MAIN_SECONDARY, getResources().getColor(R.color.main_secondary));
            editor.putInt(CACHE_SETTINGS_COLOR_FONT, getResources().getColor(R.color.font_color));
            editor.apply();
            ((MainActivity) getActivity()).updateColors();
        });

        saveChanges.setTypeface(MainActivity.typeface);
        music.setTypeface(MainActivity.typeface);

        // Receiving data about music settings
        isTurned = sp.getBoolean(CACHE_SETTINGS_MUSIC, CACHE_SETTINGS_MUSIC_DEFAULT);

        // Disabling button
        toggleButton(false);

        // Setting Switch View up
        music.setChecked(isTurned);

        // Setting OnCheckChangeListener to the Switch View
        music.setOnCheckedChangeListener((buttonView, isChecked) -> {
            toggleButton(isChecked != isTurned);
        });

        // Setting OnClickListener to the save button
        saveChanges.setOnClickListener(v -> {
            // Initializing SharedPreferences and its Editor
            SharedPreferences sp = getActivity().getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            // Inserting data about music settings into Editor and applying changes
            editor.putBoolean(CACHE_SETTINGS_MUSIC, music.isChecked());
            editor.apply();

            // Applying changes in Main Activity and removing fragment
            ((MainActivity) getActivity()).nextTrack();
            getActivity().onBackPressed();
        });

        return view;
    }


    // Method to toggle appearance of save button
    private void toggleButton(boolean flag) {
        saveChanges.setClickable(flag);
        saveChanges.setFocusable(flag);
        saveChanges.setEnabled(flag);
        if (flag) {
            saveChanges.setBackgroundResource(R.drawable.btn_bg);
        } else {
            saveChanges.setBackgroundResource(R.drawable.btn_bg_disabled);
        }
    }

    @Override
    public void onClick(View v) {
        Bundle args = new Bundle();
        switch (v.getId()) {
            case R.id.main:
                args.putInt(COLOR, MAIN);
                break;
            case R.id.main_secondary:
                args.putInt(COLOR, MAIN_SECONDARY);
                break;
            case R.id.font:
                args.putInt(COLOR, FONT);
                break;
        }
        ColorPickerFragment fragment = new ColorPickerFragment();
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, fragment)
                .commit();
    }
}
