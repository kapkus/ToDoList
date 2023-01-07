package com.example.todolist;

import org.threeten.bp.LocalDateTime;

public class TaskData {
    int id;
    String text;
    LocalDateTime date;
    boolean isDeleted;
    TaskData(int id, String text, LocalDateTime date, boolean isDeleted){
        this.id = id;
        this.text = text;
        this.date = date;
        this.isDeleted = isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean getDeleted() {
        return isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
