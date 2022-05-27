package com.dbot.client.main.newrequest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectStatus {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status_value")
    @Expose
    private String statusValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }
}
