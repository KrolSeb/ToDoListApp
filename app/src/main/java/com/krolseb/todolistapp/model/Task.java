package com.krolseb.todolistapp.model;

public class Task {
    private String title;
    private String date;
    private String category;

    public Task(String title, String date, String category) {
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public Task() { }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }


}
