package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.AllWordsDataBase;
import com.example.englishforkidsfinal.db.LearnedWordsDataBase;
import com.example.englishforkidsfinal.models.ClientAPI;
import com.example.englishforkidsfinal.models.DefaultData;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.db_models.Word;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_CACHE;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_IS_LOADED;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_IS_LOADED_DEFAULT;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_WORD_IS_LOADED;
import static com.example.englishforkidsfinal.models.cache.CacheContractions.CACHE_WORD_IS_LOADED_DEFAULT;

public class LoadingActivity extends AppCompatActivity {

    // Declaration of variables
    private ImageView loader, data;
    private Retrofit retrofit;
    private ClientAPI clientAPI;
    private boolean isDataFailed = false;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Initialization
        loader = findViewById(R.id.loader);
        data = findViewById(R.id.data);

        sp = getSharedPreferences(CACHE_CACHE, MODE_PRIVATE);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.113:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        clientAPI = retrofit.create(ClientAPI.class);

        // Setting animation to the loader ImageView
        float ROTATE_FROM = 0.0f;
        float ROTATE_TO = 10.0f * 360.0f;

        RotateAnimation r = new RotateAnimation(ROTATE_FROM, ROTATE_TO, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        r.setDuration(7500);
        r.setRepeatCount(Animation.INFINITE);
        r.setInterpolator(new LinearInterpolator());

        loader.startAnimation(r);

        // Starting to prepare data async
        new Loader().execute();
    }

    // AsyncTask class to prepare data
    public class Loader extends AsyncTask<String, Integer, List<Word>> {

        private Thread load, download;
        private boolean flag = false;

        @Override
        protected List<Word> doInBackground(String... strings) {
            // Here is check if databases are empty
            // If so databases will be completed with data
            AllWordsDataBase allWordsDB = new AllWordsDataBase(getApplicationContext());
            LearnedWordsDataBase learnedWordsDB = new LearnedWordsDataBase(getApplicationContext());

            if (!sp.getBoolean(CACHE_WORD_IS_LOADED, CACHE_WORD_IS_LOADED_DEFAULT)) {
                download = new Thread(() -> {
                    // Check for database of all words
                    if (allWordsDB.isEmpty()) {
                        clientAPI.getAllWords()
                                .enqueue(new Callback<List<Word>>() {
                                    @Override
                                    public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                                        List<Word> allWords = response.body();
                                        if (allWords != null) {
                                            for (int i = 0; i < allWords.size(); i++) {
                                                allWordsDB.add(allWords.get(i));
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Word>> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                        List<Word> allWords = DefaultData.words;
                                        for (int i = 0; i < allWords.size(); i++) {
                                            allWordsDB.add(allWords.get(i));
                                        }
                                    }
                                });
                    }

                    // Check for database of learned words
                    if (learnedWordsDB.isEmpty()) {
                        clientAPI.getWords(1)
                                .enqueue(new Callback<List<Word>>() {
                                    @Override
                                    public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                                        List<Word> words = response.body();
                                        if (words != null) {
                                            for (int i = 0; i < words.size(); i++) {
                                                learnedWordsDB.add(words.get(i));
                                            }
                                        }
                                        flag = true;
                                    }

                                    @Override
                                    public void onFailure(Call<List<Word>> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                        List<Word> words = DefaultData.words;
                                        for (int i = 0; i < 5; i++) {
                                            learnedWordsDB.add(words.get(i));
                                        }
                                        flag = true;
                                    }
                                });
                    }
                });
            }


            if (download != null) {
                download.start();

                boolean f = true;

                while (f) {
                    try {
                        download.join();
                        f = false;
                    } catch (InterruptedException e) {
                        f = true;
                    }
                }

                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(CACHE_WORD_IS_LOADED, true);
                editor.apply();
            } else {
                flag = true;
            }

            while (!flag) {
            }

            List<Word> words = allWordsDB.getWords(null);

            if (!sp.getBoolean(CACHE_IS_LOADED, CACHE_IS_LOADED_DEFAULT)) {
                load = new Thread(() -> {
                    for (int i = 0; i < words.size(); i++) {
                        try {
                            Bitmap b = Picasso.with(getApplicationContext())
                                    .load(words.get(i).getUrl())
                                    .get();
                            Tools.saveToInternalStorage(words.get(i).getEng(), b, getApplicationContext());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            if (load != null) {
                load.start();

                boolean f = true;

                while (f) {
                    try {
                        load.join();
                        f = false;
                    } catch (InterruptedException e) {
                        f = true;
                    }
                }

                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(CACHE_IS_LOADED, true);
                editor.apply();
            }

            // Closing databases
            allWordsDB.close();
            learnedWordsDB.close();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Word> receivedWords) {
            super.onPostExecute(receivedWords);

            // When checking of data is finished MainActivity starts
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}