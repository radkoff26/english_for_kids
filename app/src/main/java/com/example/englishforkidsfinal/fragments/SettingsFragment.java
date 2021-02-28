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
import android.widget.CompoundButton;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;

public class SettingsFragment extends Fragment {

    private AppCompatButton saveChanges;
    private SwitchCompat music;
    private SharedPreferences sp;
    private boolean isAccessed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        sp = ((MainActivity) getActivity()).getSharedPreferences("settings", Context.MODE_PRIVATE);

        saveChanges = view.findViewById(R.id.save_changes);
        music = view.findViewById(R.id.switch_music);

        isAccessed = sp.getBoolean("music", true);

        saveChanges.setClickable(false);
        saveChanges.setFocusable(false);
        saveChanges.setBackgroundColor(getResources().getColor(R.color.disabled_button));

        music.setChecked(isAccessed);

        music.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked != isAccessed) {
                saveChanges.setClickable(true);
                saveChanges.setFocusable(true);
                saveChanges.setBackgroundColor(getResources().getColor(R.color.main));
            } else {
                saveChanges.setClickable(false);
                saveChanges.setFocusable(false);
                saveChanges.setBackgroundColor(getResources().getColor(R.color.disabled_button));
            }
        });

        saveChanges.setOnClickListener((View.OnClickListener) v -> {
            SharedPreferences sp = ((MainActivity) getActivity()).getSharedPreferences("settings", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("music", music.isChecked());
            editor.apply();

            ((MainActivity) getActivity()).nextTrack();
            ((MainActivity) getActivity()).onBackPressed();
        });

        return view;
    }
}