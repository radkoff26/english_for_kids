package com.example.englishforkidsfinal.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.DrawingGame;
import com.example.englishforkidsfinal.activities.FindRightPictureGame;
import com.example.englishforkidsfinal.activities.CollectWord;
import com.example.englishforkidsfinal.activities.SpeakRightGame;
import com.example.englishforkidsfinal.models.Game;

import java.util.ArrayList;

public class AlphabetRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Declaration of variables
    private ArrayList<Game> games;
    private LayoutInflater inflater;
    private Context context;

    // Constructor
    public AlphabetRecyclerViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.games = new ArrayList<>();
        this.context = context;

        // Test data for Recycler View
        games.add(
                new Game("Find All Letters")
        );

        games.add(
                new Game("Speak Right")
        );

        games.add(
                new Game("Draw it!")
        );

        games.add(
                new Game("Where Is Right Picture?")
        );
    }

    // Customized ViewHolder for Recycler View
    class AlphabetViewHolder extends RecyclerView.ViewHolder {

        final TextView tv;

        public AlphabetViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_tv);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.game_item, parent, false);

        return new AlphabetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Game game = games.get(position);

        // Setting OnClickListeners to the buttons on Recycler View
        if (position == 0) {
            ((AlphabetViewHolder) holder).tv.setOnClickListener(v -> {
                Intent intent = new Intent(context, CollectWord.class);
                context.startActivity(intent);
            });
        } else if (position == 1) {
            ((AlphabetViewHolder) holder).tv.setOnClickListener(v -> {
                Intent intent = new Intent(context, SpeakRightGame.class);
                context.startActivity(intent);
            });
        } else if (position == 2) {
            ((AlphabetViewHolder) holder).tv.setOnClickListener(v -> {
                Intent intent = new Intent(context, DrawingGame.class);
                context.startActivity(intent);
            });
        } else if (position == 3) {
            ((AlphabetViewHolder) holder).tv.setOnClickListener(v -> {
                Intent intent = new Intent(context, FindRightPictureGame.class);
                context.startActivity(intent);
            });
        }

        ((AlphabetViewHolder) holder).tv.setText(game.getTitle());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
