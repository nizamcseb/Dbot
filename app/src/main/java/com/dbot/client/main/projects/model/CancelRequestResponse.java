package com.dbot.client.main.projects.model;

import com.dbot.client.retrofit.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelRequestResponse {
    @SerializedName("data")
    @Expose
    private RefundAmount data;
    @SerializedName("status")
    @Expose
    private Status status;

    public RefundAmount getRefundAmount() {
        return data;
    }

    public void setRefundAmount(RefundAmount data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
