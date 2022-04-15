package com.dbot.client.login.model;

import com.dbot.client.retrofit.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUp {
    @SerializedName("data")
    @Expose
    private Client data;
    @SerializedName("status")
    @Expose
    private Status status;

    public Client getClientData() {
        return data;
    }

    public void setClientData(Client data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
