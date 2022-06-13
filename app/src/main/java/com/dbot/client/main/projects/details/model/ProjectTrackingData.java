package com.dbot.client.main.projects.details.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectTrackingData {
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("requested_on")
    @Expose
    private String requestedOn;
    @SerializedName("contact_person_confirmation_eta")
    @Expose
    private String contactPersonConfirmationEta;
    @SerializedName("contact_person_confirmation")
    @Expose
    private String contactPersonConfirmation;
    @SerializedName("site_access_confirmation_eta")
    @Expose
    private String siteAccessConfirmationEta;
    @SerializedName("site_access_confirmation")
    @Expose
    private String siteAccessConfirmation;
    @SerializedName("site_documentation_eta")
    @Expose
    private String siteDocumentationEta;
    @SerializedName("site_documentation")
    @Expose
    private String siteDocumentation;
    @SerializedName("files_sharing_eta")
    @Expose
    private String filesSharingEta;
    @SerializedName("files_sharing")
    @Expose
    private String filesSharing;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(String requestedOn) {
        this.requestedOn = requestedOn;
    }

    public String getContactPersonConfirmationEta() {
        return contactPersonConfirmationEta;
    }

    public void setContactPersonConfirmationEta(String contactPersonConfirmationEta) {
        this.contactPersonConfirmationEta = contactPersonConfirmationEta;
    }

    public String getContactPersonConfirmation() {
        return contactPersonConfirmation;
    }

    public void setContactPersonConfirmation(String contactPersonConfirmation) {
        this.contactPersonConfirmation = contactPersonConfirmation;
    }

    public String getSiteAccessConfirmationEta() {
        return siteAccessConfirmationEta;
    }

    public void setSiteAccessConfirmationEta(String siteAccessConfirmationEta) {
        this.siteAccessConfirmationEta = siteAccessConfirmationEta;
    }

    public String getSiteAccessConfirmation() {
        return siteAccessConfirmation;
    }

    public void setSiteAccessConfirmation(String siteAccessConfirmation) {
        this.siteAccessConfirmation = siteAccessConfirmation;
    }

    public String getSiteDocumentationEta() {
        return siteDocumentationEta;
    }

    public void setSiteDocumentationEta(String siteDocumentationEta) {
        this.siteDocumentationEta = siteDocumentationEta;
    }

    public String getSiteDocumentation() {
        return siteDocumentation;
    }

    public void setSiteDocumentation(String siteDocumentation) {
        this.siteDocumentation = siteDocumentation;
    }

    public String getFilesSharingEta() {
        return filesSharingEta;
    }

    public void setFilesSharingEta(String filesSharingEta) {
        this.filesSharingEta = filesSharingEta;
    }

    public String getFilesSharing() {
        return filesSharing;
    }

    public void setFilesSharing(String filesSharing) {
        this.filesSharing = filesSharing;
    }
}
