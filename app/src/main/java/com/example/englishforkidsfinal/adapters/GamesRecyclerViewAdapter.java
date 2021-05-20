package com.example.englishforkidsfinal.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

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

        // Data for Recycler View
        games.add(
                new Game(R.drawable.collect_word_bg, CollectWord.class)
        );

        games.add(
                new Game(R.drawable.speak_right_bg, SpeakRightGame.class)
        );

        games.add(
                new Game(R.drawable.drawing_bg, DrawingGame.class)
        );

        games.add(
                new Game(R.drawable.find_picture_bg, FindRightPictureGame.class)
        );
    }

    // Customized ViewHolder for Recycler View
    class AlphabetViewHolder extends RecyclerView.ViewHolder {

        final ImageView iv;
        final ImageButton iv_btn;

        public AlphabetViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            iv_btn = itemView.findViewById(R.id.play);
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

        ((AlphabetViewHolder) holder).iv.setImageDrawable(context.getResources().getDrawable(game.getRes()));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
