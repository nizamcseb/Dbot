package com.dbot.client.main.newrequest.model;

import com.dbot.client.retrofit.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookSlotResponse {
    @SerializedName("data")
    @Expose
    private BookSlotData data;
    @SerializedName("status")
    @Expose
    private Status status;

    public BookSlotData getData() {
        return data;
    }

    public void setData(BookSlotData data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
