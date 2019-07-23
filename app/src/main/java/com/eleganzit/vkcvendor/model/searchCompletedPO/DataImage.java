package com.eleganzit.vkcvendor.model.searchCompletedPO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataImage {
    @SerializedName("defect_product_image")
    @Expose
    private String defectProductImage;

    public String getDefectProductImage() {
        return defectProductImage;
    }

    public void setDefectProductImage(String defectProductImage) {
        this.defectProductImage = defectProductImage;
    }

}
