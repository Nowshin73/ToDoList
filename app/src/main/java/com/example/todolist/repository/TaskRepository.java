package com.example.todolist.repository;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolist.model.Task;
import com.example.todolist.network.ApiService;
import com.example.todolist.network.RetrofitClient;
import com.example.todolist.network.TaskApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskRepository {
    private TaskApi api;

    public TaskRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://todo-list-backend-red.vercel.app/") // Localhost for emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(TaskApi.class);
    }

    public void getTasks(TaskListCallback callback) {
        api.getTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) callback.onSuccess(response.body());
                else callback.onError("Error loading tasks");
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void addTask(Task task, TaskCallback callback) {
        api.addTask(task).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) callback.onSuccess();
                else callback.onError("Failed to add task");
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void updateTask(String id, Task task, TaskCallback callback) {
        api.updateTask(id, task).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) callback.onSuccess();
                else callback.onError("Failed to update task");
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void deleteTask(String id, TaskCallback callback) {
        api.deleteTask(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) callback.onSuccess();
                else callback.onError("Failed to delete task");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    public interface TaskCallback {
        void onSuccess();
        void onError(String error);
    }
    public interface TaskListCallback {
        void onSuccess(List<Task> tasks);
        void onError(String errorMessage);
    }
}
