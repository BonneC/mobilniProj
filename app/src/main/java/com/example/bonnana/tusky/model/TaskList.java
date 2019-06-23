package com.example.bonnana.tusky.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TaskList<T extends Task> {

    @SerializedName("task_list")
    private ArrayList<T> taskList;

    public ArrayList<T> getTaskArrayList() {
        return taskList;
    }

    public void setTaskArrayList(ArrayList<T> taskArrayList) {
        this.taskList = taskArrayList;
    }
}
