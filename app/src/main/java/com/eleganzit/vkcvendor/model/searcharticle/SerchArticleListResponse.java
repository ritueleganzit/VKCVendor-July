package com.eleganzit.vkcvendor.model.searcharticle;

import com.eleganzit.vkcvendor.model.grid.Datum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerchArticleListResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<SerchArticleList> data = null;

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

    public List<SerchArticleList> getData() {
        return data;
    }

    public void setData(List<SerchArticleList> data) {
        this.data = data;
    }

}
