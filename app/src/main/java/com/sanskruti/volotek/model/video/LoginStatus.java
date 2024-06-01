package com.sanskruti.volotek.model.video;

import com.google.gson.annotations.SerializedName;

public class LoginStatus {
    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getIsLogIn() {
        return isLogIn;
    }

    Boolean status;
    String message;
    @SerializedName("is_user_logged_in")
    String isLogIn;
}
