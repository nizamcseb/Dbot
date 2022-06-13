package com.dbot.client.main.newrequest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailableCouponResponse {
    @SerializedName("data")
    @Expose
    private List<AvailableCoupon> data = null;

    public List<AvailableCoupon> getData() {
        return data;
    }

    public void setData(List<AvailableCoupon> data) {
        this.data = data;
    }
}
