package com.example.englishforkidsfinal.models;

import com.example.englishforkidsfinal.models.db_models.BigAnimal;
import com.example.englishforkidsfinal.models.db_models.Category;
import com.example.englishforkidsfinal.models.db_models.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClientAPI {

    // GET queries for server
    @GET("/getAllWords")
    Call<List<Word>> getAllWords();

    @GET("/getWords")
    Call<List<Word>> getWords(@Query("gr") Integer group);

    @GET("/getWordsRange")
    Call<List<Word>> getRangeOfWords(@Query("start") Integer start, @Query("end") Integer end);

    @GET("/getCategories")
    Call<List<Category>> getCategories();

    @GET("/getBigAnimals")
    Call<List<BigAnimal>> getBigAnimals();

    @GET("/getAlphabet")
    Call<List<RestAlphabetLetter>> getAlphabet();

}
