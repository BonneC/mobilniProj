package com.example.bonnana.tusky.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TaskList {

    @SerializedName("task_list")
    private ArrayList<Task> taskList;

    public ArrayList<Task> getTaskArrayList() {
        return taskList;
    }

    public void setTaskArrayList(ArrayList<Task> taskArrayList) {
        this.taskList = taskArrayList;
    }
}
