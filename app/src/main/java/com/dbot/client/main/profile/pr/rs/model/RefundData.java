package com.dbot.client.main.profile.pr.rs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefundData {
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("refund_amount")
    @Expose
    private String refundAmount;
    @SerializedName("refund_status")
    @Expose
    private RefundStatus refundStatus;
    @SerializedName("refund_initiated_on")
    @Expose
    private String refundInitiatedOn;
    @SerializedName("refund_processed_on")
    @Expose
    private String refundProcessedOn;
    @SerializedName("refund_credited_on")
    @Expose
    private String refundCreditedOn;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public RefundStatus getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(RefundStatus refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundInitiatedOn() {
        return refundInitiatedOn;
    }

    public void setRefundInitiatedOn(String refundInitiatedOn) {
        this.refundInitiatedOn = refundInitiatedOn;
    }

    public String getRefundProcessedOn() {
        return refundProcessedOn;
    }

    public void setRefundProcessedOn(String refundProcessedOn) {
        this.refundProcessedOn = refundProcessedOn;
    }

    public String getRefundCreditedOn() {
        return refundCreditedOn;
    }

    public void setRefundCreditedOn(String refundCreditedOn) {
        this.refundCreditedOn = refundCreditedOn;
    }
}
