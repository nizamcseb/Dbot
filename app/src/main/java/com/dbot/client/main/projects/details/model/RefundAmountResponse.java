package com.dbot.client.main.projects.details.model;

import com.dbot.client.main.projects.details.model.RefundAmount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefundAmountResponse {
    @SerializedName("data")
    @Expose
    private RefundAmount data;

    public RefundAmount getRefundAmount() {
        return data;
    }

    public void setRefundAmount(RefundAmount data) {
        this.data = data;
    }
}
