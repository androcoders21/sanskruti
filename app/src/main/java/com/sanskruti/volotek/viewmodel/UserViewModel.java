package com.sanskruti.volotek.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sanskruti.volotek.api.ApiStatus;
import com.sanskruti.volotek.model.BusinessItem;
import com.sanskruti.volotek.model.GetPoliticalCreateResponse;
import com.sanskruti.volotek.model.PoliticalCreateResponse;
import com.sanskruti.volotek.model.UserItem;
import com.sanskruti.volotek.model.WhatsappOtpResponse;
import com.sanskruti.volotek.model.politicalProfileModel;
import com.sanskruti.volotek.respository.UserRespository;

public class UserViewModel extends ViewModel {


    UserRespository respository;

    public UserViewModel() {
        this.respository = new UserRespository();
    }

    public LiveData<UserItem> getUserById(String userId) {

        return respository.getUserById(userId);
    }

    public LiveData<UserItem> userLoginGoogle(String email, String displayName, String photoUrl, String mobile, String loginType) {

        return respository.userLoginGoogle(email, displayName, photoUrl, mobile, loginType);

    }

    public LiveData<UserItem> profileUpdate(String userId, String profileImagePath, String userName, String userEmail, String phoneNo, String userDesignation) {

        return respository.profileUpdate(userId, profileImagePath, userName, userEmail, phoneNo, userDesignation);
    }

    public LiveData<ApiStatus> contactUsMessage(String uid, String name, String email, String number, String message) {
        return respository.contactUsMessage(uid, name, email, number, message);

    }

    public LiveData<ApiStatus> storeTransaction(String userId, String planId, String paymentId, String planPrice, String couponCode, String type) {

        return respository.storeTransaction(userId, planId, paymentId, planPrice, couponCode,type);
    }

    public LiveData<BusinessItem> setDefaultBusiness(String type, String userId, String businessId) {


        return respository.setDefaultBusiness(type, userId, businessId);
    }

    public LiveData<BusinessItem> getDefaultBusiness(String userId, String type) {

        return respository.getDefaultBusiness(userId, type);
    }

    public LiveData<BusinessItem> submitBusiness(String userId, String businessId, String businessImage, String name, String email, String phone, String website, String address, String insta, String youtube, String facebook, String twitter,String tagline, String type, String businesscategories1) {

        return respository.submitBusiness(userId, businessId, businessImage, name, email, phone, website, address, insta, youtube, facebook, twitter, tagline,  type, businesscategories1);


    }


    public LiveData<PoliticalCreateResponse> submitPolitical(String userId, String profileImage, String pSignatureImg, String pPartyImg, String pLeaderImg1, String pLeaderImg2,
                                                             String pLeaderImg3, String pLeaderImg4, String pLeaderImg5, String pLeaderImg6,
                                                             String pName, String pPhone, String pEmail,
                                                             String pFacebookUsername, String pInstagramUsername, String pTwitterUsername,
                                                             String pDesignation1, String pDesignation2) {

        return respository.submitPolitical(userId, profileImage, pSignatureImg, pPartyImg,
                pLeaderImg1, pLeaderImg2, pLeaderImg3, pLeaderImg4, pLeaderImg5, pLeaderImg6,
                pName, pPhone, pEmail,
                pFacebookUsername, pInstagramUsername, pTwitterUsername,
                pDesignation1,pDesignation2 );


    }

    public LiveData<PoliticalCreateResponse> updatePolitical(String profileId,String userId, String profileImage, String pSignatureImg, String pPartyImg, String pLeaderImg1, String pLeaderImg2,
                                                             String pLeaderImg3, String pLeaderImg4, String pLeaderImg5, String pLeaderImg6,
                                                             String pName, String pPhone, String pEmail,
                                                             String pFacebookUsername, String pInstagramUsername, String pTwitterUsername,
                                                             String pDesignation1, String pDesignation2) {

        return respository.updatePolitical(profileId,userId, profileImage, pSignatureImg, pPartyImg,
                pLeaderImg1, pLeaderImg2, pLeaderImg3, pLeaderImg4, pLeaderImg5, pLeaderImg6,
                pName, pPhone, pEmail,
                pFacebookUsername, pInstagramUsername, pTwitterUsername,
                pDesignation1,pDesignation2 );


    }

    public LiveData<GetPoliticalCreateResponse> getPolitical(String pProfileId) {

        return respository.getPolitical(pProfileId);


    }

    public LiveData<WhatsappOtpResponse> createWhatsappOtp(String number) {

        return respository.createWhatsappOtp(number);

    }

}

