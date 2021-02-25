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
import com.example.englishforkidsfinal.activities.GameSpace;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.activities.SpeakRightGame;
import com.example.englishforkidsfinal.models.Game;

import java.util.ArrayList;

public class AlphabetRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Game> games;
    private LayoutInflater inflater;
    private Context context;

    public AlphabetRecyclerViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.games = new ArrayList<>();
        this.context = context;

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

        if (position == 0) {
            ((AlphabetViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GameSpace.class);
                    ((MainActivity) context).startActivity(intent);
                }
            });
        } else if (position == 1) {
            ((AlphabetViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SpeakRightGame.class);
                    ((MainActivity) context).startActivity(intent);
                }
            });
        } else if (position == 2) {
            ((AlphabetViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DrawingGame.class);
                    ((MainActivity) context).startActivity(intent);
                }
            });
        } else if (position == 3) {
            ((AlphabetViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FindRightPictureGame.class);
                    ((MainActivity) context).startActivity(intent);
                }
            });
        }

        ((AlphabetViewHolder) holder).tv.setText(game.getTitle());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
