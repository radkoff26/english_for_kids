package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.models.ClientAPI;
import com.example.englishforkidsfinal.models.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LearningFragment extends Fragment {

    private ImageButton start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learning, container, false);

        start = v.findViewById(R.id.start_learning);

        start.setOnClickListener(v1 -> {
            ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.fragment, new MainLearningFragment()).commit();
        });

        return v;
    }
}