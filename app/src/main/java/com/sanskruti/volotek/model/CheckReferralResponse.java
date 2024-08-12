package com.sanskruti.volotek.model;

import com.google.gson.annotations.SerializedName;

public class CheckReferralResponse {
    @SerializedName("status")
    Boolean status;
    @SerializedName("type")
    String userType;

    @SerializedName("referral_code")
    String referralCode;

    public Boolean getStatus() {
        return status;
    }

    public String getUserType() {
        return userType;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
}
