package com.dbot.client.main.projects.details.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResheduleData {
    @SerializedName("request_id")
    @Expose
    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
