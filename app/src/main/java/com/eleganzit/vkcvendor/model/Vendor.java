package com.eleganzit.vkcvendor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vendor {

    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("vendor_email")
    @Expose
    private String vendorEmail;
    @SerializedName("vendor_mobile")
    @Expose
    private String vendorMobile;
    @SerializedName("vendor_capacity")
    @Expose
    private String vendorCapacity;
    @SerializedName("vendor_contact_person")
    @Expose
    private String vendorContactPerson;
    @SerializedName("vendor_contact_person_number")
    @Expose
    private String vendorContactPersonNumber;
    @SerializedName("vendor_address")
    @Expose
    private String vendorAddress;
    @SerializedName("vendor_status")
    @Expose
    private String vendorStatus;
    @SerializedName("vendor_start_time")
    @Expose
    private String vendorStartTime;
    @SerializedName("vendor_end_time")
    @Expose
    private String vendorEndTime;
    @SerializedName("assign_count")
    @Expose
    private String assignCount;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getVendorMobile() {
        return vendorMobile;
    }

    public void setVendorMobile(String vendorMobile) {
        this.vendorMobile = vendorMobile;
    }

    public String getVendorCapacity() {
        return vendorCapacity;
    }

    public void setVendorCapacity(String vendorCapacity) {
        this.vendorCapacity = vendorCapacity;
    }

    public String getVendorContactPerson() {
        return vendorContactPerson;
    }

    public void setVendorContactPerson(String vendorContactPerson) {
        this.vendorContactPerson = vendorContactPerson;
    }

    public String getVendorContactPersonNumber() {
        return vendorContactPersonNumber;
    }

    public void setVendorContactPersonNumber(String vendorContactPersonNumber) {
        this.vendorContactPersonNumber = vendorContactPersonNumber;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getVendorStatus() {
        return vendorStatus;
    }

    public void setVendorStatus(String vendorStatus) {
        this.vendorStatus = vendorStatus;
    }

    public String getVendorStartTime() {
        return vendorStartTime;
    }

    public void setVendorStartTime(String vendorStartTime) {
        this.vendorStartTime = vendorStartTime;
    }

    public String getVendorEndTime() {
        return vendorEndTime;
    }

    public void setVendorEndTime(String vendorEndTime) {
        this.vendorEndTime = vendorEndTime;
    }

    public String getAssignCount() {
        return assignCount;
    }

    public void setAssignCount(String assignCount) {
        this.assignCount = assignCount;
    }


}
