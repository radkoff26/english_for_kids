package com.example.englishforkidsfinal.models;

import com.example.englishforkidsfinal.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Alphabet {

    // Initializing final arrays and list
    public static final String[] ALPHABET = "a b c d e f g h i j k l m n o p q r s t u v w x y z".toLowerCase().split(" ");
    public static final String[] ALPHABET_UPPER = "a b c d e f g h i j k l m n o p q r s t u v w x y z".toUpperCase().split(" ");
    public static final ArrayList<AlphabetLetter> letters = new ArrayList<>();

    // Putting test data into list
    static {
        letters.add(
                new AlphabetLetter("a", R.anim.letter_rotation, R.drawable.a)
        );
        letters.add(
                new AlphabetLetter("b", R.anim.letter_rotation, R.drawable.b)
        );
        letters.add(
                new AlphabetLetter("c", R.anim.letter_rotation, R.drawable.c)
        );
        letters.add(
                new AlphabetLetter("d", R.anim.letter_rotation, R.drawable.d)
        );
        letters.add(
                new AlphabetLetter("e", R.anim.letter_rotation, R.drawable.e)
        );
        letters.add(
                new AlphabetLetter("f", R.anim.letter_rotation, R.drawable.f)
        );
        letters.add(
                new AlphabetLetter("g", R.anim.letter_rotation, R.drawable.g)
        );
        letters.add(
                new AlphabetLetter("h", R.anim.letter_rotation, R.drawable.h)
        );
        letters.add(
                new AlphabetLetter("i", R.anim.letter_rotation, R.drawable.i)
        );
        letters.add(
                new AlphabetLetter("j", R.anim.letter_rotation, R.drawable.j)
        );
        letters.add(
                new AlphabetLetter("k", R.anim.letter_rotation, R.drawable.k)
        );
        letters.add(
                new AlphabetLetter("l", R.anim.letter_rotation, R.drawable.l)
        );
        letters.add(
                new AlphabetLetter("m", R.anim.letter_rotation, R.drawable.m)
        );
        letters.add(
                new AlphabetLetter("n", R.anim.letter_rotation, R.drawable.n)
        );
        letters.add(
                new AlphabetLetter("o", R.anim.letter_rotation, R.drawable.o)
        );
        letters.add(
                new AlphabetLetter("p", R.anim.letter_rotation, R.drawable.p)
        );
        letters.add(
                new AlphabetLetter("q", R.anim.letter_rotation, R.drawable.q)
        );
        letters.add(
                new AlphabetLetter("r", R.anim.letter_rotation, R.drawable.r)
        );
        letters.add(
                new AlphabetLetter("s", R.anim.letter_rotation, R.drawable.s)
        );
        letters.add(
                new AlphabetLetter("t", R.anim.letter_rotation, R.drawable.t)
        );
        letters.add(
                new AlphabetLetter("u", R.anim.letter_rotation, R.drawable.u)
        );
        letters.add(
                new AlphabetLetter("v", R.anim.letter_rotation, R.drawable.v)
        );
        letters.add(
                new AlphabetLetter("w", R.anim.letter_rotation, R.drawable.w)
        );
        letters.add(
                new AlphabetLetter("x", R.anim.letter_rotation, R.drawable.x)
        );
        letters.add(
                new AlphabetLetter("y", R.anim.letter_rotation, R.drawable.y)
        );
        letters.add(
                new AlphabetLetter("z", R.anim.letter_rotation, R.drawable.z)
        );
    }

    // Method to get random char from the alphabet array
    public static char getRandomChar() {
        List<String> list = Arrays.asList(ALPHABET);
        int random = (int) (Math.random()*(list.size()));
        return list.get(random).trim().charAt(0);
    }

    // Method to get AlphabetLetter by the given letter
    public static AlphabetLetter getByWord(String letter) {
        for (AlphabetLetter mLetter : letters) {
            if (mLetter.getLetter().toLowerCase().equals(letter.toLowerCase())) {
                return mLetter;
            }
        }
        return null;
    }

    // Method to get upper alphabet
    public static List<String> getUpperAlphabet() {
        return  Arrays.asList(ALPHABET_UPPER);
    }
}
