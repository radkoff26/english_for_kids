package com.example.englishforkidsfinal.models;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.db_models.Word;

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

    public static Picture getRandomPicture() {
        return pictures.get((int) (Math.random() * pictures.size()));
    }
}
