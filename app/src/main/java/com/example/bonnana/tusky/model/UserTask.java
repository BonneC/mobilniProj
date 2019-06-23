package com.example.bonnana.tusky.model;

import com.google.gson.annotations.SerializedName;

public class UserTask extends Task {
    private class UserTaskStatus {
        @SerializedName("updated_at")
        String updatedTimestamp;

        @SerializedName("completed")
        Integer completed;

        UserTaskStatus(Integer completed, String updatedTimestamp) {
            this.updatedTimestamp = updatedTimestamp;
            this.completed = completed;
        }
    }

    @SerializedName("pivot")
    private UserTaskStatus taskStatus;

    public UserTask(String id, String name, String description, Integer completed, String updatedTimestamp) {
        super(id, name, description);
        this.taskStatus = new UserTaskStatus(completed, updatedTimestamp);
    }

    public UserTaskStatus getTaskStatus() {
        return taskStatus;
    }

    public int isCompleted() {
        return taskStatus.completed;
    }

    public void updateTaskStatus(int completed){
        taskStatus.completed = completed;
    }

}
