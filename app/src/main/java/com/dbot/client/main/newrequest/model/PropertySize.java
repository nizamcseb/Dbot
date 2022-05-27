package com.dbot.client.main.newrequest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertySize {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("size_value")
    @Expose
    private String sizeValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSizeValue() {
        return sizeValue;
    }

    public void setSizeValue(String sizeValue) {
        this.sizeValue = sizeValue;
    }
}
