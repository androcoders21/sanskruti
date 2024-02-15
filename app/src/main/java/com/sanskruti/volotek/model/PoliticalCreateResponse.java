package com.sanskruti.volotek.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PoliticalCreateResponse implements Serializable {
    @SerializedName("success")
    public Boolean success;
    @SerializedName("message")
    public String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
