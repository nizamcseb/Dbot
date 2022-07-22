package com.dbot.client.common.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailableDatesResponse {
    @SerializedName("data")
    @Expose
    private List<AvailableDate> data = null;

    public List<AvailableDate> getData() {
        return data;
    }

    public void setData(List<AvailableDate> data) {
        this.data = data;
    }
}
