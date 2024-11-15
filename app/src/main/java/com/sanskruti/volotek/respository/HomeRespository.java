package com.sanskruti.volotek.respository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.sanskruti.volotek.api.ApiClient;
import com.sanskruti.volotek.api.ApiClientSecond;
import com.sanskruti.volotek.api.ApiService;
import com.sanskruti.volotek.api.ApiStatus;
import com.sanskruti.volotek.custom.animated_video.model.ModelAudio;
import com.sanskruti.volotek.model.AppInfos;
import com.sanskruti.volotek.model.CategoryItem;
import com.sanskruti.volotek.model.CheckReferralResponse;
import com.sanskruti.volotek.model.CouponItem;
import com.sanskruti.volotek.model.DeletePoliticalProfileBaseModel;
import com.sanskruti.volotek.model.DigitalCardModel;
import com.sanskruti.volotek.model.FeatureItem;
import com.sanskruti.volotek.model.FestivalItem;
import com.sanskruti.volotek.model.FrameResponse;
import com.sanskruti.volotek.model.GreetingPage;
import com.sanskruti.volotek.model.HomeItem;
import com.sanskruti.volotek.model.LanguageItem;
import com.sanskruti.volotek.model.Logout;
import com.sanskruti.volotek.model.PoliticalProfileBaseModel;
import com.sanskruti.volotek.model.PostItem;
import com.sanskruti.volotek.model.ReferralResponse;
import com.sanskruti.volotek.model.SearchData;
import com.sanskruti.volotek.model.ServiceItem;
import com.sanskruti.volotek.model.SliderItem;
import com.sanskruti.volotek.model.SubsPlanItem;
import com.sanskruti.volotek.model.VideoItem;
import com.sanskruti.volotek.model.WatermarkResponse;
import com.sanskruti.volotek.model.politicalProfileModel;
import com.sanskruti.volotek.model.video.LoginStatus;
import com.sanskruti.volotek.model.video.TokenResponse;
import com.sanskruti.volotek.utils.Constant;


import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRespository {


    private ApiService apiService;

    public HomeRespository() {
        apiService = ApiClient.getApiDataService();
    }

    public LiveData<HomeItem> getHomeData() {
        MutableLiveData<HomeItem> data = new MutableLiveData<>();
        apiService.getHomeDatas().enqueue(new Callback<HomeItem>() {
            @Override
            public void onResponse(Call<HomeItem> call, Response<HomeItem> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeItem> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<CouponItem> validateCoupon(String userId, String couponCode) {
        MutableLiveData<CouponItem> data = new MutableLiveData<>();
        apiService.validateCoupon(userId, couponCode).enqueue(new Callback<CouponItem>() {
            @Override
            public void onResponse(Call<CouponItem> call, Response<CouponItem> response) {
                Log.d("response___t", "" + response.body());
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CouponItem> call, Throwable t) {
                data.setValue(null);
                Log.d("response___t", "E-> " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<List<FeatureItem>> getGreetingData(String categoryid, int pagecount) {
        MutableLiveData<List<FeatureItem>> data = new MutableLiveData<>();
        apiService.getGreetingData(categoryid, pagecount).enqueue(new Callback<List<FeatureItem>>() {
            @Override
            public void onResponse(Call<List<FeatureItem>> call, Response<List<FeatureItem>> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<FeatureItem>> call, Throwable t) {
                Log.d("onResponse___", " E-> " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<DigitalCardModel>> getBusinessCards() {
        MutableLiveData<List<DigitalCardModel>> data = new MutableLiveData<>();
        apiService.getDigitalBusinessCardList().enqueue(new Callback<List<DigitalCardModel>>() {
            @Override
            public void onResponse(Call<List<DigitalCardModel>> call, Response<List<DigitalCardModel>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<DigitalCardModel>> call, Throwable t) {
                Log.d("onResponse___", " E-> " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<DigitalCardModel>> getMybusinesslist(String userId) {
        MutableLiveData<List<DigitalCardModel>> data = new MutableLiveData<>();
        apiService.getMybusinesslist(userId).enqueue(new Callback<List<DigitalCardModel>>() {
            @Override
            public void onResponse(Call<List<DigitalCardModel>> call, Response<List<DigitalCardModel>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<DigitalCardModel>> call, Throwable t) {
                Log.d("onResponse___", " E-> " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<ApiStatus> addBusinessCard(String userId, Integer cardId) {
        MutableLiveData<ApiStatus> data = new MutableLiveData<>();
        apiService.addBusinessCard(userId, cardId).enqueue(new Callback<ApiStatus>() {
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

    public LiveData<List<SubsPlanItem>> getSubscriptionsPlanList() {
        MutableLiveData<List<SubsPlanItem>> data = new MutableLiveData<>();
        apiService.getSubscriptionsPlanList().enqueue(new Callback<List<SubsPlanItem>>() {
            @Override
            public void onResponse(Call<List<SubsPlanItem>> call, Response<List<SubsPlanItem>> response) {
                Log.i("getPlanDtataLucky","body = "+String.valueOf(response.body()));
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<SubsPlanItem>> call, Throwable t) {
                Log.i("getPlanDtataLucky","Message = "+String.valueOf(t.getMessage()));
                Log.d("onResponse___", " E-> " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<List<ServiceItem>> getAllService() {
        MutableLiveData<List<ServiceItem>> data = new MutableLiveData<>();
        apiService.getAllService().enqueue(new Callback<List<ServiceItem>>() {
            @Override
            public void onResponse(Call<List<ServiceItem>> call, Response<List<ServiceItem>> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ServiceItem>> call, Throwable t) {
                Log.d("onResponse___", " E-> " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<CategoryItem>> getCategories(String type) {
        MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
        apiService.getCategories(type).enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<List<PostItem>> getAllPostsByCategory(int page, String type,String postType, String categoryid, String language, boolean isVideo) {
        MutableLiveData<List<PostItem>> data = new MutableLiveData<>();
        apiService.getAllPostsByCategory(page, type,postType, categoryid, language, isVideo).enqueue(new Callback<List<PostItem>>() {
            @Override
            public void onResponse(Call<List<PostItem>> call, Response<List<PostItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostItem>> call, Throwable t) {
                t.printStackTrace();
                Log.d("errors","rrrpr"+t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<CategoryItem>> getBusinessSubCategory(String type, String subCatid) {
        MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
        apiService.getBusinessSubCategory(type, subCatid).enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<HomeItem> getSubBusinessCatHome(String subCategoryId) {

        MutableLiveData<HomeItem> data = new MutableLiveData<>();

        apiService.getSubBusinessCatHome(subCategoryId).enqueue(new Callback<HomeItem>() {
            @Override
            public void onResponse(Call<HomeItem> call, Response<HomeItem> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<HomeItem> call, Throwable t) {

                data.setValue(null);
            }
        });

        return data;

    }


    public LiveData<List<SliderItem>> getHomeBanners() {

        MutableLiveData<List<SliderItem>> data = new MutableLiveData<>();

        apiService.getAllBannerss().enqueue(new Callback<List<SliderItem>>() {
            @Override
            public void onResponse(Call<List<SliderItem>> call, Response<List<SliderItem>> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<SliderItem>> call, Throwable t) {

                data.setValue(null);
            }
        });

        return data;

    }


    public LiveData<List<FestivalItem>> getFestivals(String type, boolean video) {

        MutableLiveData<List<FestivalItem>> data = new MutableLiveData<>();

        apiService.getFestivals(type,video).enqueue(new Callback<List<FestivalItem>>() {
            @Override
            public void onResponse(Call<List<FestivalItem>> call, Response<List<FestivalItem>> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<FestivalItem>> call, Throwable t) {

                data.setValue(null);
            }
        });

        return data;

    }

    public LiveData<List<PostItem>> getDailyPosts(int page, String catid, String language) {
        MutableLiveData<List<PostItem>> data = new MutableLiveData<>();
        apiService.getDailyPostData(page, catid, language).enqueue(new Callback<List<PostItem>>() {
            @Override
            public void onResponse(Call<List<PostItem>> call, Response<List<PostItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<PoliticalProfileBaseModel> getAllPoliticalProfile(String user_id) {
        MutableLiveData<PoliticalProfileBaseModel> data = new MutableLiveData<>();
        apiService.getAllPoliticalData(user_id).enqueue(new Callback<PoliticalProfileBaseModel>() {
            @Override
            public void onResponse(Call<PoliticalProfileBaseModel> call, Response<PoliticalProfileBaseModel> response) {
                Log.i("checkframdata","data = "+new Gson().toJson(response.body()));
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PoliticalProfileBaseModel> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }



    public LiveData<DeletePoliticalProfileBaseModel> deletePoliticalProfile(String profile_id) {
        MutableLiveData<DeletePoliticalProfileBaseModel> data = new MutableLiveData<>();
        apiService.deletePoliticalProfile(profile_id).enqueue(new Callback<DeletePoliticalProfileBaseModel>() {
            @Override
            public void onResponse(Call<DeletePoliticalProfileBaseModel> call, Response<DeletePoliticalProfileBaseModel> response) {
                Log.i("checkframdata","data = "+new Gson().toJson(response.body()));
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DeletePoliticalProfileBaseModel> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public LiveData<FrameResponse> getCustomFrame(String ratio, String type, String user_id) {
        MutableLiveData<FrameResponse> data = new MutableLiveData<>();
        apiService.getCustomFrames(user_id,type, ratio).enqueue(new Callback<FrameResponse>() {
            @Override
            public void onResponse(Call<FrameResponse> call, Response<FrameResponse> response) {
                Log.i("checkframdata","data = "+new Gson().toJson(response.body()));
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<FrameResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<List<ModelAudio>> getAllMusic(int page, int catId,String langId) {
        MutableLiveData<List<ModelAudio>> data = new MutableLiveData<>();
        apiService.getAllMusic(page, catId, langId).enqueue(new Callback<List<ModelAudio>>() {
            @Override
            public void onResponse(Call<List<ModelAudio>> call, Response<List<ModelAudio>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ModelAudio>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


    public LiveData<List<LanguageItem>> getLanguagess() {


        MutableLiveData<List<LanguageItem>> data = new MutableLiveData<>();

        apiService.getLanguagess().enqueue(new Callback<List<LanguageItem>>() {
            @Override
            public void onResponse(Call<List<LanguageItem>> call, Response<List<LanguageItem>> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<LanguageItem>> call, Throwable t) {

                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<AppInfos> getAppInfo() {

        MutableLiveData<AppInfos> data = new MutableLiveData<>();

        apiService.getAppInfo().enqueue(new Callback<AppInfos>() {
            @Override
            public void onResponse(Call<AppInfos> call, Response<AppInfos> response) {

                data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<AppInfos> call, Throwable t) {

                Log.d("errrorrr",""+t.getMessage());

                t.printStackTrace();
                data.setValue(null);

            }
        });
        return data;
    }

    public LiveData<WatermarkResponse> getWatermark(){
        MutableLiveData<WatermarkResponse> data = new MutableLiveData<>();

        apiService.getWatermark().enqueue(new Callback<WatermarkResponse>() {
            @Override
            public void onResponse(Call<WatermarkResponse> call, Response<WatermarkResponse> response) {
                data.setValue(response.body());
            }
            @Override
            public void onFailure(Call<WatermarkResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<TokenResponse> updateToken(String userId, String deviceType, String deviceToken){
        MutableLiveData<TokenResponse> data = new MutableLiveData<>();

        RequestBody userid = RequestBody.create(MediaType.parse(Constant.multipart), userId);
        RequestBody deviceT = RequestBody.create(MediaType.parse(Constant.multipart), deviceType);
        RequestBody deviceTok = RequestBody.create(MediaType.parse(Constant.multipart), deviceToken);
        apiService.updateToken(userid,deviceT,deviceTok).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<LoginStatus> getLoginStatus(String userId){
        MutableLiveData<LoginStatus> data = new MutableLiveData<>();

        RequestBody userid = RequestBody.create(MediaType.parse(Constant.multipart), userId);
        apiService.getLoginStatus(userid).enqueue(new Callback<LoginStatus>() {
            @Override
            public void onResponse(Call<LoginStatus> call, Response<LoginStatus> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginStatus> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<LoginStatus> updateLoginStatus(String userId, String isLogin){
        MutableLiveData<LoginStatus> data = new MutableLiveData<>();

        RequestBody userid = RequestBody.create(MediaType.parse(Constant.multipart), userId);
        RequestBody islogin = RequestBody.create(MediaType.parse(Constant.multipart), isLogin);
        apiService.updateLoginStatus(userid,islogin).enqueue(new Callback<LoginStatus>() {
            @Override
            public void onResponse(Call<LoginStatus> call, Response<LoginStatus> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginStatus> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<SearchData> getSearchData(){
        MutableLiveData<SearchData> data = new MutableLiveData<>();
        apiService.getSearchData().enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<CategoryItem>> getFeaturedGreeting() {
        MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
        apiService.getFeaturedGreeting().enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<CategoryItem>> getTrending() {
        MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
        apiService.getTrending().enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<ReferralResponse> updateReferralCode(String userMobile, String referralCode){
        MutableLiveData<ReferralResponse> data = new MutableLiveData<>();

        RequestBody userMob = RequestBody.create(MediaType.parse(Constant.multipart), userMobile);
        RequestBody referralCod = RequestBody.create(MediaType.parse(Constant.multipart), referralCode);
        apiService.updateReferralCode(userMob,referralCod).enqueue(new Callback<ReferralResponse>() {
            @Override
            public void onResponse(Call<ReferralResponse> call, Response<ReferralResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ReferralResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<CheckReferralResponse> checkReferralCode(String userId){
        MutableLiveData<CheckReferralResponse> data = new MutableLiveData<>();

        RequestBody userID = RequestBody.create(MediaType.parse(Constant.multipart), userId);
        apiService.checkReferralCode(userID).enqueue(new Callback<CheckReferralResponse>() {
            @Override
            public void onResponse(Call<CheckReferralResponse> call, Response<CheckReferralResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CheckReferralResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Logout> logout(){
        MutableLiveData<Logout> data = new MutableLiveData<>();
        apiService.logout().enqueue(new Callback<Logout>() {
            @Override
            public void onResponse(Call<Logout> call, Response<Logout> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Logout> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<CategoryItem>> getMainCategories(){
        MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
        apiService.getMainCategories().enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<CategoryItem>> getSubCategories(String categoryId){
        MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
        RequestBody category = RequestBody.create(MediaType.parse(Constant.multipart), categoryId);
        apiService.getSubCategories(category).enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public LiveData<List<PostItem>> getSubCategoryPosts(String categoryId,String subCategoryId){
        MutableLiveData<List<PostItem>> data = new MutableLiveData<>();
        RequestBody category = RequestBody.create(MediaType.parse(Constant.multipart), categoryId);
        RequestBody subCategory = RequestBody.create(MediaType.parse(Constant.multipart), subCategoryId);
        apiService.getSubCategoryPosts(category,subCategory).enqueue(new Callback<List<PostItem>>() {
            @Override
            public void onResponse(Call<List<PostItem>> call, Response<List<PostItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<CategoryItem>> getGreetingMainCategories(){
        MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
        apiService.getGreetingMainCategories().enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public LiveData<List<CategoryItem>> getGreetingSubCategories(String categoryId){
        MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
        RequestBody category = RequestBody.create(MediaType.parse(Constant.multipart), categoryId);
        apiService.getGreetingSubCategories(category).enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public LiveData<List<PostItem>> getGreetingSubCategoryPosts(String categoryId,String subCategoryId){
        MutableLiveData<List<PostItem>> data = new MutableLiveData<>();
        RequestBody category = RequestBody.create(MediaType.parse(Constant.multipart), categoryId);
        RequestBody subCategory = RequestBody.create(MediaType.parse(Constant.multipart), subCategoryId);
        apiService.getGreetingSubCategoryPosts(category,subCategory).enqueue(new Callback<List<PostItem>>() {
            @Override
            public void onResponse(Call<List<PostItem>> call, Response<List<PostItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<GreetingPage>> getGreetingPageData(){
        MutableLiveData<List<GreetingPage>> data = new MutableLiveData<>();
        apiService.getGreetingPageData().enqueue(new Callback<List<GreetingPage>>() {
            @Override
            public void onResponse(Call<List<GreetingPage>> call, Response<List<GreetingPage>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GreetingPage>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public LiveData<List<CategoryItem>> getVideoMainCategories(){
        MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
        apiService.getVideoMainCategories().enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public LiveData<List<CategoryItem>> getVideoSubCategories(String categoryId){
        MutableLiveData<List<CategoryItem>> data = new MutableLiveData<>();
        RequestBody category = RequestBody.create(MediaType.parse(Constant.multipart), categoryId);
        apiService.getVideoSubCategories(category).enqueue(new Callback<List<CategoryItem>>() {
            @Override
            public void onResponse(Call<List<CategoryItem>> call, Response<List<CategoryItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoryItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public LiveData<List<VideoItem>> getVideoSubCategoriesFrames(String categoryId,String subCategoryId){
        MutableLiveData<List<VideoItem>> data = new MutableLiveData<>();
        RequestBody category = RequestBody.create(MediaType.parse(Constant.multipart), categoryId);
        RequestBody subCategory = RequestBody.create(MediaType.parse(Constant.multipart), subCategoryId);
        apiService.getVideoSubCategoriesFrames(category,subCategory).enqueue(new Callback<List<VideoItem>>() {
            @Override
            public void onResponse(Call<List<VideoItem>> call, Response<List<VideoItem>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<VideoItem>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
    public LiveData<List<GreetingPage>> getVideoPageData(){
        MutableLiveData<List<GreetingPage>> data = new MutableLiveData<>();
        apiService.getVideoPageData().enqueue(new Callback<List<GreetingPage>>() {
            @Override
            public void onResponse(Call<List<GreetingPage>> call, Response<List<GreetingPage>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GreetingPage>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
