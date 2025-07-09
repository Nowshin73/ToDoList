package com.example.todolist.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.adapter.TaskAdapter;
import com.example.todolist.model.Task;
import com.example.todolist.viewmodel.TaskViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;
    private EditText editTitle, editDescription;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        buttonAdd = findViewById(R.id.buttonAdd);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);

        adapter = new TaskAdapter(new TaskAdapter.TaskActionListener() {
            @Override
            public void onDeleteClicked(Task task) {
                taskViewModel.deleteTask(task.get_id());
            }

            @Override
            public void onStatusChanged(Task task, boolean isDone) {
                task.setDone(isDone);
                taskViewModel.updateTask(task.get_id(), task);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Observe LiveData for task list
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });

        // Add task button click
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String description = editDescription.getText().toString().trim();

                if (!title.isEmpty()) {
                    Task newTask = new Task(title, description, false);
                    taskViewModel.addTask(newTask);
                    editTitle.setText("");
                    editDescription.setText("");
                }
            }
        });
    }
}
