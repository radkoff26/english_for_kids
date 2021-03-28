package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.adapters.GamesRecyclerViewAdapter;
import com.example.englishforkidsfinal.R;

public class GamesFragment extends Fragment {

    // Declaration of variables
    private RecyclerView rv;
    private GamesRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initializing variables
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        // Initializing view
        rv = (RecyclerView) view.findViewById(R.id.rv);

        // Initializing adapter of Recycler View
        adapter = new GamesRecyclerViewAdapter(getContext());

        // Setting adapter to Recycler View
        rv.setAdapter(adapter);

        return view;
    }
}