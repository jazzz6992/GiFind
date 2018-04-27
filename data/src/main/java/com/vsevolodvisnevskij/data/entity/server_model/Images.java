package com.vsevolodvisnevskij.data.entity.server_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {


    @SerializedName("preview_gif")
    @Expose
    private PreviewGif previewGif;

    @SerializedName("original")
    @Expose
    private Original original;

    public PreviewGif getPreviewGif() {
        return previewGif;
    }

    public void setPreviewGif(PreviewGif previewGif) {
        this.previewGif = previewGif;
    }

    public Original getOriginal() {
        return original;
    }

    public void setOriginal(Original original) {
        this.original = original;
    }
}
