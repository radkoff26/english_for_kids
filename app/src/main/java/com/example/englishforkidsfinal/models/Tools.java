package com.example.englishforkidsfinal.models;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

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

}
