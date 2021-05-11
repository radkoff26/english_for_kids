package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.adapters.CategoriesRecyclerViewAdapter;
import com.example.englishforkidsfinal.db.CategoryDataBase;
import com.example.englishforkidsfinal.models.db_models.Category;

import java.util.List;

public class CategoriesFragment extends Fragment {

    private RecyclerView rv;
    private CategoryDataBase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        rv = view.findViewById(R.id.categories);


        db = new CategoryDataBase(getContext());

        List<Category> categories = db.getCategories();

        CategoriesRecyclerViewAdapter adapter = new CategoriesRecyclerViewAdapter(getContext(), categories);
        rv.setAdapter(adapter);

        return view;
    }
}