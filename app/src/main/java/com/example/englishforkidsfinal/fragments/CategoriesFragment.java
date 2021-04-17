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
import com.example.englishforkidsfinal.adapters.CategoriesRecyclerViewAdapter;
import com.example.englishforkidsfinal.models.Category;

import java.util.ArrayList;
import java.util.Set;

import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CACHE;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CATEGORIES;

public class CategoriesFragment extends Fragment {

    private RecyclerView rv;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        rv = view.findViewById(R.id.categories);

        sp = getActivity().getSharedPreferences(CACHE_CACHE, Context.MODE_PRIVATE);

        Set<String> args = sp.getStringSet(CACHE_CATEGORIES, null);
        ArrayList<Category> categories = new ArrayList<>();

        if (args != null) {

            for (String arg : args) {
                categories.add(new Category(0, arg));
            }

            CategoriesRecyclerViewAdapter adapter = new CategoriesRecyclerViewAdapter(getContext(), categories);
            rv.setAdapter(adapter);
        }

        return view;
    }
}