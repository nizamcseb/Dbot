package com.dbot.client.main.projects.details.model;

import com.dbot.client.retrofit.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResheduleResponse {
    @SerializedName("data")
    @Expose
    private ResheduleData data;
    @SerializedName("status")
    @Expose
    private Status status;

    public ResheduleData getResheduleData() {
        return data;
    }

    public void setResheduleData(ResheduleData data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
