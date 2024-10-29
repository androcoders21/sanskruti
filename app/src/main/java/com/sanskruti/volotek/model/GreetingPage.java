package com.sanskruti.volotek.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GreetingPage {
    @NonNull
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("icon")
    public String icon;
    @SerializedName("sub_categories")
    public List<CategoryItem> subCategories;

    public GreetingPage(@NonNull String id, String name, String icon, List<CategoryItem> subCategories) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.subCategories = subCategories;
    }
}
