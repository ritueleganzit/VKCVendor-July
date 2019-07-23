package com.eleganzit.vkcvendor.model.searchCompletedPO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompletedPOData {
    @SerializedName("countData")
    @Expose
    private Integer countData;
    @SerializedName("po_doc_num")
    @Expose
    private String poDocNum;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("line_number")
    @Expose
    private String lineNumber;
    @SerializedName("article")
    @Expose
    private String article;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("countDefect")
    @Expose
    private Integer countDefect;
    @SerializedName("quality_produced")
    @Expose
    private Integer quality_produced;
    @SerializedName("jsonImage")
    @Expose
    private ImagesResponse jsonImage;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getCountData() {
        return countData;
    }

    public void setCountData(Integer countData) {
        this.countData = countData;
    }

    public String getPoDocNum() {
        return poDocNum;
    }

    public void setPoDocNum(String poDocNum) {
        this.poDocNum = poDocNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Integer getCountDefect() {
        return countDefect;
    }

    public void setCountDefect(Integer countDefect) {
        this.countDefect = countDefect;
    }

    public Integer getQuality_produced() {
        return quality_produced;
    }

    public void setQuality_produced(Integer quality_produced) {
        this.quality_produced = quality_produced;
    }

    public ImagesResponse getJsonImage() {
        return jsonImage;
    }

    public void setJsonImage(ImagesResponse jsonImage) {
        this.jsonImage = jsonImage;
    }

}
