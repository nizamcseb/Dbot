package com.dbot.client.main.profile.refer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RcAndRhResponse {
    @SerializedName("data")
    @Expose
    private RcAndRhData data;

    public RcAndRhData getRcAndRhData() {
        return data;
    }

    public void setRcAndRhData(RcAndRhData data) {
        this.data = data;
    }
}
