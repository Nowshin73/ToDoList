package com.example.todolist.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private TaskActionListener listener;

    // Constructor receives the listener from MainActivity
    public TaskAdapter(TaskActionListener listener) {
        this.listener = listener;
    }

    // Set the tasks and refresh RecyclerView
    public void setTasks(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    // Create view holder for each item
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(v);
    }

    // Bind data to each item in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.textTitle.setText(task.getTitle());
        holder.textDescription.setText(task.getDescription());
        holder.checkboxDone.setChecked(task.isDone());

        // Delete button click
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClicked(task); // notify MainActivity
            }
        });

        // CheckBox toggle for marking task done/undone
        holder.checkboxDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setDone(isChecked); // update local task object
            listener.onStatusChanged(task, isChecked); // notify MainActivity
        });
    }

    // Total number of items
    @Override
    public int getItemCount() {
        return taskList == null ? 0 : taskList.size();
    }

    // ViewHolder holds the UI elements of a task item
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDescription;
        Button buttonDelete;
        CheckBox checkboxDone;

        public TaskViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            checkboxDone = itemView.findViewById(R.id.checkboxDone);
        }
    }

    // Interface for handling user actions
    public interface TaskActionListener {
        void onDeleteClicked(Task task);                  // delete button
        void onStatusChanged(Task task, boolean isDone);  // checkbox
    }
}
