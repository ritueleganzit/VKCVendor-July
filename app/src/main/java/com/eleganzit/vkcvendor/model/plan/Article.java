package com.eleganzit.vkcvendor.model.plan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {
    @SerializedName("article_name")
    @Expose
    private String articleName;

    @SerializedName("item")
    @Expose
    private String item;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @SerializedName("gridData")
    @Expose
    private List<Grid> gridData = null;

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public List<Grid> getGridData() {
        return gridData;
    }

    public void setGridData(List<Grid> gridData) {
        this.gridData = gridData;
    }

}
