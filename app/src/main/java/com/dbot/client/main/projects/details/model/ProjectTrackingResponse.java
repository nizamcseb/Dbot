package com.dbot.client.main.projects.details.model;

import com.dbot.client.main.projects.details.model.ProjectTrackingData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectTrackingResponse {
    @SerializedName("data")
    @Expose
    private ProjectTrackingData data;

    public ProjectTrackingData getProjectTrackingData() {
        return data;
    }

    public void setProjectTrackingData(ProjectTrackingData data) {
        this.data = data;
    }
}
