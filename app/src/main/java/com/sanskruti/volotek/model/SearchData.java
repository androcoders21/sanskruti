package com.sanskruti.volotek.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchData {

    @SerializedName("status")
    public Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SearchDataObj> getData() {
        return data;
    }

    public void setData(List<SearchDataObj> data) {
        this.data = data;
    }

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<SearchDataObj> data;

}
