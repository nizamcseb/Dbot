package com.dbot.client.main.newrequest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientProjectData {
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("book_date")
    @Expose
    private String bookDate;
    @SerializedName("slot_time_id")
    @Expose
    private String slotTimeId;
    @SerializedName("booked_on")
    @Expose
    private String bookedOn;
    @SerializedName("map_location")
    @Expose
    private String mapLocation;
    @SerializedName("door_number")
    @Expose
    private String doorNumber;
    @SerializedName("building_name")
    @Expose
    private String buildingName;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("project_name")
    @Expose
    private String projectName;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("contact_person_phone")
    @Expose
    private String contactPersonPhone;
    @SerializedName("property_size")
    @Expose
    private PropertySize propertySize;
    @SerializedName("project_type")
    @Expose
    private String projectType;
    @SerializedName("scope")
    @Expose
    private List<Integer> scope = null;
    @SerializedName("package")
    @Expose
    private Package _package;
    @SerializedName("coupen_code")
    @Expose
    private String coupenCode;
    @SerializedName("package_amount")
    @Expose
    private String packageAmount;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("amount_paid")
    @Expose
    private String amountPaid;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("project_status")
    @Expose
    private ProjectStatus projectStatus;
    @SerializedName("refund_status")
    @Expose
    private String refundStatus;
    @SerializedName("display_date")
    @Expose
    private String displayDate;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getSlotTimeId() {
        return slotTimeId;
    }

    public void setSlotTimeId(String slotTimeId) {
        this.slotTimeId = slotTimeId;
    }

    public String getBookedOn() {
        return bookedOn;
    }

    public void setBookedOn(String bookedOn) {
        this.bookedOn = bookedOn;
    }

    public String getMapLocation() {
        return mapLocation;
    }

    public void setMapLocation(String mapLocation) {
        this.mapLocation = mapLocation;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public PropertySize getPropertySize() {
        return propertySize;
    }

    public void setPropertySize(PropertySize propertySize) {
        this.propertySize = propertySize;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public List<Integer> getScope() {
        return scope;
    }

    public void setScope(List<Integer> scope) {
        this.scope = scope;
    }

    public Package getPackage() {
        return _package;
    }

    public void setPackage(Package _package) {
        this._package = _package;
    }

    public String getCoupenCode() {
        return coupenCode;
    }

    public void setCoupenCode(String coupenCode) {
        this.coupenCode = coupenCode;
    }

    public String getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(String packageAmount) {
        this.packageAmount = packageAmount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }
}
