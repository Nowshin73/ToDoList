package com.example.todolist.repository;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolist.model.Task;
import com.example.todolist.network.ApiService;
import com.example.todolist.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRepository {

    private ApiService apiService;

    public TaskRepository() {
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    public LiveData<List<Task>> getAllTasks() {
        MutableLiveData<List<Task>> taskList = new MutableLiveData<>();

        apiService.getAllTasks().enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    taskList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Log.e("TaskRepo", "Error fetching tasks", t);
            }
        });

        return taskList;
    }

    public void addTask(Task task) {
        apiService.addTask(task).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {}

            @Override
            public void onFailure(Call<Task> call, Throwable t) {}
        });
    }

    public void deleteTask(String id) {
        apiService.deleteTask(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }

    public void updateTask(String id, Task task) {
        apiService.updateTask(id, task).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {}

            @Override
            public void onFailure(Call<Task> call, Throwable t) {}
        });
    }
}
