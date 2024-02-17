package com.sanskruti.volotek.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sanskruti.volotek.api.ApiClient;
import com.sanskruti.volotek.api.ApiClientSecond;
import com.sanskruti.volotek.api.ApiService;
import com.sanskruti.volotek.api.ApiStatus;
import com.sanskruti.volotek.model.BusinessItem;
import com.sanskruti.volotek.model.GetPoliticalCreateResponse;
import com.sanskruti.volotek.model.PoliticalCreateResponse;
import com.sanskruti.volotek.model.PoliticalProfileBaseModel;
import com.sanskruti.volotek.model.UserItem;
import com.sanskruti.volotek.model.WhatsappOtpResponse;
import com.sanskruti.volotek.model.politicalProfileModel;
import com.sanskruti.volotek.utils.Constant;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRespository {


    private ApiService apiService;

    public UserRespository() {
        apiService = ApiClient.getApiDataService();
    }

    public LiveData<UserItem> getUserById(String userId) {
        MutableLiveData<UserItem> data = new MutableLiveData<>();
        apiService.getUserById(userId).enqueue(new Callback<UserItem>() {
            @Override
            public void onResponse(Call<UserItem> call, Response<UserItem> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserItem> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<UserItem> userLoginGoogle(String email, String displayName, String photoUrl, String mobile, String loginType) {
        MutableLiveData<UserItem> data = new MutableLiveData<>();
        apiService.userLoginGoogle(displayName, email, photoUrl, mobile, loginType, "").enqueue(new Callback<UserItem>() {
            @Override
            public void onResponse(Call<UserItem> call, Response<UserItem> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserItem> call, Throwable t) {
                t.printStackTrace();
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<UserItem> profileUpdate(String userId, String profileImagePath,  String userName, String userEmail, String phoneNo, String userDesignation) {

        MutableLiveData<UserItem> data = new MutableLiveData<>();

        RequestBody requestFile = null;
        RequestBody imageuri = null;
        MultipartBody.Part body = null;


        if (profileImagePath != null) {

            File file = new File(profileImagePath);
            requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
            imageuri = RequestBody.create(MediaType.parse(Constant.multipart), profileImagePath);
            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        }

        RequestBody userid = RequestBody.create(MediaType.parse(Constant.multipart), userId);
        RequestBody name = RequestBody.create(MediaType.parse(Constant.multipart), userName);
        RequestBody email = RequestBody.create(MediaType.parse(Constant.multipart), userEmail);
        RequestBody phone = RequestBody.create(MediaType.parse(Constant.multipart), phoneNo);
        RequestBody designation = RequestBody.create(MediaType.parse(Constant.multipart), userDesignation);


        ApiClient.getApiDataService().profileUpdate(userid, imageuri, body, name, email, phone, designation).
                enqueue(new Callback<UserItem>() {
                    @Override
                    public void onResponse(Call<UserItem> call, Response<UserItem> response) {

                        Log.i("RESPONSE", "RESPONSE-->" + new Gson().toJson(response.body()));


                        data.setValue(response.body());


                    }

                    @Override
                    public void onFailure(Call<UserItem> call, Throwable t) {
                        t.printStackTrace();

                        data.setValue(null);

                    }
                });


        return data;
    }


    public LiveData<ApiStatus> contactUsMessage(String uid, String name, String email, String number, String message) {
        MutableLiveData<ApiStatus> data = new MutableLiveData<>();
        apiService.contactUsMessage(uid, name, email, number, message).enqueue(new Callback<ApiStatus>() {
            @Override
            public void onResponse(Call<ApiStatus> call, Response<ApiStatus> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ApiStatus> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<ApiStatus> storeTransaction(String userId, String planId, String paymentId, String planPrice, String couponCode, String type) {
        MutableLiveData<ApiStatus> data = new MutableLiveData<>();

        apiService.storeTransaction(userId, planId, paymentId, planPrice, couponCode,type).enqueue(new Callback<ApiStatus>() {
            @Override
            public void onResponse(Call<ApiStatus> call, Response<ApiStatus> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ApiStatus> call, Throwable t) {
                t.printStackTrace();
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<BusinessItem> getDefaultBusiness(String userId, String type) {
        MutableLiveData<BusinessItem> data = new MutableLiveData<>();
        apiService.getBusinessDefaultList(userId, type).enqueue(new Callback<BusinessItem>() {
            @Override
            public void onResponse(Call<BusinessItem> call, Response<BusinessItem> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BusinessItem> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<BusinessItem> setDefaultBusiness(String type, String userId, String businessId) {
        MutableLiveData<BusinessItem> data = new MutableLiveData<>();
        apiService.setDefaultBusiness(type, userId, businessId).enqueue(new Callback<BusinessItem>() {
            @Override
            public void onResponse(Call<BusinessItem> call, Response<BusinessItem> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BusinessItem> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<BusinessItem> submitBusiness(String userId, String businessId, String businessImage, String name, String email, String phone, String website, String address,  String insta, String youtube, String facebook, String twitter, String tagline, String type, String businesscategories1) {

        MutableLiveData<BusinessItem> data = new MutableLiveData<>();


        MultipartBody.Part body = null;
        RequestBody businessid = null;
        RequestBody imageuri =null;

        if (businessImage != null) {

            File file = new File(businessImage);
            RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
            body = MultipartBody.Part.createFormData(Constant.BUSSINESS_IMAGE, file.getName(), requestFile);
            imageuri = RequestBody.create(MediaType.parse(Constant.multipart), businessImage);

        }

        if (businessId !=null && !businessId.isEmpty()){

            businessid = RequestBody.create(MediaType.parse(Constant.multipart), businessId);

        }


        Log.d("usersss", "id: "+userId+
                "name"+name+
                "email:"+email+
                "website:"+website+
                "address:"+address+
                "type:"+type+
                "bus id:"+businesscategories1);


        RequestBody userid = RequestBody.create(MediaType.parse(Constant.multipart), userId);
        RequestBody businessname = RequestBody.create(MediaType.parse(Constant.multipart), name);
        RequestBody businessemail = RequestBody.create(MediaType.parse(Constant.multipart), email);
        RequestBody businessphone = RequestBody.create(MediaType.parse(Constant.multipart), phone);
        RequestBody businesswebsite = RequestBody.create(MediaType.parse(Constant.multipart), website);
        RequestBody businessaddress = RequestBody.create(MediaType.parse(Constant.multipart), address);
        RequestBody businessinsta = RequestBody.create(MediaType.parse(Constant.multipart), insta);
        RequestBody businessyoutube = RequestBody.create(MediaType.parse(Constant.multipart), youtube);
        RequestBody businessfacebook = RequestBody.create(MediaType.parse(Constant.multipart), facebook);
        RequestBody businesstwitter = RequestBody.create(MediaType.parse(Constant.multipart), twitter);
        RequestBody businesstype = RequestBody.create(MediaType.parse(Constant.multipart), type);
        RequestBody businesscategories = RequestBody.create(MediaType.parse(Constant.multipart), businesscategories1);

        RequestBody businessTagliness = RequestBody.create(MediaType.parse(Constant.multipart), tagline);


        ApiClient.getApiDataService().submitBusiness(userid, businessid, imageuri, body, businessname, businessemail, businessphone, businesswebsite, businessaddress, businessinsta, businessyoutube, businessfacebook, businesstwitter,
                        businessTagliness, businesstype, businesscategories).
                enqueue(new Callback<BusinessItem>() {
                    @Override
                    public void onResponse(Call<BusinessItem> call, Response<BusinessItem> response) {

                        Log.i("RESPONSE", "RESPONSE-->" + new Gson().toJson(response.body()));

                        data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<BusinessItem> call, Throwable t) {

                        t.printStackTrace();

                        data.setValue(null);

                    }
                });

        return data;
    }




    public LiveData<PoliticalCreateResponse> submitPolitical(String userId, String profileImage, String pSignatureImg, String pPartyImg,
                                                  String pLeaderImg1, String pLeaderImg2, String pLeaderImg3, String pLeaderImg4,  String pLeaderImg5, String pLeaderImg6,
                                                  String pName, String pPhone, String pEmail,
                                                  String pFacebookUsername, String pInstagramUsername,String pTwitterUsername,
                                                  String pDesignation1, String pDesignation2) {

        MutableLiveData<PoliticalCreateResponse> data = new MutableLiveData<>();

        MultipartBody.Part body = null;
        RequestBody signatureImageUri =null;

        MultipartBody.Part profileBody = null;
        RequestBody profileImageUri =null;

        MultipartBody.Part partyBody = null;
        RequestBody pPartyImgUri = null;

        MultipartBody.Part leader1Body = null;
        RequestBody leaderImg1Uri =null;

        MultipartBody.Part leader2Body = null;
        RequestBody leaderImg2Uri = null;

        MultipartBody.Part leader3Body = null;
        RequestBody leaderImg3Uri =null;

        MultipartBody.Part leader4Body = null;
        RequestBody leaderImg4Uri = null;

        MultipartBody.Part leader5Body = null;
        RequestBody leaderImg5Uri =null;

        MultipartBody.Part leader6Body = null;
        RequestBody leaderImg6Uri = null;

        if(!profileImage.isEmpty()){
            if (profileImage != null) {
                File file = new File(profileImage);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                profileBody = MultipartBody.Part.createFormData("pProfileImg", file.getName(), requestFile);
                profileImageUri = RequestBody.create(MediaType.parse(Constant.multipart), profileImage);
                Log.i("getJSONDataCreate", "RESPONSE profileBody 4 -->" + String.valueOf(profileBody));
            }
        }

        if(!pPartyImg.isEmpty()){
            if (pPartyImg != null) {
                File file = new File(pPartyImg);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                partyBody = MultipartBody.Part.createFormData("pPartyImg", file.getName(), requestFile);
                pPartyImgUri = RequestBody.create(MediaType.parse(Constant.multipart), pPartyImg);
                Log.i("getJSONDataCreate", "RESPONSE partyBody 4 -->" + String.valueOf(partyBody));
            }
        }
        if(!pLeaderImg1.isEmpty()){
            if (pLeaderImg1 != null) {
                File file = new File(pLeaderImg1);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader1Body = MultipartBody.Part.createFormData("pLeaderImg1", file.getName(), requestFile);
                leaderImg1Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg1);
            }
        }
        if(!pLeaderImg2.isEmpty()){
            if (pLeaderImg2 != null) {
                File file = new File(pLeaderImg2);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader2Body = MultipartBody.Part.createFormData("pLeaderImg2", file.getName(), requestFile);
                leaderImg2Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg2);
            }
        }

        if(!pLeaderImg3.isEmpty()){
            if (pLeaderImg3 != null) {
                File file = new File(pLeaderImg3);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader3Body = MultipartBody.Part.createFormData("pLeaderImg3", file.getName(), requestFile);
                leaderImg3Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg3);
            }
        }

        if(!pLeaderImg4.isEmpty()){
            if (pLeaderImg4 != null) {
                File file = new File(pLeaderImg4);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader4Body = MultipartBody.Part.createFormData("pLeaderImg4", file.getName(), requestFile);
                leaderImg4Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg4);
            }
        }

        if(!pLeaderImg5.isEmpty()){
            if (pLeaderImg5 != null) {
                File file = new File(pLeaderImg5);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader5Body = MultipartBody.Part.createFormData("pLeaderImg5", file.getName(), requestFile);
                leaderImg5Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg5);
            }
        }

        if(!pLeaderImg6.isEmpty()){
            if (pLeaderImg6 != null) {
                File file = new File(pLeaderImg6);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader6Body = MultipartBody.Part.createFormData("pLeaderImg6", file.getName(), requestFile);
                leaderImg6Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg6);
            }
        }






        Log.i("getJSONDataCreate", "profileImageUri: "+profileImageUri+
                " , profileBody : "+profileBody);


        RequestBody userid = RequestBody.create(MediaType.parse(Constant.multipart), userId);
        RequestBody politicalName = RequestBody.create(MediaType.parse(Constant.multipart), pName);
        RequestBody politicalPhone = RequestBody.create(MediaType.parse(Constant.multipart), pPhone);
        RequestBody politicalEmail = RequestBody.create(MediaType.parse(Constant.multipart), pEmail);
        RequestBody politicalInstagram = RequestBody.create(MediaType.parse(Constant.multipart), pInstagramUsername);
        RequestBody politicalFacebook = RequestBody.create(MediaType.parse(Constant.multipart), pFacebookUsername);
        RequestBody politicalTwitter = RequestBody.create(MediaType.parse(Constant.multipart), pTwitterUsername);
        RequestBody politicalDesignation1 = RequestBody.create(MediaType.parse(Constant.multipart), pDesignation1);
        RequestBody politicalDesignation2 = RequestBody.create(MediaType.parse(Constant.multipart), pDesignation2);

        Log.i("getJSONDataCreate", "RESPONSE partyBody -->" + String.valueOf(partyBody));

        Log.i("getJSONDataCreate", "RESPONSE profileBody -->" + String.valueOf(profileBody));
        ApiClientSecond.getApiDataService().submitPoliticak(userid,
                         profileBody,

                        body,
                         partyBody,
                        leader1Body,   leader2Body,
                        leader3Body,   leader4Body,
                         leader5Body,   leader6Body,
                        politicalName, politicalPhone, politicalEmail, politicalInstagram, politicalFacebook, politicalTwitter,
                        politicalDesignation1, politicalDesignation2).
                enqueue(new Callback<PoliticalCreateResponse>() {
                    @Override
                    public void onResponse(Call<PoliticalCreateResponse> call, Response<PoliticalCreateResponse> response) {
                        Log.i("getJSONDataCreate", "first RESPONSE CODE-->" + String.valueOf(response.code()));
                        Log.i("getJSONDataCreate", "first RESPONSE-->" + new Gson().toJson(response.body()));

                        data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<PoliticalCreateResponse> call, Throwable t) {
                        Log.i("getJSONDataCreate", "first RESPONSE CODE-->" + String.valueOf(t.getMessage()));
                        t.printStackTrace();

                        data.setValue(null);

                    }
                });

        return data;
    }





    public LiveData<PoliticalCreateResponse> updatePolitical(String profileId,String userId, String profileImage, String pSignatureImg, String pPartyImg,
                                                             String pLeaderImg1, String pLeaderImg2, String pLeaderImg3, String pLeaderImg4,  String pLeaderImg5, String pLeaderImg6,
                                                             String pName, String pPhone, String pEmail,
                                                             String pFacebookUsername, String pInstagramUsername,String pTwitterUsername,
                                                             String pDesignation1, String pDesignation2) {

        MutableLiveData<PoliticalCreateResponse> data = new MutableLiveData<>();

        MultipartBody.Part body = null;
        RequestBody signatureImageUri =null;

        MultipartBody.Part profileBody = null;
        RequestBody profileImageUri =null;

        MultipartBody.Part partyBody = null;
        RequestBody pPartyImgUri = null;

        MultipartBody.Part leader1Body = null;
        RequestBody leaderImg1Uri =null;

        MultipartBody.Part leader2Body = null;
        RequestBody leaderImg2Uri = null;

        MultipartBody.Part leader3Body = null;
        RequestBody leaderImg3Uri =null;

        MultipartBody.Part leader4Body = null;
        RequestBody leaderImg4Uri = null;

        MultipartBody.Part leader5Body = null;
        RequestBody leaderImg5Uri =null;

        MultipartBody.Part leader6Body = null;
        RequestBody leaderImg6Uri = null;


        if(!profileImage.isEmpty()){
            if (profileImage != null) {
                File file = new File(profileImage);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                profileBody = MultipartBody.Part.createFormData("pProfileImg", file.getName(), requestFile);
                profileImageUri = RequestBody.create(MediaType.parse(Constant.multipart), profileImage);
                Log.i("getJSONDataUpdate", "RESPONSE profileBody 4 -->" + String.valueOf(profileBody));
            }
        }

        if(!pPartyImg.isEmpty()){
            if (pPartyImg != null) {
                File file = new File(pPartyImg);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                partyBody = MultipartBody.Part.createFormData("pPartyImg", file.getName(), requestFile);
                pPartyImgUri = RequestBody.create(MediaType.parse(Constant.multipart), pPartyImg);
                Log.i("getJSONDataUpdate", "RESPONSE partyBody 4 -->" + String.valueOf(partyBody));
            }
        }
        if(!pLeaderImg1.isEmpty()){
            if (pLeaderImg1 != null) {
                File file = new File(pLeaderImg1);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader1Body = MultipartBody.Part.createFormData("pLeaderImg1", file.getName(), requestFile);
                leaderImg1Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg1);
            }
        }
        if(!pLeaderImg2.isEmpty()){
            if (pLeaderImg2 != null) {
                File file = new File(pLeaderImg2);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader2Body = MultipartBody.Part.createFormData("pLeaderImg2", file.getName(), requestFile);
                leaderImg2Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg2);
            }
        }

        if(!pLeaderImg3.isEmpty()){
            if (pLeaderImg3 != null) {
                File file = new File(pLeaderImg3);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader3Body = MultipartBody.Part.createFormData("pLeaderImg3", file.getName(), requestFile);
                leaderImg3Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg3);
            }
        }

        if(!pLeaderImg4.isEmpty()){
            if (pLeaderImg4 != null) {
                File file = new File(pLeaderImg4);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader4Body = MultipartBody.Part.createFormData("pLeaderImg4", file.getName(), requestFile);
                leaderImg4Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg4);
            }
        }

        if(!pLeaderImg5.isEmpty()){
            if (pLeaderImg5 != null) {
                File file = new File(pLeaderImg5);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader5Body = MultipartBody.Part.createFormData("pLeaderImg5", file.getName(), requestFile);
                leaderImg5Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg5);
            }
        }

        if(!pLeaderImg6.isEmpty()){
            if (pLeaderImg6 != null) {
                File file = new File(pLeaderImg6);
                RequestBody requestFile = RequestBody.create(MediaType.parse(Constant.multipart), file);
                leader6Body = MultipartBody.Part.createFormData("pLeaderImg6", file.getName(), requestFile);
                leaderImg6Uri = RequestBody.create(MediaType.parse(Constant.multipart), pLeaderImg6);
            }
        }






        Log.i("getJSONData", "profileImageUri: "+profileImageUri+
                " , profileBody : "+profileBody);


        RequestBody userid = RequestBody.create(MediaType.parse(Constant.multipart), userId);

        RequestBody politicalName = null;
        RequestBody politicalPhone = null;
        RequestBody politicalEmail = null;
        RequestBody politicalInstagram = null;
        RequestBody politicalFacebook = null;
        RequestBody politicalTwitter = null;
        RequestBody politicalDesignation1 = null;
        RequestBody politicalDesignation2 = null;
        if(pName !=null){
            politicalName = RequestBody.create(MediaType.parse(Constant.multipart), pName);
        }

        if(pPhone !=null){
            politicalPhone = RequestBody.create(MediaType.parse(Constant.multipart), pPhone);
        }

        if(pEmail !=null){
            politicalEmail = RequestBody.create(MediaType.parse(Constant.multipart), pEmail);
        }

        if(pInstagramUsername !=null){
            politicalInstagram = RequestBody.create(MediaType.parse(Constant.multipart), pInstagramUsername);
        }

        if(pFacebookUsername !=null){
            politicalFacebook = RequestBody.create(MediaType.parse(Constant.multipart), pFacebookUsername);
        }

        if(pTwitterUsername !=null){
            politicalTwitter = RequestBody.create(MediaType.parse(Constant.multipart), pTwitterUsername);
        }

        if(pDesignation1 !=null){
            politicalDesignation1 = RequestBody.create(MediaType.parse(Constant.multipart), pDesignation1);
        }

        if(pDesignation2 !=null){
            politicalDesignation2 = RequestBody.create(MediaType.parse(Constant.multipart), pDesignation2);
        }

        Log.i("getJSONDataUpdate", "RESPONSE second profileIdOther -->" + String.valueOf(profileId));
        ApiClientSecond.getApiDataService().updatePolitical(profileId,userid,
                        profileBody,

                        body,
                        partyBody,
                        leader1Body,   leader2Body,
                        leader3Body,   leader4Body,
                        leader5Body,   leader6Body,
                        politicalName, politicalPhone, politicalEmail, politicalInstagram, politicalFacebook, politicalTwitter,
                        politicalDesignation1, politicalDesignation2).
                enqueue(new Callback<PoliticalCreateResponse>() {
                    @Override
                    public void onResponse(Call<PoliticalCreateResponse> call, Response<PoliticalCreateResponse> response) {
                        Log.i("getJSONDataUpdate", "first RESPONSE CODE-->" + String.valueOf(response.code()));
                        Log.i("getJSONDataUpdate", "first RESPONSE-->" + new Gson().toJson(response.body()));

                        data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<PoliticalCreateResponse> call, Throwable t) {
                        Log.i("getJSONDataUpdate", "first RESPONSE Message-->" + String.valueOf(t.getMessage()));
                        t.printStackTrace();

                        data.setValue(null);

                    }
                });

        return data;
    }


    public LiveData<GetPoliticalCreateResponse> getPolitical(String profileId) {

        MutableLiveData<GetPoliticalCreateResponse> data = new MutableLiveData<>();


        ApiClientSecond.getApiDataService().getPolitical(profileId).
                enqueue(new Callback<GetPoliticalCreateResponse>() {
                    @Override
                    public void onResponse(Call<GetPoliticalCreateResponse> call, Response<GetPoliticalCreateResponse> response) {
                        Log.i("getJSONData", "first RESPONSE CODE-->" + String.valueOf(response.code()));
                        Log.i("getJSONData", "first RESPONSE-->" + new Gson().toJson(response.body()));

                        data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<GetPoliticalCreateResponse> call, Throwable t) {

                        t.printStackTrace();

                        data.setValue(null);

                    }
                });

        return data;
    }



    public LiveData<WhatsappOtpResponse> createWhatsappOtp(String number) {


        MutableLiveData<WhatsappOtpResponse> data = new MutableLiveData<>();

        apiService.createWhatsappOtp(number).enqueue(new Callback<WhatsappOtpResponse>() {
            @Override
            public void onResponse(Call<WhatsappOtpResponse> call, Response<WhatsappOtpResponse> response) {

                data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<WhatsappOtpResponse> call, Throwable t) {

                data.setValue(null);
            }
        });

        return data;
    }

}
