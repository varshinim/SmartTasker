package com.codepath.smarttasker.models;


public class Task {

    private String text;
    private int id;

    public Task()
    {
        this.text = null;
    }
    public Task(String text) {
        super();
        this.text = text;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
