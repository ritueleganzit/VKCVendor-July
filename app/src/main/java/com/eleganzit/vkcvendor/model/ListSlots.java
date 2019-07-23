package com.eleganzit.vkcvendor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListSlots {

    @SerializedName("slot_id")
    @Expose
    private String slotId;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("slot_start_time")
    @Expose
    private String slotStartTime;
    @SerializedName("slot_end_time")
    @Expose
    private String slotEndTime;
    @SerializedName("slot_name")
    @Expose
    private String slotName;

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getSlotStartTime() {
        return slotStartTime;
    }

    public void setSlotStartTime(String slotStartTime) {
        this.slotStartTime = slotStartTime;
    }

    public String getSlotEndTime() {
        return slotEndTime;
    }

    public void setSlotEndTime(String slotEndTime) {
        this.slotEndTime = slotEndTime;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }
}
