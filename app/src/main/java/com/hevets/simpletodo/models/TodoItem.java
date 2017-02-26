package com.hevets.simpletodo.models;

import java.io.Serializable;

public class TodoItem implements Serializable {

    private int position;
    private String title;

    public TodoItem(int position, String title) {
        this.position = position;
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
