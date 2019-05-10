package com.example.bonnana.tusky.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopicList {

    @SerializedName("topic_list")
    private ArrayList<Topic> topicList;

    public ArrayList<Topic> getTopicArrayList() {
        return topicList;
    }

    public void setTopicArrayList(ArrayList<Topic> topicArrayList) {
        this.topicList = topicArrayList;
    }
}
