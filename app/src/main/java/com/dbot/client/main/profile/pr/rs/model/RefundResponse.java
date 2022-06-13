package com.dbot.client.main.profile.pr.rs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RefundResponse {
    @SerializedName("data")
    @Expose
    private List<RefundData> data = null;

    public List<RefundData> getRefundData() {
        return data;
    }

    public void setRefundData(List<RefundData> data) {
        this.data = data;
    }
}
