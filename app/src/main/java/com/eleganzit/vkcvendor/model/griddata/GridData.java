package com.eleganzit.vkcvendor.model.griddata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GridData {
    @SerializedName("grid_value")
    @Expose
    private String gridValue;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("quality_produced")
    @Expose
    private String qualityProduced;
    @SerializedName("scheduled_quantity")
    @Expose
    private String scheduledQuantity;
    @SerializedName("complete")
    @Expose
    private Double complete;

    public String getGridValue() {
        return gridValue;
    }

    public void setGridValue(String gridValue) {
        this.gridValue = gridValue;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getQualityProduced() {
        return qualityProduced;
    }

    public void setQualityProduced(String qualityProduced) {
        this.qualityProduced = qualityProduced;
    }

    public String getScheduledQuantity() {
        return scheduledQuantity;
    }

    public void setScheduledQuantity(String scheduledQuantity) {
        this.scheduledQuantity = scheduledQuantity;
    }

    public Double getComplete() {
        return complete;
    }

    public void setComplete(Double complete) {
        this.complete = complete;
    }

}
