package com.eleganzit.vkcvendor.model.poline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineData {
    @SerializedName("line_id")
    @Expose
    private String lineId;
    @SerializedName("line_number")
    @Expose
    private String lineNumber;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }
}
