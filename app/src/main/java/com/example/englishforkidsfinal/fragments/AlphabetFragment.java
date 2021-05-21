package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.AlphabetDataBase;
import com.example.englishforkidsfinal.models.RestAlphabetLetter;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.view_models.AlphabetLetterView;

import java.util.List;

import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.LETTER;

public class AlphabetFragment extends Fragment {

    private int[] ids = new int[] {
            R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f,
            R.id.g, R.id.h, R.id.i, R.id.j, R.id.k, R.id.l,
            R.id.m, R.id.n, R.id.o, R.id.p, R.id.q, R.id.r,
            R.id.s, R.id.t, R.id.u, R.id.v, R.id.w, R.id.x,
            R.id.y, R.id.z
    };
    private AlphabetLetterView letterView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alphabet, container, false);

        AlphabetDataBase db = new AlphabetDataBase(getContext());

        List<RestAlphabetLetter> letters = db.getAlphabet();

        for (int i = 0; i < ids.length; i++) {
            letterView = view.findViewById(ids[i]);
            letterView.setLetter(letters.get(i));
            Tools.loadImageFromStorage(letters.get(i).getUri(), getContext(), letterView);
            letterView.setOnClickListener(v -> {
                AlphabetLetterFragment fragment = new AlphabetLetterFragment();
                Bundle args = new Bundle();
                args.putSerializable(LETTER, ((AlphabetLetterView) v).getLetter());
                fragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, fragment)
                        .commit();
            });
        }

        return view;
    }
}