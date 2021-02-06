package com.example.englishforkidsfinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.models.ClientAPI;
import com.example.englishforkidsfinal.models.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LearningFragment extends Fragment {

    ListView listView;
    List<Student> students;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learning, container, false);

        listView = v.findViewById(R.id.lv_students);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.46:8080")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ClientAPI clientAPI = retrofit.create(ClientAPI.class);

        Call<List<Student>> call = clientAPI.getStudents();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                students = (List<Student>)response.body();
                //Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                List<String> names = new ArrayList<>(students.size());
                for (Student student : students) {
                    names.add(student.toString());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, names);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {

                Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}