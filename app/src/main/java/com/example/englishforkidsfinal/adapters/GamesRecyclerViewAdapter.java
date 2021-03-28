package com.example.englishforkidsfinal.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.CollectWord;
import com.example.englishforkidsfinal.activities.DrawingGame;
import com.example.englishforkidsfinal.activities.FindRightPictureGame;
import com.example.englishforkidsfinal.activities.SpeakRightGame;
import com.example.englishforkidsfinal.models.Game;

import java.util.ArrayList;

public class GamesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Declaration of variables
    private ArrayList<Game> games;
    private LayoutInflater inflater;
    private Context context;

    // Constructor
    public GamesRecyclerViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.games = new ArrayList<>();
        this.context = context;

        // Test data for Recycler View
        games.add(
                new Game(R.drawable.make_word_game, CollectWord.class)
        );

        games.add(
                new Game(R.drawable.speak_right_game, SpeakRightGame.class)
        );

        games.add(
                new Game(R.drawable.drawing_game, DrawingGame.class)
        );

        games.add(
                new Game(R.drawable.find_right_picture_game, FindRightPictureGame.class)
        );
    }

    // Customized ViewHolder for Recycler View
    class AlphabetViewHolder extends RecyclerView.ViewHolder {

        final ImageButton iv_btn;

        public AlphabetViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_btn = itemView.findViewById(R.id.iv_btn);
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
        ((AlphabetViewHolder) holder).iv_btn.setOnClickListener(v -> {
            context.startActivity(new Intent(context, game.getGameClass()));
        });

        ((AlphabetViewHolder) holder).iv_btn.setImageDrawable(context.getResources().getDrawable(game.getRes()));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
