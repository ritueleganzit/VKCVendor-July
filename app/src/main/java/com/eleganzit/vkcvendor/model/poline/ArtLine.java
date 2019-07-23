package com.eleganzit.vkcvendor.model.poline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtLine {
    @SerializedName("article")
    @Expose
    private String article;
    @SerializedName("item")
    @Expose
    private String item;


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

}
