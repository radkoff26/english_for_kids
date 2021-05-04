package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.AllWordsDataBase;
import com.example.englishforkidsfinal.db.LearnedWordsDataBase;
import com.example.englishforkidsfinal.models.Category;
import com.example.englishforkidsfinal.models.ClientAPI;
import com.example.englishforkidsfinal.models.DefaultData;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.db_models.Word;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.slider.Slider;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.englishforkidsfinal.db.contractions.DBModelContractions.NUMBER_OF_WORDS_IN_GROUP;
import static com.example.englishforkidsfinal.db.contractions.DBModelContractions.ONE_PART;
import static com.example.englishforkidsfinal.models.contractions.CacheContractions.CACHE_CACHE;
import static com.example.englishforkidsfinal.models.contractions.CacheContractions.CACHE_CATEGORIES;
import static com.example.englishforkidsfinal.models.contractions.CacheContractions.CACHE_CONTEST_GROUP;
import static com.example.englishforkidsfinal.models.contractions.CacheContractions.CACHE_CONTEST_GROUP_DEFAULT;

public class LoadingActivity extends AppCompatActivity {

    // Declaration of variables
    private LinearProgressIndicator loader;
    private Retrofit retrofit;
    private ClientAPI clientAPI;
    private SharedPreferences sp;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Initialization
        loader = findViewById(R.id.loader);

        sp = getSharedPreferences(CACHE_CACHE, MODE_PRIVATE);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        clientAPI = retrofit.create(ClientAPI.class);

        // Starting to prepare data async
        new Loader().execute();
        new LoadImages().execute();
    }

    // AsyncTask class to prepare data
    public class Loader extends AsyncTask<String, Integer, List<Word>> {

        @Override
        protected List<Word> doInBackground(String... strings) {
            // Here is check if databases are empty
            // If so databases will be completed with data
            AllWordsDataBase allWordsDB = new AllWordsDataBase(getApplicationContext());
            LearnedWordsDataBase learnedWordsDB = new LearnedWordsDataBase(getApplicationContext());

            int level = sp.getInt(CACHE_CONTEST_GROUP, CACHE_CONTEST_GROUP_DEFAULT);

            List<Word> allWords = allWordsDB.getWords(null);
            List<Word> learnedWords = learnedWordsDB.getWords();

            clientAPI.getCategories()
                    .enqueue(new Callback<List<Category>>() {
                        @Override
                        public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                            List<Category> categories = response.body();
                            if (categories != null) {
                                SharedPreferences.Editor editor = sp.edit();
                                Set<String> res = new HashSet<>();
                                for (int i = 0; i < categories.size(); i++) {
                                    res.add(categories.get(i).getTitle());
                                }
                                editor.putStringSet(CACHE_CATEGORIES, res);
                                editor.apply();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Category>> call, Throwable t) {
                            SharedPreferences.Editor editor = sp.edit();
                            Set<String> res = new HashSet<>();
                            for (int i = 0; i < DefaultData.categories.size(); i++) {
                                res.add(DefaultData.categories.get(i).getTitle());
                            }
                            editor.putStringSet(CACHE_CATEGORIES, res);
                            editor.apply();
                        }
                    });

            if (learnedWords.isEmpty() || allWords.size() < Math.ceil(((double) level + 1) / 10) * ONE_PART) {
                flag = false;
                // Check for database of all words
                clientAPI.getAllWords()
                        .enqueue(new Callback<List<Word>>() {
                            @Override
                            public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                                List<Word> allWords = response.body();
                                if (allWords != null) {
                                    for (int i = 0; i < allWords.size(); i++) {
                                        allWords.get(i).setLoaded(false);
                                        allWordsDB.add(allWords.get(i));
                                    }
                                }
                                flag = true;
                            }

                            @Override
                            public void onFailure(Call<List<Word>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                List<Word> allWords = DefaultData.words;
                                for (int i = 0; i < allWords.size(); i++) {
                                    allWords.get(i).setLoaded(false);
                                    allWordsDB.add(allWords.get(i));
                                }
                                flag = true;
                            }
                        });

                // Check for database of learned words
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
                            }

                            @Override
                            public void onFailure(Call<List<Word>> call, Throwable t) {
                                List<Word> words = DefaultData.words;
                                for (int i = 0; i < NUMBER_OF_WORDS_IN_GROUP; i++) {
                                    learnedWordsDB.add(words.get(i));
                                }
                            }
                        });
            } else {
                flag = true;
            }
            // Closing databases
            allWordsDB.close();
            learnedWordsDB.close();
            return null;
        }

        @Override
        protected void onPreExecute() {
            Callable<Void> callable = () -> {
                loader.setProgressCompat(1, true);
                return null;
            };
            new Tools.CountDown(callable).execute(1000);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Word> receivedWords) {
            super.onPostExecute(receivedWords);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    class LoadImages extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            AllWordsDataBase allWordsDB = new AllWordsDataBase(getApplicationContext());

            while (!flag) {}

            Thread load = new Thread(() -> {
                List<Word> words = allWordsDB.getWords(null);
                for (int i = 0; i < words.size(); i++) {
                    if (!words.get(i).isLoaded()) {
                        try {
                            Bitmap b = Picasso.with(getApplicationContext())
                                    .load(words.get(i).getUrl())
                                    .get();
                            Tools.saveToInternalStorage(words.get(i).getEng(), b, getApplicationContext());
                            words.get(i).setLoaded(true);
                            allWordsDB.add(words.get(i));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            load.start();

            boolean f = true;

            while (f) try {
                load.join();
                f = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            allWordsDB.close();

            return null;
        }

        @Override
        protected void onPreExecute() {
            Callable<Void> callable = () -> {
                loader.setProgressCompat(2, true);
                return null;
            };
            new Tools.CountDown(callable).execute(1000);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            loader.setProgressCompat(3, true);
            super.onPostExecute(integer);
            Callable<Void> callable = () -> {
                // When checking of data is finished MainActivity starts
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return null;
            };
            new Tools.CountDown(callable).execute(200);
        }
    }
}