package com.example.englishforkidsfinal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;

import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_SETTINGS;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_SETTINGS_MUSIC;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_SETTINGS_MUSIC_DEFAULT;

public class SettingsFragment extends Fragment {

    // Declaration of variables
    private AppCompatButton saveChanges;
    private SwitchCompat music;
    private SharedPreferences sp;
    private boolean isTurned;

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
            saveChanges.setBackgroundColor(getResources().getColor(R.color.main));
        } else {
            saveChanges.setBackgroundColor(getResources().getColor(R.color.disabled_button));
        }
    }
}