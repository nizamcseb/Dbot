package com.dbot.client.common.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableDate {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("available_status")
    @Expose
    private Boolean availableStatus;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Boolean availableStatus) {
        this.availableStatus = availableStatus;
    }
}
