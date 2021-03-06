package com.example.englishforkidsfinal.models;

import com.example.englishforkidsfinal.R;

import java.util.Arrays;
import java.util.List;

public class TestModels {
    // Test data
    public static List<Word> words = Arrays.asList(
            new Word(0, "Dog", "Собака", R.drawable.dog, 1),
            new Word( 0, "Pig", "Свинья", R.drawable.pig, 1),
            new Word( 0, "Rabbit", "Заяц", R.drawable.rabbit, 1),
            new Word( 0, "Cow", "Корова", R.drawable.cow, 1),
            new Word(0, "Frog", "Лягушка", R.drawable.frog, 1),
            new Word(0, "Elephant", "Слон", R.drawable.elephant, 2),
            new Word( 0, "Giraffe", "Жираф", R.drawable.giraffe2, 2),
            new Word( 0, "Crocodile", "Крокодил", R.drawable.crocodile, 2),
            new Word( 0, "Monkey", "Обезьяна", R.drawable.monkey, 2),
            new Word(0, "Snake", "Змея", R.drawable.snake, 2)
    );

    // Method to get random word of list
    public static Word randomWord() {
        return words.get((int) (Math.random() * words.size()));
    }
}
