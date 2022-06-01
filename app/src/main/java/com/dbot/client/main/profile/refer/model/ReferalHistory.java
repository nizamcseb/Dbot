package com.dbot.client.main.profile.refer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferalHistory {
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("bar_color")
    @Expose
    private String barColor;
    @SerializedName("comment")
    @Expose
    private String comment;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBarColor() {
        return barColor;
    }

    public void setBarColor(String barColor) {
        this.barColor = barColor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
