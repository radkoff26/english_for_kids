package com.example.englishforkidsfinal.models;

import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

// Additional class for DrawingView that contains all paths to draw and simplifies work with its removing and appending
public class PathList extends HashMap<Path, Paint> {

    private ArrayList<Object[]> sequence = new ArrayList<>();

    @Nullable
    @Override
    public Paint put(Path key, Paint value) {
        sequence.add(new Object[]{key, value});
        return super.put(key, value);
    }

    public void removePreLast() {
        Path path = getPreLastPath();
        if (path != null) {
            remove(path);
            sequence.remove(sequence.size() - 2);
        }
    }

    public void removeLast() {
        Path path = getLastPath();
        if (path != null) {
            remove(path);
            sequence.remove(sequence.size() - 1);
        }
    }

    public Path getPreLastPath() {
        return sequence.size() <= 1 ? null : (Path) sequence.get(sequence.size() - 2)[0];
    }

    public Path getLastPath() {
        return sequence.size() == 0 ? null : (Path) sequence.get(sequence.size() - 1)[0];
    }
}
