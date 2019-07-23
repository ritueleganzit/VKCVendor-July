package com.eleganzit.vkcvendor.model.defect;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefectArtResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("countData")
    @Expose
    private Integer countData;
    @SerializedName("data")
    @Expose
    private List<DefectArt> data = null;

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

    public Integer getCountData() {
        return countData;
    }

    public void setCountData(Integer countData) {
        this.countData = countData;
    }

    public List<DefectArt> getData() {
        return data;
    }

    public void setData(List<DefectArt> data) {
        this.data = data;
    }
}
