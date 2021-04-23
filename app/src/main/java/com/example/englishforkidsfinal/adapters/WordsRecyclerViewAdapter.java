package com.example.englishforkidsfinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.models.view_models.SpeechImageView;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.db_models.Word;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class WordsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Declaration of variables
    private List<Word> words;
    private LayoutInflater inflater;
    private Context context;

    // Constructor
    public WordsRecyclerViewAdapter(Context context, List<Word> words) {
        this.inflater = LayoutInflater.from(context);
        this.words = words;
        this.context = context;
    }

    // Customized ViewHolder for Recycler View
    class WordsViewHolder extends RecyclerView.ViewHolder {

        final ImageView iv;
        final MaterialTextView eng;
        final MaterialTextView ru;
        final SpeechImageView sound;

        public WordsViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            eng = itemView.findViewById(R.id.eng);
            ru = itemView.findViewById(R.id.ru);
            sound = itemView.findViewById(R.id.sound);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.word_item, parent, false);

        return new WordsRecyclerViewAdapter.WordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Word word = words.get(position);

        Tools.loadImageFromStorageFit(word.getEng(), context, ((WordsViewHolder) holder).iv);

        ((WordsViewHolder) holder).eng.setText(word.getEng().toUpperCase());
        ((WordsViewHolder) holder).eng.setTypeface(MainActivity.typeface);
        ((WordsViewHolder) holder).ru.setText(word.getRu().toUpperCase());
        ((WordsViewHolder) holder).ru.setTypeface(MainActivity.typeface);
        ((WordsViewHolder) holder).sound.setWordToSpeak(word.getEng());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }
}
