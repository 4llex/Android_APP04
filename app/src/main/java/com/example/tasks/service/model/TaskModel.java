package com.example.tasks.service.model;

import com.google.gson.annotations.SerializedName;

public class TaskModel {

    @SerializedName("Id")
    private int id;

    @SerializedName("PriorityId")
    private int priorityId;

    @SerializedName("Description")
    private String description;

    @SerializedName("DueData")
    private String dueData;

    @SerializedName("Complete")
    private String complete;

}
