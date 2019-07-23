package com.eleganzit.vkcvendor.model.searchCompletedPO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("dataImage")
    @Expose
    private List<DataImage> dataImage = null;

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

    public List<DataImage> getDataImage() {
        return dataImage;
    }

    public void setDataImage(List<DataImage> dataImage) {
        this.dataImage = dataImage;
    }
}
