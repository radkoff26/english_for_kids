package com.example.englishforkidsfinal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apandroid.colorwheel.ColorWheel;
import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.google.android.material.button.MaterialButton;

import java.util.function.Function;

import kotlin.Unit;
import kotlin.jvm.functions.*;

import static com.example.englishforkidsfinal.fragments.SettingsFragment.*;
import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.*;
import static com.example.englishforkidsfinal.models.contractions.CacheContractions.*;

public class ColorPickerFragment extends Fragment {

    private ColorWheel colorWheel;
    private MaterialButton save;
    private int color = 0;
    private int area;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);

        Bundle args = getArguments();
        area = args.getInt(COLOR);

        colorWheel = view.findViewById(R.id.colorPicker);
        save = view.findViewById(R.id.save);

        colorWheel.setColorChangeListener(integer -> {
            save.setBackgroundColor(integer);
            color = integer;
            return null;
        });

        save.setOnClickListener(v -> {
            SharedPreferences sp = getActivity().getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            switch (area) {
                case MAIN:
                    editor.putInt(CACHE_SETTINGS_COLOR_MAIN, color);
                    break;
                case MAIN_SECONDARY:
                    editor.putInt(CACHE_SETTINGS_COLOR_MAIN_SECONDARY, color);
                    break;
                case FONT:
                    editor.putInt(CACHE_SETTINGS_COLOR_FONT, color);
                    break;
            }
            editor.apply();
            ((MainActivity) getActivity()).updateColors();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new SettingsFragment())
                    .commit();
            ((MainActivity) getActivity()).removeBesidesLast();
        });

        return view;
    }


}