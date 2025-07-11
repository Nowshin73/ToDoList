package com.example.todolist.viewmodel;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;


import java.util.List;

public class TaskViewModel extends ViewModel {
    private TaskRepository repository;
    private MutableLiveData<List<Task>> tasksLiveData;

    public TaskViewModel() {
        repository = new TaskRepository();
        tasksLiveData = new MutableLiveData<>();
        fetchTasks();
    }

    public LiveData<List<Task>> getTasks() {
        return tasksLiveData;
    }

    public void fetchTasks() {
        repository.getTasks(new TaskRepository.TaskListCallback() {
            @Override
            public void onSuccess(List<Task> tasks) {
                tasksLiveData.setValue(tasks);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("VM", errorMessage);
            }
        });
    }

    public void addTask(Task task) {
        repository.addTask(task, new TaskRepository.TaskCallback() {
            @Override
            public void onSuccess() {
                fetchTasks();
            }

            @Override
            public void onError(String error) {
                Log.e("Add", error);
            }
        });
    }

    public void updateTask(String id, Task task) {
        repository.updateTask(id, task, new TaskRepository.TaskCallback() {
            @Override
            public void onSuccess() {
                fetchTasks();
            }

            @Override
            public void onError(String error) {
                Log.e("Update", error);
            }
        });
    }

    public void deleteTask(String id) {
        repository.deleteTask(id, new TaskRepository.TaskCallback() {
            @Override
            public void onSuccess() {
                fetchTasks();
            }

            @Override
            public void onError(String error) {
                Log.e("Delete", error);
            }
        });
    }
}
