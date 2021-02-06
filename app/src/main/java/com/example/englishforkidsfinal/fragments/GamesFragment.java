package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishforkidsfinal.adapters.AlphabetRecyclerViewAdapter;
import com.example.englishforkidsfinal.R;

public class GamesFragment extends Fragment {

    private RecyclerView rv;
    private AlphabetRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        rv = (RecyclerView) view.findViewById(R.id.rv);

        adapter = new AlphabetRecyclerViewAdapter(getContext());

        rv.setAdapter(adapter);

        return view;
    }
}