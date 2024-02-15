package com.sanskruti.volotek.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PoliticalProfileBaseModel {
    @SerializedName("success")
    public Boolean success;

    @SerializedName("profiles")
    public List<politicalProfileModel> profiles;
}
