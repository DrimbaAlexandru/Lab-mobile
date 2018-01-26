package com.example.alex.emptyapp.Repository.Rest;

import com.example.alex.emptyapp.Domain.MyTask;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Alex on 26.11.2017.
 */

public interface RestTaskRepository
{
    final String basePath = "task/";

    @GET(basePath)
    Call<List<MyTask>> getTasks(@Query("lastUpdated") Long lastModified);

    @PUT(basePath + "id/")
    Call<MyTask> updateTask(@Path("id") int id, @Body MyTask task);
}
