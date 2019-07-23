package com.eleganzit.vkcvendor.model.AllHourlyDetail;

import com.eleganzit.vkcvendor.model.grid.Articledatum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllHourlyDetailData {

    @SerializedName("pur_doc_num")
    @Expose
    private String purDocNum;
    @SerializedName("articledata")
    @Expose
    private List<ArticleData> articledata = null;

    public String getPurDocNum() {
        return purDocNum;
    }

    public void setPurDocNum(String purDocNum) {
        this.purDocNum = purDocNum;
    }

    public List<ArticleData> getArticledata() {
        return articledata;
    }

    public void setArticledata(List<ArticleData> articledata) {
        this.articledata = articledata;
    }
}
