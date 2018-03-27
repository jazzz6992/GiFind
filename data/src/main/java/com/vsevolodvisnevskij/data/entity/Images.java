
package com.vsevolodvisnevskij.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {


    @SerializedName("fixed_height")
    @Expose
    private FixedHeight fixedHeight;

    public FixedHeight getFixedHeight() {
        return fixedHeight;
    }

    public void setFixedHeight(FixedHeight fixedHeight) {
        this.fixedHeight = fixedHeight;
    }
}
