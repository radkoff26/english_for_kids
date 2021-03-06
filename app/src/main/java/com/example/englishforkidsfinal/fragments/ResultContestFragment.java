package com.example.englishforkidsfinal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.DataBase;

import static com.example.englishforkidsfinal.models.TestModels.words;


public class ResultContestFragment extends Fragment {

    // Declaration of variables
    private int result, prev;
    private TextView score;
    private SharedPreferences sp;
    private AppCompatButton finish;
    private SharedPreferences.Editor editor;
    private DataBase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialization of main view
        View view = inflater.inflate(R.layout.fragment_result_contest, container, false);

        // Initialization of SharedPreferences
        sp = getActivity().getSharedPreferences("contest", Context.MODE_PRIVATE);

        // Initialization of Database
        db = new DataBase(getContext());

        // Receiving previous number of group of words from SharedPreferences
        prev = sp.getInt("group", 1);

        // Initializing Editor of SharedPreferences
        editor = sp.edit();

        // Initializing bundle of given arguments
        Bundle args = getArguments();

        // Receiving result argument from bundle from previous fragment
        result = args.getInt("result", 0);

        // Initializing views
        score = view.findViewById(R.id.result);
        finish = view.findViewById(R.id.finish);

        // Setting text to the Text View
        score.setText(result + "/5");

        // Checking
        // If the result is the best, it will be shown in Toast and words inserted into Database
        if (result == 5) {
            editor.putInt("group", prev + 1);
            Toast.makeText(getContext(), "You have successfully passed contest and opened words of group number " + (prev + 1) + "!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < words.size(); i++) {
                if (words.get(i).getGr() == prev) {
                    db.add(words.get(i));
                }
            }
        }

        // Applying all changes in SharedPreferences
        editor.apply();

        // Setting OnClickListener to the finish button to go to Home Fragment by invoking onBackPressed method
        finish.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        return view;
    }

    @Override
    public void onDestroy() {
        // Closing Database if fragment is going to be closed
        db.close();
        super.onDestroy();
    }
}