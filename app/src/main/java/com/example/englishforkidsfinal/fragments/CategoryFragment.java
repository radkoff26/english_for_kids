package com.example.englishforkidsfinal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.adapters.WordsRecyclerViewAdapter;
import com.example.englishforkidsfinal.db.LearnedWordsDataBase;
import com.example.englishforkidsfinal.models.Category;

import static com.example.englishforkidsfinal.models.ArgumentsContractions.CATEGORY;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CACHE;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST_GROUP;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CONTEST_GROUP_DEFAULT;


public class CategoryFragment extends Fragment {

    private RecyclerView rv;
    private SharedPreferences sp;
    private int group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        sp = getActivity().getSharedPreferences(CACHE_CONTEST, Context.MODE_PRIVATE);
        group = sp.getInt(CACHE_CONTEST_GROUP, CACHE_CONTEST_GROUP_DEFAULT);

        rv = view.findViewById(R.id.words);

        Bundle arguments = getArguments();

        if (arguments.getSerializable(CATEGORY) != null && group != 0) {
            LearnedWordsDataBase db = new LearnedWordsDataBase(getContext());
            WordsRecyclerViewAdapter adapter = new WordsRecyclerViewAdapter(getContext(), db.getWords(((Category) arguments.getSerializable(CATEGORY)).getTitle()));
            rv.setAdapter(adapter);
            db.close();
        }

        return view;
    }
}