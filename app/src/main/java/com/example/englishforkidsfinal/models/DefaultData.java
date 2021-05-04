package com.example.englishforkidsfinal.models;

import com.example.englishforkidsfinal.models.db_models.Word;

import java.util.ArrayList;

public class DefaultData {
    public static ArrayList<Word> words = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();
    static {
        words.add(
                new Word(
                        1,
                        "pig",
                        "свинья",
                        "https://www.pinclipart.com/picdir/big/11-118114_picture-free-dirty-pigs-clipart-transparent-background-pig.png",
                        1,
                        "животные"
                )
        );
        words.add(
                new Word(
                        2,
                        "dog",
                        "собака",
                        "https://i.ya-webdesign.com/images/dog-vector-png-1.png",
                        1,
                        "животные"
                )
        );
        words.add(
                new Word(
                        3,
                        "cow",
                        "корова",
                        "https://pixy.org/src/467/4674051.png",
                        1,
                        "животные"
                )
        );
        words.add(
                new Word(
                        4,
                        "frog",
                        "лягушка",
                        "https://i.ya-webdesign.com/images/cartoon-frog-png-3.png",
                        1,
                        "животные"
                )
        );
        words.add(
                new Word(
                        5,
                        "rabbit",
                        "заяц",
                        "https://www.vippng.com/png/full/64-644186_bunny-clip-art-cartoon-images-rabbit-clip-art.png",
                        1,
                        "животные"
                )
        );
        words.add(
                new Word(
                        6,
                        "crocodile",
                        "крокодил",
                        "https://i.ya-webdesign.com/images/flat-clip-alligator-3.png",
                        2,
                        "животные"
                )
        );
        words.add(
                new Word(
                        7,
                        "giraffe",
                        "жираф",
                        "https://webcomicms.net/sites/default/files/clipart/170538/cute-cartoon-giraffe-pictures-170538-9274298.png",
                        2,
                        "животные"
                )
        );
        words.add(
                new Word(
                        8,
                        "elephant",
                        "слон",
                        "https://pixy.org/src/131/1314499.png",
                        2,
                        "животные"
                )
        );
        words.add(
                new Word(
                        9,
                        "monkey",
                        "обезьяна",
                        "https://www.clipartmax.com/png/full/85-858559_baby-monkeys-chimpanzee-cartoon-clip-art-cartoon-picture-of-monkey.png",
                        2,
                        "животные"
                )
        );
        words.add(
                new Word(
                        10,
                        "snake",
                        "змея",
                        "https://img.pngio.com/snake-cartoon-png-97-images-in-collection-page-3-snake-cartoon-png-3283_4850.png",
                        2,
                        "животные"
                )
        );
        categories.add(
                new Category(
                        1,
                        "животные"
                )
        );
    }
}
