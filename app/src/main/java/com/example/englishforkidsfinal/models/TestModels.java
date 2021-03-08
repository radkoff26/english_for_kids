package com.example.englishforkidsfinal.models;

import com.example.englishforkidsfinal.R;

import java.util.Arrays;
import java.util.List;

public class TestModels {
    // Test picture
    private static final List<Picture> pictures = Arrays.asList(
            new Picture(
                    R.drawable.test_model,
                    Arrays.asList(
                            new ColorsPaint("#d43535", 1),
                            new ColorsPaint("#00cb56", 2)
                    )
            ),
            new Picture(
                    R.drawable.cat_to_draw,
                    Arrays.asList(
                            new ColorsPaint("#ff0000", 1),
                            new ColorsPaint("#ffa200", 2),
                            new ColorsPaint("#ffea00", 3)
                    )
            )
    );

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

    // Test pictures
    public static Picture getRandomPicture() {
        int i = (int) (Math.random() * pictures.size());
        return pictures.get(i);
    }

    // Method to get random word of list
    public static Word randomWord() {
        return words.get((int) (Math.random() * words.size()));
    }
}
