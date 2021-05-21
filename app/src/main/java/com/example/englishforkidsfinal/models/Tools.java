package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

// Class that contains tools for application
public class Tools {

    public static void saveToInternalStorage(String name, Bitmap bitmapImage, Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        File path = new File(directory, name + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap loadImageFromStorage(String name, Context context, @Nullable ImageView iv) {
        try {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("images", Context.MODE_PRIVATE);
            File path = new File(directory, name + ".jpg");
            if (iv != null) {
                Picasso.with(context)
                        .load(path)
                        .into(iv);
            }
            return BitmapFactory.decodeStream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static Bitmap loadImageFromStorageFit(String name, Context context, @Nullable ImageView iv) {
        try {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("images", Context.MODE_PRIVATE);
            File path = new File(directory, name + ".jpg");
            if (iv != null) {
                Picasso.with(context)
                        .load(path)
                        .fit()
                        .into(iv);
            }
            return BitmapFactory.decodeStream(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static class CountDown extends AsyncTask<Integer, Integer, Void> {

        private Callable<Void> function;

        public CountDown(Callable<Void> function) {
            this.function = function;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int mSec = integers[0];
            try {
                Thread.sleep(mSec);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            try {
                function.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            super.onPostExecute(v);
        }
    }
}
