package com.sanskruti.volotek.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class politicalProfileModel implements Serializable {

    @SerializedName("_id")
    public String _id;
    @SerializedName("pUserId")
    public String pUserId;

    @SerializedName("pName")
    public String pName;

    @SerializedName("pPhone")
    public String pPhone;

    @SerializedName("pEmail")
    public String pEmail;


    @SerializedName("pFacebookUsername")
    public String pFacebookUsername;

    @SerializedName("pInstagramUsername")
    public String pInstagramUsername;




    @SerializedName("pTwitterUsername")
    public String pTwitterUsername;

    @SerializedName("pDesignation1")
    public String pDesignation1;

    @SerializedName("pDesignation2")
    public String pDesignation2;

    @SerializedName("pProfileImg")
    public String pProfileImg;


    @SerializedName("pPartyImg")
    public String pPartyImg;

    @SerializedName("pSignatureImg")
    public String pSignatureImg;




    @SerializedName("pLeaderImg1")
    public String pLeaderImg1;


    @SerializedName("pLeaderImg2")
    public String pLeaderImg2;

    @SerializedName("pLeaderImg3")
    public String pLeaderImg3;

    @SerializedName("pLeaderImg4")
    public String pLeaderImg4;


    @SerializedName("pLeaderImg5")
    public String pLeaderImg5;

    @SerializedName("pLeaderImg6")
    public String pLeaderImg6;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getpLeaderImg1() {
        return pLeaderImg1;
    }

    public void setpLeaderImg1(String pLeaderImg1) {
        this.pLeaderImg1 = pLeaderImg1;
    }

    public String getpLeaderImg2() {
        return pLeaderImg2;
    }

    public void setpLeaderImg2(String pLeaderImg2) {
        this.pLeaderImg2 = pLeaderImg2;
    }

    public String getpLeaderImg3() {
        return pLeaderImg3;
    }

    public void setpLeaderImg3(String pLeaderImg3) {
        this.pLeaderImg3 = pLeaderImg3;
    }

    public String getpLeaderImg4() {
        return pLeaderImg4;
    }

    public void setpLeaderImg4(String pLeaderImg4) {
        this.pLeaderImg4 = pLeaderImg4;
    }

    public String getpLeaderImg5() {
        return pLeaderImg5;
    }

    public void setpLeaderImg5(String pLeaderImg5) {
        this.pLeaderImg5 = pLeaderImg5;
    }

    public String getpLeaderImg6() {
        return pLeaderImg6;
    }

    public void setpLeaderImg6(String pLeaderImg6) {
        this.pLeaderImg6 = pLeaderImg6;
    }

    public String getpUserId() {
        return pUserId;
    }

    public void setpUserId(String pUserId) {
        this.pUserId = pUserId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPhone() {
        return pPhone;
    }

    public void setpPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public String getpEmail() {
        return pEmail;
    }

    public void setpEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getpFacebookUsername() {
        return pFacebookUsername;
    }

    public void setpFacebookUsername(String pFacebookUsername) {
        this.pFacebookUsername = pFacebookUsername;
    }

    public String getpInstagramUsername() {
        return pInstagramUsername;
    }

    public void setpInstagramUsername(String pInstagramUsername) {
        this.pInstagramUsername = pInstagramUsername;
    }

    public String getpTwitterUsername() {
        return pTwitterUsername;
    }

    public void setpTwitterUsername(String pTwitterUsername) {
        this.pTwitterUsername = pTwitterUsername;
    }

    public String getpDesignation1() {
        return pDesignation1;
    }

    public void setpDesignation1(String pDesignation1) {
        this.pDesignation1 = pDesignation1;
    }

    public String getpDesignation2() {
        return pDesignation2;
    }

    public void setpDesignation2(String pDesignation2) {
        this.pDesignation2 = pDesignation2;
    }

    public String getpProfileImg() {
        return pProfileImg;
    }

    public void setpProfileImg(String pProfileImg) {
        this.pProfileImg = pProfileImg;
    }

    public String getpPartyImg() {
        return pPartyImg;
    }

    public void setpPartyImg(String pPartyImg) {
        this.pPartyImg = pPartyImg;
    }

    public String getpSignatureImg() {
        return pSignatureImg;
    }

    public void setpSignatureImg(String pSignatureImg) {
        this.pSignatureImg = pSignatureImg;
    }
}
