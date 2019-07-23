package com.eleganzit.vkcvendor.model.grid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {
    @SerializedName("pur_doc_num")
    @Expose
    private String purDocNum;
    @SerializedName("article_name")
    @Expose
    private String articleName;
    @SerializedName("line_id")
    @Expose
    private String lineId;
    @SerializedName("line_number")
    @Expose
    private String lineNumber;
    @SerializedName("articledata")
    @Expose
    private List<Articledatum> articledata = null;

    public String getPurDocNum() {
        return purDocNum;
    }

    public void setPurDocNum(String purDocNum) {
        this.purDocNum = purDocNum;
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

    public List<Articledatum> getArticledata() {
        return articledata;
    }

    public void setArticledata(List<Articledatum> articledata) {
        this.articledata = articledata;
    }

}
