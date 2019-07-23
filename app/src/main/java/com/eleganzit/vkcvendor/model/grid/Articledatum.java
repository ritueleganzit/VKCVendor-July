package com.eleganzit.vkcvendor.model.grid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Articledatum {
    @SerializedName("article_name")
    @Expose
    private String articleName;
    @SerializedName("gridData")
    @Expose
    private List<GridDatum> gridData = null;

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public List<GridDatum> getGridData() {
        return gridData;
    }

    public void setGridData(List<GridDatum> gridData) {
        this.gridData = gridData;
    }

}
