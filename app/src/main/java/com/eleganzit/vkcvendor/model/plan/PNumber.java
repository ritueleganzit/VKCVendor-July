package com.eleganzit.vkcvendor.model.plan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PNumber {
    private boolean isEnabled;
    private boolean isChecked;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @SerializedName("pur_doc_num")
    @Expose
    private String purDocNum;
    @SerializedName("mapping")
    @Expose
    private String mapping;
    @SerializedName("articledata")
    @Expose
    private List<Article> articledata = null;

    public String getPurDocNum() {
        return purDocNum;
    }

    public void setPurDocNum(String purDocNum) {
        this.purDocNum = purDocNum;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public List<Article> getArticledata() {
        return articledata;
    }

    public void setArticledata(List<Article> articledata) {
        this.articledata = articledata;
    }

}
