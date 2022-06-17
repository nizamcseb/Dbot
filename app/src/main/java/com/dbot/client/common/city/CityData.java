package com.dbot.client.common.city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("working_city")
    @Expose
    private String workingCity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWorkingCity() {
        return workingCity;
    }

    public void setWorkingCity(String workingCity) {
        this.workingCity = workingCity;
    }
}
