package com.eleganzit.vkcvendor.model.plan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlanResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("assign_count")
    @Expose
    private String assignCount;
    @SerializedName("data")
    @Expose
    private List<PNumber> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAssignCount() {
        return assignCount;
    }

    public void setAssignCount(String assignCount) {
        this.assignCount = assignCount;
    }

    public List<PNumber> getData() {
        return data;
    }

    public void setData(List<PNumber> data) {
        this.data = data;
    }

}
