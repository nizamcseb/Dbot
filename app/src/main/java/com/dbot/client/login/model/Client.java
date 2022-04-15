package com.dbot.client.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("client_phone")
    @Expose
    private String clientPhone;
    @SerializedName("client_email")
    @Expose
    private String clientEmail;
    @SerializedName("company_name")
    @Expose
    private String companyName;

    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("freelancer")
    @Expose
    public int freelancer;
    @SerializedName("deviceid")
    @Expose
    public String deviceid;
    @SerializedName("notify_token")
    @Expose
    public String notify_token;
    @SerializedName("os_type")
    @Expose
    public int os_type;
    @SerializedName("device_model")
    @Expose
    public String device_model;


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

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCity(){
        return city;
    }
    public void setCity(String city){
        city = city;
    }
    public int getFreelancer(){
        return freelancer;
    }
    public void setFreelancer(int freelancer){
        freelancer = freelancer;
    }
    public String getDeviceid(){
        return deviceid;
    }
    public void setDeviceid(String deviceid){
        deviceid = deviceid;
    }
    public String getDevice_model(){
        return device_model;
    }
    public void setDevice_model(String device_model){
        device_model = device_model;
    }
    public String getNotify_token(){
        return notify_token;
    }
    public void setNotify_token(String notify_token){
        notify_token = notify_token;
    }
    public int getOs_type(){
        return os_type;
    }
    public void setOs_type(int os_type){
        os_type = os_type;
    }
}
