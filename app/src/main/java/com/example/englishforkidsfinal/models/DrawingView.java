package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;

import java.util.Map;

public class DrawingView extends View {

    private Paint paint;
    private Path path;
    private PathList paths;
    private float x, y;
    private boolean flag = true;
    private Picture picture;

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paths = new PathList();

        path = new Path();

        paths.put(path, new Paint());
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
        setBackground(getResources().getDrawable(picture.getResource()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Map.Entry<Path, Paint> entry : paths.entrySet()) {
            canvas.drawPath(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path.moveTo(x, y);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(x, y);
            flag = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            path = new Path();
            paths.put(path, paint);
        }

        invalidate();
        return true;
    }

    public void setColor(int color) {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20f);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paths.removeLast();
        paths.put(path, paint);
    }

    public void back() {
        paths.removePreLast();
        invalidate();
    }
}
