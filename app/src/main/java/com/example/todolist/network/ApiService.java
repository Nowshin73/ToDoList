package com.example.todolist.network;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

import com.example.todolist.model.Task;

public interface ApiService {

    @GET("tasks")
    Call<List<Task>> getAllTasks();

    @POST("tasks")
    Call<Task> addTask(@Body Task task);

    @PUT("tasks/{id}")
    Call<Task> updateTask(@Path("id") String id, @Body Task task);

    @DELETE("tasks/{id}")
    Call<Void> deleteTask(@Path("id") String id);
}
