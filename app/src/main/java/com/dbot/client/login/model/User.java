package com.dbot.client.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("client_id")
    private String client_id;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("client_phone")
    private String clientPhone;
    @SerializedName("client_email")
    private String clientEmail;
    @SerializedName("company_name")
    private String companyName;
    @SerializedName("company_phone")
    private String company_phone;
    @SerializedName("company_email")
    private String company_email;
    @SerializedName("city")
    private String city;
    @SerializedName("freelancer")
    public int freelancer;
    @SerializedName("deviceid")
    public String deviceid;
    @SerializedName("notify_token")
    public String notify_token;
    @SerializedName("os_type")
    public int os_type;
    @SerializedName("device_model")
    public String device_model;

    public User(String client_id,
                String fullname,
                String clientPhone,
                String clientEmail,
                String companyName,
                String company_phone,
                String company_email,
                String city,
                int freelancer,
                String deviceid,
                String notify_token,
                int os_type,
                String device_model) {
        this.client_id = client_id;
        this.fullname = fullname;
        this.clientPhone = clientPhone;
        this.clientEmail = clientEmail;
        this.companyName = companyName;
        this.company_phone = company_phone;
        this.company_email = company_email;
        this.city = city;
        this.freelancer = freelancer;
        this.deviceid = deviceid;
        this.notify_token = notify_token;
        this.os_type = os_type;
        this.device_model = device_model;

    }
}
