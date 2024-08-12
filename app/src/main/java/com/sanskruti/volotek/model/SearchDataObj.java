package com.sanskruti.volotek.model;

import com.google.gson.annotations.SerializedName;

public class SearchDataObj {

    @SerializedName("id")
    public int id;
    @SerializedName("path")
    public String path;
    @SerializedName("category")
    public String category;
    @SerializedName("type")
    public String type;
    @SerializedName("position")
    public String position;

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getPosition() {
        return position;
    }
}
