package com.dbot.client.main.newrequest.model;

import com.dbot.client.retrofit.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplyCouponResponse {
    @SerializedName("data")
    @Expose
    private Coupon data;
    @SerializedName("status")
    @Expose
    private Status status;

    public Coupon getCoupon() {
        return data;
    }

    public void setCoupon(Coupon data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
