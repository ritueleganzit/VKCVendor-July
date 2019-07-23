package com.eleganzit.vkcvendor.model.AllHourlyDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleData {
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("article_name")
    @Expose
    private String articleName;
    @SerializedName("line_id")
    @Expose
    private String lineId;
    @SerializedName("line_number")
    @Expose
    private String lineNumber;
    @SerializedName("gridData")
    @Expose
    private List<GridData> gridData = null;


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

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

    public List<GridData> getGridData() {
        return gridData;
    }

    public void setGridData(List<GridData> gridData) {
        this.gridData = gridData;
    }
}
