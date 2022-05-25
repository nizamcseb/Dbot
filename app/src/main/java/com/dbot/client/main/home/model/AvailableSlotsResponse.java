package com.dbot.client.main.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailableSlotsResponse {
    @SerializedName("data")
    @Expose
    private List<AvailableSlotsData> data = null;

    public List<AvailableSlotsData> getData() {
        return data;
    }

    public void setData(List<AvailableSlotsData> data) {
        this.data = data;
    }
}
