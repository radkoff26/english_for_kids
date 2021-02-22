package com.example.englishforkidsfinal.models;

import com.example.englishforkidsfinal.R;

import java.util.Arrays;
import java.util.List;

public class TestModels {
    public static List<Word> words = Arrays.asList(
            new Word(0, "Dog", "Собака", R.drawable.dog),
            new Word( 0, "Pig", "Свинья", R.drawable.pig),
            new Word( 0, "Rabbit", "Заяц", R.drawable.rabbit),
            new Word( 0, "Cow", "Корова", R.drawable.cow),
            new Word(0, "Frog", "Лягушка", R.drawable.frog)
    );

    public static Word randomWord() {
        return words.get((int) (Math.random() * words.size()));
    }
}
