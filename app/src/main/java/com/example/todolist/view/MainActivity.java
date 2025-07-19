package com.example.todolist.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
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

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;


public class MainActivity extends AppCompatActivity {

    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;

    private EditText editTitle, editDescription;
    private Button buttonAdd;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerViewTasks);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(new TaskAdapter.TaskActionListener() {
            @Override
            public void onDeleteClicked(Task task) {
                taskViewModel.deleteTask(task.get_id());
            }

//            @Override
//            public void onStatusChanged(Task task, boolean isDone) {
//                task.setDone(isDone);
//                taskViewModel.updateTask(task.get_id(), task);
//            }

            @Override
            public void onStatusChanged(Task task, boolean isDone) {
                taskViewModel.updateTaskStatus(task.get_id(), isDone);
            }


            @Override
            public void onEditClicked(Task task) {
                showEditDialog(task);
            }
        });

        recyclerView.setAdapter(adapter);

        taskViewModel.getTasks().observe(this, tasks -> {
            adapter.setTasks(tasks);
        });

        buttonAdd.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String desc = editDescription.getText().toString().trim();

            if (!TextUtils.isEmpty(title)) {
                Task task = new Task(title, desc, false);
                taskViewModel.addTask(task);
                editTitle.setText("");
                editDescription.setText("");
            } else {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditDialog(@NonNull Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Task");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_task, null);
        final EditText inputTitle = viewInflated.findViewById(R.id.editTaskTitle);
        final EditText inputDesc = viewInflated.findViewById(R.id.editTaskDescription);

        inputTitle.setText(task.getTitle());
        inputDesc.setText(task.getDescription());

        builder.setView(viewInflated);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newTitle = inputTitle.getText().toString().trim();
            String newDesc = inputDesc.getText().toString().trim();

            if (!newTitle.isEmpty()) {
                // Create updated task with full fields
                Task updatedTask = new Task();
               // updatedTask.set_id(task.get_id());
                updatedTask.setTitle(newTitle);
                updatedTask.setDescription(newDesc);
                updatedTask.setDone(task.isDone()); // preserve status
                taskViewModel.updateTask(task.get_id(), updatedTask);
                Toast.makeText(MainActivity.this, "task updated", Toast.LENGTH_SHORT).show();
                taskViewModel.fetchTasks();
            } else {
                Toast.makeText(MainActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

}
