package com.example.todolist.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends ViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel() {
        repository = new TaskRepository();
        allTasks = repository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void addTask(Task task) {
        repository.addTask(task);
    }

    public void deleteTask(String id) {
        repository.deleteTask(id);
    }

    public void updateTask(String id, Task task) {
        repository.updateTask(id, task);
    }
}
