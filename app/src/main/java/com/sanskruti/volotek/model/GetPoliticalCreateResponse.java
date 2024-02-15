package com.sanskruti.volotek.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPoliticalCreateResponse {
    @SerializedName("success")
    public Boolean success;

    @SerializedName("profile")
    public politicalProfileModel profiles;
}
