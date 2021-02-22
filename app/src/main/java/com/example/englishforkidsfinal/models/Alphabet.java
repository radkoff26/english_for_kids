package com.example.englishforkidsfinal.models;

import com.example.englishforkidsfinal.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Alphabet {

    public static final String[] ALPHABET = "a b c d e f g h i j k l m n o p q r s t u v w x y z".toLowerCase().split(" ");
    public static final String[] ALPHABET_UPPER = "a b c d e f g h i j k l m n o p q r s t u v w x y z".toUpperCase().split(" ");
    public static final ArrayList<AlphabetLetter> letters = new ArrayList<>();

    static {
        letters.add(
                new AlphabetLetter("a", R.anim.letter_rotating, R.drawable.a)
        );
        letters.add(
                new AlphabetLetter("b", R.anim.letter_rotating, R.drawable.b)
        );
        letters.add(
                new AlphabetLetter("c", R.anim.letter_rotating, R.drawable.c)
        );
        letters.add(
                new AlphabetLetter("d", R.anim.letter_rotating, R.drawable.d)
        );
        letters.add(
                new AlphabetLetter("e", R.anim.letter_rotating, R.drawable.e)
        );
        letters.add(
                new AlphabetLetter("f", R.anim.letter_rotating, R.drawable.f)
        );
        letters.add(
                new AlphabetLetter("g", R.anim.letter_rotating, R.drawable.g)
        );
        letters.add(
                new AlphabetLetter("h", R.anim.letter_rotating, R.drawable.h)
        );
        letters.add(
                new AlphabetLetter("i", R.anim.letter_rotating, R.drawable.i)
        );
        letters.add(
                new AlphabetLetter("j", R.anim.letter_rotating, R.drawable.j)
        );
        letters.add(
                new AlphabetLetter("k", R.anim.letter_rotating, R.drawable.k)
        );
        letters.add(
                new AlphabetLetter("l", R.anim.letter_rotating, R.drawable.l)
        );
        letters.add(
                new AlphabetLetter("m", R.anim.letter_rotating, R.drawable.m)
        );
        letters.add(
                new AlphabetLetter("n", R.anim.letter_rotating, R.drawable.n)
        );
        letters.add(
                new AlphabetLetter("o", R.anim.letter_rotating, R.drawable.o)
        );
        letters.add(
                new AlphabetLetter("p", R.anim.letter_rotating, R.drawable.p)
        );
        letters.add(
                new AlphabetLetter("q", R.anim.letter_rotating, R.drawable.q)
        );
        letters.add(
                new AlphabetLetter("r", R.anim.letter_rotating, R.drawable.r)
        );
        letters.add(
                new AlphabetLetter("s", R.anim.letter_rotating, R.drawable.s)
        );
        letters.add(
                new AlphabetLetter("t", R.anim.letter_rotating, R.drawable.t)
        );
        letters.add(
                new AlphabetLetter("u", R.anim.letter_rotating, R.drawable.u)
        );
        letters.add(
                new AlphabetLetter("v", R.anim.letter_rotating, R.drawable.v)
        );
        letters.add(
                new AlphabetLetter("w", R.anim.letter_rotating, R.drawable.w)
        );
        letters.add(
                new AlphabetLetter("x", R.anim.letter_rotating, R.drawable.x)
        );
        letters.add(
                new AlphabetLetter("y", R.anim.letter_rotating, R.drawable.y)
        );
        letters.add(
                new AlphabetLetter("z", R.anim.letter_rotating, R.drawable.z)
        );
    }

    public static char getRandomChar() {
        List<String> list = Arrays.asList(ALPHABET);
        int random = (int) (Math.random()*(list.size()));
        return list.get(random).trim().charAt(0);
    }

    public static AlphabetLetter getByWord(String letter) {
        for (AlphabetLetter mLetter : letters) {
            if (mLetter.getLetter().toLowerCase().equals(letter.toLowerCase())) {
                return mLetter;
            }
        }
        return null;
    }

    public static List<String> getUpperAlphabet() {
        return  Arrays.asList(ALPHABET_UPPER);
    }
}
