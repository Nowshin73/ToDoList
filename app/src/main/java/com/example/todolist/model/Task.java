package com.example.todolist.model;
public class Task {
    private String _id;           // MongoDB returns _id
    private String title;
    private String description;
    private boolean isDone;

    // Constructors
    public Task(String title, String description, boolean isDone) {
        this.title = title;
        this.description = description;
        this.isDone = isDone;
    }

    // Getters and Setters
    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    // ✅ Setter for isDone (you were missing this)
    public void setDone(boolean done) {
        this.isDone = done;
    }

}
