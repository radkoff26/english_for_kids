package com.example.englishforkidsfinal.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.fragments.CategoryFragment;
import com.example.englishforkidsfinal.models.db_models.Category;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.CATEGORY;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Declaration of variables
    private List<Category> categories;
    private LayoutInflater inflater;
    private Context context;

    // Constructor
    public CategoriesRecyclerViewAdapter(Context context, List<Category> categories) {
        this.inflater = LayoutInflater.from(context);
        this.categories = categories;
        this.context = context;
    }

    // Customized ViewHolder for Recycler View
    class CategoryViewHolder extends RecyclerView.ViewHolder {

        final MaterialTextView tv;
        final FrameLayout fl;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.category_title);
            fl = itemView.findViewById(R.id.fl);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_item, parent, false);

        return new CategoriesRecyclerViewAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Category category = categories.get(position);

        ((CategoryViewHolder) holder).tv.setText(category.getTitle().toUpperCase());
        ((CategoryViewHolder) holder).tv.setTypeface(MainActivity.typeface);
        ((CategoryViewHolder) holder).fl.setOnClickListener(v -> {
            CategoryFragment categoryFragment = new CategoryFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable(CATEGORY, category);
            categoryFragment.setArguments(arguments);
            ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, categoryFragment)
                    .commit();
            ((MainActivity) context).removeBesidesLast();
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}

