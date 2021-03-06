package com.example.englishforkidsfinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.models.ColorsPaint;

import java.util.List;

public class DrawingAdapter extends ArrayAdapter<ColorsPaint> {

    // Declaration of variables
    private List<ColorsPaint> colors;

    public DrawingAdapter(@NonNull Context context, int resource, @NonNull List<ColorsPaint> objects) {
        super(context, resource, objects);
        // Initialization of variables
        colors = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Setting color button to ListView
        ColorsPaint color = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.color_item, parent, false);
        }
        FrameLayout btn = convertView.findViewById(R.id.color);
        TextView tv = convertView.findViewById(R.id.text);
        tv.setText(color.getNumber() + "");
        tv.setTypeface(MainActivity.typeface);
        btn.setBackgroundColor(color.getIntColor());

        return convertView;
    }
}
