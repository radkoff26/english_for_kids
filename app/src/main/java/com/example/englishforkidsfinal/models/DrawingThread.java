package com.example.englishforkidsfinal.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceHolder;

public class DrawingThread extends Thread {

    private SurfaceHolder surfaceHolder;

    private Paint paint = new Paint(Paint.DITHER_FLAG);

    private volatile boolean running = true;

    private Path path;

    private Bitmap bitmap;

    public DrawingThread(SurfaceHolder surfaceHolder, Bitmap btm) {
        this.surfaceHolder = surfaceHolder;
        bitmap = btm;
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(50f);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawColor(Color.WHITE);
                    canvas.drawBitmap(bitmap, 0, 0, paint);
                    if (path != null) {
                        canvas.drawPath(path, paint);
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
