package com.example.englishforkidsfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.englishforkidsfinal.models.db_models.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadingActivity extends AppCompatActivity {

    private ImageView loader;
    private Retrofit retrofit;
    private ClientAPI clientAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loader = findViewById(R.id.loader);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        clientAPI = retrofit.create(ClientAPI.class);

        float ROTATE_FROM = 0.0f;
        float ROTATE_TO = 10.0f * 360.0f;

        RotateAnimation r = new RotateAnimation(ROTATE_FROM, ROTATE_TO, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        r.setDuration(7500);
        r.setRepeatCount(Animation.INFINITE);
        r.setInterpolator(new LinearInterpolator());

        loader.startAnimation(r);

        new Loader().execute();
    }

    public class Loader extends AsyncTask<String, Integer, List<Word>> {

        @Override
        protected List<Word> doInBackground(String... strings) {
            AllWordsDataBase allWordsDB = new AllWordsDataBase(getApplicationContext());
            LearnedWordsDataBase learnedWordsDB = new LearnedWordsDataBase(getApplicationContext());

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
                            }
                        });
            }

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
                            }

                            @Override
                            public void onFailure(Call<List<Word>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            allWordsDB.close();
            learnedWordsDB.close();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Word> words) {
            super.onPostExecute(words);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}