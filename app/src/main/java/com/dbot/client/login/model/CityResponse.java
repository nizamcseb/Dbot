package com.dbot.client.login.model;

import com.dbot.client.retrofit.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityResponse {
    @SerializedName("data")
    @Expose
    private List<CityData> data = null;
    @SerializedName("status")
    @Expose
    private Status status;

    public List<CityData> getCityListData() {
        return data;
    }

    public void setCityListData(List<CityData> data) {
        this.data = data;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
