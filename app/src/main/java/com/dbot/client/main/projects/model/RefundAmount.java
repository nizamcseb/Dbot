package com.dbot.client.main.projects.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefundAmount {
    @SerializedName("refund_amount")
    @Expose
    private Integer refundAmount;

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }
}
