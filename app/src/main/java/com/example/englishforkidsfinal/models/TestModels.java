package com.example.englishforkidsfinal.models;

import com.example.englishforkidsfinal.R;

import java.util.Arrays;
import java.util.List;

public class TestModels {
    public static List<Word> words = Arrays.asList(
            new Word(0, "dog", "собака", R.drawable.dog),
            new Word( 0, "pig", "свинья", R.drawable.pig),
//            new Word( 0, "rabbit", "заяц", R.drawable.rabbit),
//            new Word( 0, "cow", "корова", R.drawable.cow),
            new Word(0, "frog", "лягушка", R.drawable.frog)
    );
}
