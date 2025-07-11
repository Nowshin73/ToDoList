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

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList = new ArrayList<>();
    private final TaskActionListener listener;

    public interface TaskActionListener {
        void onDeleteClicked(Task task);
        void onStatusChanged(Task task, boolean isDone);
        void onEditClicked(Task task);
    }

    public TaskAdapter(TaskActionListener listener) {
        this.listener = listener;
    }

    public void setTasks(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.textNumber.setText((position + 1) + ".");
        holder.textTitle.setText(task.getTitle());
        holder.textDescription.setText(task.getDescription());
        holder.checkboxDone.setChecked(task.isDone());

        holder.buttonDelete.setOnClickListener(v -> listener.onDeleteClicked(task));
        holder.buttonEdit.setOnClickListener(v -> listener.onEditClicked(task));

        holder.checkboxDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setDone(isChecked);
            listener.onStatusChanged(task, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textNumber, textTitle, textDescription;
        CheckBox checkboxDone;
        Button buttonDelete, buttonEdit;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textNumber = itemView.findViewById(R.id.textNumber);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            checkboxDone = itemView.findViewById(R.id.checkboxDone);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
        }
    }
}
