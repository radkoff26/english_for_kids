package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.englishforkidsfinal.R;

public class DrawingSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    // Declaration of variables
    private DrawingThread thread;
    private Path path;
    private Paint paint;
    private float x, y;
    private Bitmap bitmap;

    // Constructor
    public DrawingSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        // Initialization of necessary variables
        path = new Path();
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.YELLOW);
        Canvas canvas = holder.lockCanvas();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle);

        // Drawing Bitmap picture
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        holder.unlockCanvasAndPost(canvas);

        // Drawing Thread initialization
        thread = new DrawingThread(holder, bitmap);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // Waiting until thread finish their work
        thread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException ignore) {}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Initializing x and y coordinates
        x = event.getX();
        y = event.getY();

        // Watching touches to draw path
        if (bitmap != null) {
            if (x < bitmap.getWidth() && y < bitmap.getHeight()) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        path.moveTo(x, y);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        path.lineTo(x, y);
                        break;
                }
            }
        }

        // Setting path to draw in thread
        thread.setPath(path);

        // Updating Surface View
        invalidate();
        return true;
    }
}
