package com.eleganzit.vkcvendor.model.plan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Grid {
    @SerializedName("grid_value")
    @Expose
    private String gridValue;
    @SerializedName("scheduled_quantity")
    @Expose
    private String scheduledQuantity;

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

}
