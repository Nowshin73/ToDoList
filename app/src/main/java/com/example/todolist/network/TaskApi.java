package com.example.todolist.network;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.*;

import com.example.todolist.model.Task;

public interface TaskApi {
    @GET("tasks")
    Call<List<Task>> getTasks();

    @POST("tasks")
    Call<Task> addTask(@Body Task task);

    @PUT("tasks/{id}")
    Call<Void> updateTask(@Path("id") String id, @Body Task task);

    @PATCH("tasks/{id}")
    Call<Void> updateTaskStatus(@Path("id") String id, @Body Map<String, Boolean> status);
    @DELETE("tasks/{id}")
    Call<Void> deleteTask(@Path("id") String id);
}
