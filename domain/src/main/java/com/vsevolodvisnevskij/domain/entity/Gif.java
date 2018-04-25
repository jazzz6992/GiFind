package com.vsevolodvisnevskij.domain.entity;

/**
 * Created by vsevolodvisnevskij on 26.03.2018.
 */

public class Gif {
    private String id;
    private String previewUrl;
    private String originalUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
