package com.dbot.client.main.projects.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateProject {
    @SerializedName("client_id")
    private String client_id;
    @SerializedName("booking_id")
    private String booking_id;
    @SerializedName("door_number")
    private String door_number;
    @SerializedName("building_name")
    private String building_name;
    @SerializedName("landmark")
    private String landmark;
    @SerializedName("project_name")
    private String project_name;
    @SerializedName("contact_person_name")
    private String contact_person_name;
    @SerializedName("contact_person_phone")
    private String contact_person_phone;
    @SerializedName("property_size")
    private Integer property_size;
    @SerializedName("project_type")
    private Integer project_type;
    @SerializedName("scope")
    @Expose
    private List<Integer> scope = null;



    public String getClientId() {
        return client_id;
    }

    public void setClientId(String clientId) {
        this.client_id = clientId;
    }

   public String getBookingId() {
        return booking_id;
    }

    public void setBookingId(String booking_id) {
        this.booking_id = booking_id;
    }



    public String getDoorNumber() {
        return door_number;
    }

    public void setDoorNumber(String doorNumber) {
        this.door_number = doorNumber;
    }

    public String getBuildingName() {
        return building_name;
    }

    public void setBuildingName(String buildingName) {
        this.building_name = buildingName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getProjectName() {
        return project_name;
    }

    public void setProjectName(String projectName) {
        this.project_name = projectName;
    }

    public String getContactPersonName() {
        return contact_person_name;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contact_person_name = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contact_person_phone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contact_person_phone = contactPersonPhone;
    }

    public Integer getPropertySize() {
        return property_size;
    }

    public void setPropertySize(Integer propertySize) {
        this.property_size = propertySize;
    }

    public Integer getProjectType() {
        return project_type;
    }

    public void setProjectType(Integer projectType) {
        this.project_type = projectType;
    }

    public List<Integer> getScope() {
        return scope;
    }

    public void setScope(List<Integer> scope) {
        this.scope = scope;
    }



}
