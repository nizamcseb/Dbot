package com.dbot.client.main.profile.pr.rs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefundStatus {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
