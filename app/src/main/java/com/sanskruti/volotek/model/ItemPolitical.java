package com.sanskruti.volotek.model;

public class ItemPolitical {

    public String pName;

    public String pPhone;
    public String pEmail;

    public String pFacebookUsername;

    public String pInstagramUsername;

    public String pTwitterUsername;

    public String pDesignation1;

    public String pDesignation2;


    public String pProfileImg;

    public String pPartyImg;

    public String pLeaderImg1;

    public String pLeaderImg2;

    public String pLeaderImg3;




    public String pLeaderImg4;

    public String pLeaderImg5;

    public String pLeaderImg6;

    public String id;

    public String profileId;
    public ItemPolitical(String profileId,String id, String pName, String pPhone, String pEmail,
            String pFacebookUsername, String pInstagramUsername, String pTwitterUsername,
                         String pDesignation1, String pDesignation2, String pProfileImg,
                         String pPartyImg, String pLeaderImg1, String pLeaderImg2,
                         String pLeaderImg3, String pLeaderImg4, String pLeaderImg5,String pLeaderImg6) {
        this.profileId =profileId;
        this.id = id;
        this.pName = pName;
        this.pPhone = pPhone;
        this.pEmail = pEmail;
        this.pFacebookUsername = pFacebookUsername;
        this.pInstagramUsername = pInstagramUsername;
        this.pTwitterUsername = pTwitterUsername;
        this.pDesignation1 = pDesignation1;
        this.pDesignation2 = pDesignation2;
        this.pProfileImg = pProfileImg;
        this.pPartyImg = pPartyImg;
        this.pLeaderImg1 = pLeaderImg1;
        this.pLeaderImg2 = pLeaderImg2;
        this.pLeaderImg3 = pLeaderImg3;
        this.pLeaderImg4 = pLeaderImg4;
        this.pLeaderImg5 = pLeaderImg5;
        this.pLeaderImg6 = pLeaderImg6;
    }

}
