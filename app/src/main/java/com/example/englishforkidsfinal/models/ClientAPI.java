package com.example.englishforkidsfinal.models;

import com.example.englishforkidsfinal.models.db_models.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClientAPI {

    @GET("/getAllWords")
    Call<List<Word>> getAllWords();

    @GET("/getWords")
    Call<List<Word>> getWords(@Query("gr") Integer group);

}
