package com.eleganzit.vkcvendor.model.AllHourlyDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GridData {
    @SerializedName("grid_value")
    @Expose
    private String gridValue;
    @SerializedName("quality_produced")
    @Expose
    private String qualityProduced;
    @SerializedName("scheduled_quantity")
    @Expose
    private String scheduledQuantity;

    public String getGridValue() {
        return gridValue;
    }

    public void setGridValue(String gridValue) {
        this.gridValue = gridValue;
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
}
