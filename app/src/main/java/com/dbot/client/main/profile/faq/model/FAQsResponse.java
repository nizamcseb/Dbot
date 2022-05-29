package com.dbot.client.main.profile.faq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FAQsResponse {
    @SerializedName("data")
    @Expose
    private List<FAQsData> data = null;

    public List<FAQsData> getData() {
        return data;
    }

    public void setData(List<FAQsData> data) {
        this.data = data;
    }
}
