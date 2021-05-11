package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.CollectWord;
import com.example.englishforkidsfinal.models.db_models.Word;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import static com.example.englishforkidsfinal.models.contractions.CacheContractions.*;

// Class that contains tools for application
public class Tools {

    // Method to load image by url
    public static void loadImageByURLToTheImageView(String url, ImageView imageView, Context context) {
        // Invoking execute method to load image by url
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }

    // AsyncTask class to receive image by url and display in given ImageView
    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

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

    public static void setBackgroundMain(View view, Context context) {
        SharedPreferences sp = context.getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);
        view.setBackgroundColor(sp.getInt(CACHE_SETTINGS_COLOR_MAIN, CACHE_SETTINGS_COLOR_MAIN_DEFAULT));
    }

    public static void setBackgroundMainSecondary(View view, Context context) {
        SharedPreferences sp = context.getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);
        view.setBackgroundColor(sp.getInt(CACHE_SETTINGS_COLOR_MAIN_SECONDARY, CACHE_SETTINGS_COLOR_MAIN_SECONDARY_DEFAULT));
    }

    public static void setFont(TextView view, Context context) {
        SharedPreferences sp = context.getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);
        view.setTextColor(sp.getInt(CACHE_SETTINGS_COLOR_FONT, CACHE_SETTINGS_COLOR_FONT_DEFAULT));
    }

    public static int getBackgroundMain(Context context) {
        SharedPreferences sp = context.getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);
        return sp.getInt(CACHE_SETTINGS_COLOR_MAIN, CACHE_SETTINGS_COLOR_MAIN_DEFAULT);
    }

    public static int getBackgroundMainSecondary(Context context) {
            SharedPreferences sp = context.getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);
            return sp.getInt(CACHE_SETTINGS_COLOR_MAIN_SECONDARY, CACHE_SETTINGS_COLOR_MAIN_SECONDARY_DEFAULT);
    }

    public static int getFont(Context context) {
        SharedPreferences sp = context.getSharedPreferences(CACHE_SETTINGS, Context.MODE_PRIVATE);
        return sp.getInt(CACHE_SETTINGS_COLOR_FONT, CACHE_SETTINGS_COLOR_FONT_DEFAULT);
    }
}
