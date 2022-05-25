package com.dbot.client.main.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableSlotsData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("slot_time")
    @Expose
    private String slotTime;
    @SerializedName("available_status")
    @Expose
    private Boolean availableStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    public Boolean getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(Boolean availableStatus) {
        this.availableStatus = availableStatus;
    }

}
