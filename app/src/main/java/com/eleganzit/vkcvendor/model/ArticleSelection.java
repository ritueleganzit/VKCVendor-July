package com.eleganzit.vkcvendor.model;

import java.util.List;

public class ArticleSelection {
    List<String> articlelist;

    public ArticleSelection(List<String> articlelist) {
        this.articlelist = articlelist;
    }

    public List<String> getArticlelist() {
        return articlelist;
    }

    public void setArticlelist(List<String> articlelist) {
        this.articlelist = articlelist;
    }
}
