package com.dbot.client.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginData implements Serializable {
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
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("freelancer")
    @Expose
    private String freelancer;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("company_phone")
    @Expose
    private String companyPhone;
    @SerializedName("company_email")
    @Expose
    private String companyEmail;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(String freelancer) {
        this.freelancer = freelancer;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

}
