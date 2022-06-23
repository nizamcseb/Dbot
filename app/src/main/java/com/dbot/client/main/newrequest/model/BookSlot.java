package com.dbot.client.main.newrequest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BookSlot implements Serializable {
    @SerializedName("client_id")
    private String client_id;
    @SerializedName("book_date")
    private String book_date;
    @SerializedName("slot_time_id")
    private Integer slot_time_id;
    @SerializedName("city")
    private Integer city;
    @SerializedName("map_location")
    private String map_location;
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

    @SerializedName("package")
    private Integer package_id;
    @SerializedName("coupen_code")
    private String coupen_code;
    @SerializedName("package_amount")
    private Integer package_amount;
    @SerializedName("discount")
    private Integer discount;
    @SerializedName("amount_paid")
    private Integer amount_paid;
    @SerializedName("payment_status")
    private Integer payment_status;

    @SerializedName("transaction_id")
    private String transaction_id;


    public String getClientId() {
        return client_id;
    }

    public void setClientId(String clientId) {
        this.client_id = clientId;
    }

    public String getBookDate() {
        return book_date;
    }

    public void setBookDate(String bookDate) {
        this.book_date = bookDate;
    }

    public Integer getSlotTimeId() {
        return slot_time_id;
    }

    public void setSlotTimeId(Integer slotTimeId) {
        this.slot_time_id = slotTimeId;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getMapLocation() {
        return map_location;
    }

    public void setMapLocation(String mapLocation) {
        this.map_location = mapLocation;
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

    public Integer getPackage() {
        return package_id;
    }

    public void setPackage(Integer _packageData) {
        this.package_id = _packageData;
    }

    public String getCoupenCode() {
        return coupen_code;
    }

    public void setCoupenCode(String coupenCode) {
        this.coupen_code = coupenCode;
    }

    public Integer getPackageAmount() {
        return package_amount;
    }

    public void setPackageAmount(Integer packageAmount) {
        this.package_amount = packageAmount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getAmountPaid() {
        return amount_paid;
    }

    public void setAmountPaid(Integer amountPaid) {
        this.amount_paid = amountPaid;
    }

    public Integer getPaymentStatus() {
        return payment_status;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.payment_status = paymentStatus;
    }

    public String getTransactionId() {
        return transaction_id;
    }

    public void setTransactionId(String transaction_id) {
        this.transaction_id = transaction_id;
    }

}
