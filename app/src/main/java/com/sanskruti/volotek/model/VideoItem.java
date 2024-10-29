package com.sanskruti.volotek.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class VideoItem {
    @NonNull
    @SerializedName("id")
    public String id;
    @SerializedName("postId")
    public String postId;
    @SerializedName("language")
    public String language;
    @SerializedName("image")
    public String image;
    @SerializedName("thumbnail")
    public String thumbnail;
    @SerializedName("video")
    public String video;
    @SerializedName("gif")
    public String gif;
    @SerializedName("is_paid")
    public String isPaid;
    @SerializedName("type")
    public String type;
}
