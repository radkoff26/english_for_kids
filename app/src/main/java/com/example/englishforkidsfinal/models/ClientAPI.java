package com.example.englishforkidsfinal.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientAPI {
    @GET("/getStudents")
    Call<List<Student>> getStudents();
}
