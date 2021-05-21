package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.db.AllWordsDataBase;
import com.example.englishforkidsfinal.db.AlphabetDataBase;
import com.example.englishforkidsfinal.db.BigAnimalDatabase;
import com.example.englishforkidsfinal.db.CategoryDataBase;
import com.example.englishforkidsfinal.db.LearnedWordsDataBase;
import com.example.englishforkidsfinal.models.RestAlphabetLetter;
import com.example.englishforkidsfinal.models.db_models.BigAnimal;
import com.example.englishforkidsfinal.models.db_models.Category;
import com.example.englishforkidsfinal.models.ClientAPI;
import com.example.englishforkidsfinal.models.DefaultData;
import com.example.englishforkidsfinal.models.Tools;
import com.example.englishforkidsfinal.models.db_models.Word;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.englishforkidsfinal.db.contractions.DBModelContractions.*;
import static com.example.englishforkidsfinal.models.contractions.CacheContractions.*;

public class LoadingActivity extends AppCompatActivity {

    // Declaration of variables
    private LinearProgressIndicator loader;
    private Retrofit retrofit;
    private ClientAPI clientAPI;
    private SharedPreferences sp, sp_contest;
    private boolean flag = false;
    private static final String BASE_URL = "http://188.225.46.21:8081";
    private TextView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Initialization
        loader = findViewById(R.id.loader);
        loading = findViewById(R.id.loading);

        loading.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FuturaRoundBold.ttf"));

        sp = getSharedPreferences(CACHE_CACHE, MODE_PRIVATE);
        sp_contest = getSharedPreferences(CACHE_CONTEST, MODE_PRIVATE);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        clientAPI = retrofit.create(ClientAPI.class);

        // Starting to prepare data async
        clientAPI.getBigAnimals()
                .enqueue(new Callback<List<BigAnimal>>() {
                    @Override
                    public void onResponse(Call<List<BigAnimal>> call, Response<List<BigAnimal>> response) {
                        List<BigAnimal> bigAnimals = response.body();
                        BigAnimalDatabase db = new BigAnimalDatabase(getApplicationContext());
                        for (int i = 0; i < bigAnimals.size(); i++) {
                            db.addBigAnimal(bigAnimals.get(i));
                        }
                        db.close();
                    }

                    @Override
                    public void onFailure(Call<List<BigAnimal>> call, Throwable t) {
                    }
                });

        clientAPI.getCategories()
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        List<Category> categories = response.body();
                        CategoryDataBase db = new CategoryDataBase(getApplicationContext());
                        for (int i = 0; i < categories.size(); i++) {
                            db.addCategory(categories.get(i));
                        }
                        db.close();
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        CategoryDataBase db = new CategoryDataBase(getApplicationContext());
                        if (db.isEmpty()) {
                            List<Category> categories = DefaultData.categories;
                            for (int i = 0; i < categories.size(); i++) {
                                db.addCategory(categories.get(i));
                            }
                            db.close();
                        }
                    }
                });

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

            int level = sp_contest.getInt(CACHE_CONTEST_GROUP, CACHE_CONTEST_GROUP_DEFAULT);

            List<Word> allWords = allWordsDB.getWords(null);
            List<Word> learnedWords = learnedWordsDB.getWords();

            AlphabetDataBase db = new AlphabetDataBase(getApplicationContext());

            if (db.getAlphabet().size() < 26) {
                clientAPI.getAlphabet()
                        .enqueue(new Callback<List<RestAlphabetLetter>>() {
                            @Override
                            public void onResponse(Call<List<RestAlphabetLetter>> call, Response<List<RestAlphabetLetter>> response) {
                                List<RestAlphabetLetter> letters = response.body();
                                for (int i = 0; i < letters.size(); i++) {
                                    db.addLetter(letters.get(i));
                                }
                                db.close();
                            }

                            @Override
                            public void onFailure(Call<List<RestAlphabetLetter>> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }

            flag = true;

            if (learnedWords.isEmpty()) {

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
            }

            flag = false;

            Log.d("DEBUG", level + "");
            int plus_20 = (((level == 0 ? 0 : (level - 1)) * NUMBER_OF_WORDS_IN_GROUP) + ONE_PART);

            if (allWords.isEmpty()) {
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
                                List<Word> allWords = DefaultData.words;
                                for (int i = 0; i < allWords.size(); i++) {
                                    allWords.get(i).setLoaded(false);
                                    allWordsDB.add(allWords.get(i));
                                }
                                flag = true;
                            }
                        });
            } else if (allWords.size() < plus_20) {
                clientAPI.getRangeOfWords(allWords.size() + 1, plus_20)
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
            new Tools.CountDown(callable).execute(300);
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

            while (!flag) {
            }

            boolean f;

            AlphabetDataBase alphabetDB = new AlphabetDataBase(getApplicationContext());
            Thread loadAlphabet = new Thread(() -> {
                List<RestAlphabetLetter> letters = alphabetDB.getAlphabet();
                if (!letters.isEmpty()) {
                    for (int i = 0; i < letters.size(); i++) {
                        if (!letters.get(i).isLoaded()) {
                            try {
                                Log.d("DEBUG", "LOADING");
                                Bitmap b;
                                b = Picasso.with(getApplicationContext())
                                        .load(BASE_URL + "/getImage?name=" + letters.get(i).getUri())
                                        .get();
                                Tools.saveToInternalStorage(letters.get(i).getUri(), b, getApplicationContext());
                                b = Picasso.with(getApplicationContext())
                                        .load(BASE_URL + "/getImage?name=" + letters.get(i).getPicture_uri())
                                        .get();
                                Tools.saveToInternalStorage(letters.get(i).getPicture_uri(), b, getApplicationContext());
                                RestAlphabetLetter letter = letters.get(i);
                                alphabetDB.addLetter(new RestAlphabetLetter(
                                        letter.getId(),
                                        letter.getLetter(),
                                        letter.getUri(),
                                        letter.getPicture_uri(),
                                        true
                                ));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

            loadAlphabet.start();

            f = true;

            while (f) try {
                loadAlphabet.join();
                f = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            alphabetDB.close();

            BigAnimalDatabase db = new BigAnimalDatabase(getApplicationContext());
            Thread loadAnimals = new Thread(() -> {
                List<BigAnimal> bigAnimals = db.getBigAnimals();
                if (!bigAnimals.isEmpty()) {
                    for (int i = 0; i < bigAnimals.size(); i++) {
                        if (!bigAnimals.get(i).getLoaded()) {
                            try {
                                Bitmap b;
                                b = Picasso.with(getApplicationContext())
                                        .load(BASE_URL + "/getImage?name=" + bigAnimals.get(i).getUri())
                                        .get();
                                Tools.saveToInternalStorage(bigAnimals.get(i).getUri(), b, getApplicationContext());
                                b = Picasso.with(getApplicationContext())
                                        .load(BASE_URL + "/getImage?name=" + bigAnimals.get(i).getUri_bg())
                                        .get();
                                Tools.saveToInternalStorage(bigAnimals.get(i).getUri_bg(), b, getApplicationContext());
                                db.addBigAnimal(new BigAnimal(
                                        bigAnimals.get(i).getId(),
                                        bigAnimals.get(i).getUri(),
                                        bigAnimals.get(i).getUri_bg(),
                                        bigAnimals.get(i).getWord(),
                                        true
                                ));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

            loadAnimals.start();

            f = true;

            while (f) try {
                loadAnimals.join();
                f = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            db.close();

            Thread load = new Thread(() -> {
                List<Word> words = allWordsDB.getWords(null);
                Log.d("DEBUG", words.toString());
                int level = sp_contest.getInt(CACHE_CONTEST_GROUP, CACHE_CONTEST_GROUP_DEFAULT);
                Log.d("DEBUG", level + "");
                int plus_20 = (((level == 0 ? 0 : (level - 1)) * NUMBER_OF_WORDS_IN_GROUP) + ONE_PART);
                int size = Math.min(plus_20, words.size());
                Log.d("DEBUG", plus_20 + "");
                for (int i = 0; i < size; i++) {
                    if (!words.get(i).isLoaded()) {
                        Log.d("DEBUG", words.get(i).getEng());
                        try {
                            Bitmap b;
                            if (words.get(i).getUrl().contains("http")) {
                                b = Picasso.with(getApplicationContext())
                                        .load(words.get(i).getUrl())
                                        .get();
                            } else {
                                b = Picasso.with(getApplicationContext())
                                        .load(BASE_URL + "/getImage?name=" + words.get(i).getUrl())
                                        .get();
                            }
                            Tools.saveToInternalStorage(words.get(i).getEng(), b, getApplicationContext());
                            Word word = new Word(
                                    words.get(i).getId(),
                                    words.get(i).getEng(),
                                    words.get(i).getRu(),
                                    words.get(i).getUrl(),
                                    words.get(i).getGr(),
                                    true,
                                    words.get(i).getCategory_id()
                            );
                            allWordsDB.add(word);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            load.start();

            f = true;

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
            new Tools.CountDown(callable).execute(500);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            loader.setProgressCompat(3, true);
            super.onPostExecute(integer);
            // When checking of data is finished MainActivity starts
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}