package com.eleganzit.vkcvendor.model.grid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GridDatum {
    @SerializedName("grid_value")
    @Expose
    private String gridValue;
    @SerializedName("scheduled_quantity")
    @Expose
    private String scheduledQuantity;
    @SerializedName("quality_produced")
    @Expose
    private String qualityProduced;

    public String getGridValue() {
        return gridValue;
    }

    public void setGridValue(String gridValue) {
        this.gridValue = gridValue;
    }

    public String getScheduledQuantity() {
        return scheduledQuantity;
    }

    public void setScheduledQuantity(String scheduledQuantity) {
        this.scheduledQuantity = scheduledQuantity;
    }

    public String getQualityProduced() {
        return qualityProduced;
    }

    public void setQualityProduced(String qualityProduced) {
        this.qualityProduced = qualityProduced;
    }

}
