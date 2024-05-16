package com.sanskruti.volotek.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Watermark implements Serializable {
    @SerializedName("id")
    public String id;
    @SerializedName("watermark_image")
    public String watermarkImage;
    @SerializedName("position")
    public Integer position;
    @SerializedName("show_watermark")
    public Integer showWatermark;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
}
