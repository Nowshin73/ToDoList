package com.example.todolist.model;
public class Task {
    private String _id;
    private String title;
    private String description;
    private boolean isDone;

    public Task() {}

    public Task(String title, String description, boolean isDone) {
        this.title = title;
        this.description = description;
        this.isDone = isDone;
    }

    public String get_id() { return _id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isDone() { return isDone; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDone(boolean done) { this.isDone = done; }
}
