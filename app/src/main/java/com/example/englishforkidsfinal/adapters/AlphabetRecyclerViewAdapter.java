package com.example.englishforkidsfinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.models.Alphabet;
import com.example.englishforkidsfinal.models.AlphabetLetter;
import com.example.englishforkidsfinal.models.view_models.SpeechImageView;

import java.util.List;

public class AlphabetRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AlphabetLetter> strings = Alphabet.letters;
    private Context context;
    private LayoutInflater inflater;

    public AlphabetRecyclerViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    class AlphabetViewHolder extends RecyclerView.ViewHolder {

        final SpeechImageView iv;

        public AlphabetViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.alphabet_item, parent, false);

        return new AlphabetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AlphabetLetter element = strings.get(position);

        ((AlphabetViewHolder) holder).iv.setImageResource(element.getRes());
        ((AlphabetViewHolder) holder).iv.setWordToSpeak(element.getLetter());
        ((AlphabetViewHolder) holder).iv.setAnimation(R.anim.letter_up_down);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }
}
