package com.sanskruti.volotek.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchData {

    @SerializedName("status")
    public Boolean status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<> data;

}
