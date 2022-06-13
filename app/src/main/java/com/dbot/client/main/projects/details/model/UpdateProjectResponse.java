package com.dbot.client.main.projects.details.model;

import com.dbot.client.main.projects.model.ClientProjectData;
import com.dbot.client.retrofit.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProjectResponse {
    @SerializedName("data")
    @Expose
    private ClientProjectData data;
    @SerializedName("status")
    @Expose
    private Status status;

    public ClientProjectData getClientProjectData() {
        return data;
    }

    public void setClientProjectData(ClientProjectData data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
