package com.dbot.client.main.newrequest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class BookSlot {
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

}
