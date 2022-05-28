package com.dbot.client.main.projects.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientProjectResponse {
    @SerializedName("data")
    @Expose
    private List<ClientProjectData> data = null;

    public List<ClientProjectData> getData() {
        return data;
    }

    public void setData(List<ClientProjectData> data) {
        this.data = data;
    }
}
