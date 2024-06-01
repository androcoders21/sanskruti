package com.sanskruti.volotek.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sanskruti.volotek.api.ApiStatus;
import com.sanskruti.volotek.custom.animated_video.model.ModelAudio;
import com.sanskruti.volotek.model.AppInfos;
import com.sanskruti.volotek.model.CategoryItem;
import com.sanskruti.volotek.model.CouponItem;
import com.sanskruti.volotek.model.DeletePoliticalProfileBaseModel;
import com.sanskruti.volotek.model.DigitalCardModel;
import com.sanskruti.volotek.model.FeatureItem;
import com.sanskruti.volotek.model.FestivalItem;
import com.sanskruti.volotek.model.FrameResponse;
import com.sanskruti.volotek.model.HomeItem;
import com.sanskruti.volotek.model.LanguageItem;
import com.sanskruti.volotek.model.PoliticalProfileBaseModel;
import com.sanskruti.volotek.model.PostItem;
import com.sanskruti.volotek.model.ServiceItem;
import com.sanskruti.volotek.model.SliderItem;
import com.sanskruti.volotek.model.SubsPlanItem;
import com.sanskruti.volotek.model.WatermarkResponse;
import com.sanskruti.volotek.model.politicalProfileModel;
import com.sanskruti.volotek.model.video.LoginStatus;
import com.sanskruti.volotek.model.video.TokenResponse;
import com.sanskruti.volotek.respository.HomeRespository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private HomeRespository respository;

    public HomeViewModel() {
        respository = new HomeRespository();
    }

    public LiveData<HomeItem> getHomeData() {
        return respository.getHomeData();
    }

    public LiveData<CouponItem> validateCoupon(String userId, String promo) {
        return respository.validateCoupon(userId, promo);
    }

    public LiveData<List<FeatureItem>> getGreetingData(String categoryid, int pagecount) {
        return respository.getGreetingData(categoryid, pagecount);
    }


    public LiveData<List<SubsPlanItem>> getSubscriptionsPlanList() {
        return respository.getSubscriptionsPlanList();
    }
    public LiveData<List<ServiceItem>> getAllService() {
        return respository.getAllService();
    }

    public LiveData<List<PostItem>> getAllPostsByCategory(int page, String type,String postType, String categoryid, String language, boolean isVideo) {
        return respository.getAllPostsByCategory(page, type, postType, categoryid, language, isVideo);
    }

    public LiveData<List<CategoryItem>> getBusinessSubCategory(String type, String subCatid) {
        return respository.getBusinessSubCategory(type, subCatid);
    }


    public LiveData<List<PostItem>> getDailyPosts(int page, String catid, String language) {
        return respository.getDailyPosts(page, catid, language);
    }

    public LiveData<List<CategoryItem>> getCategories(String type) {

        return respository.getCategories(type);
    }


    public LiveData<PoliticalProfileBaseModel> getAllPoliticalProfile(String userId) {

        return respository.getAllPoliticalProfile(userId);
    }



    public LiveData<DeletePoliticalProfileBaseModel> deletePoliticalProfile(String userId) {

        return respository.deletePoliticalProfile(userId);
    }
    public LiveData<FrameResponse> getFrames(String ratio, String type, String userId) {

        return respository.getCustomFrame(userId,type, ratio);
    }


    public LiveData<List<ModelAudio>> getAllMusic(int page, Integer catId, String langId) {

        return respository.getAllMusic(page, catId, langId);
    }


    public LiveData<List<LanguageItem>> getLanguagess() {

        return respository.getLanguagess();
    }

    public LiveData<AppInfos> getAppInfo() {

        return respository.getAppInfo();
    }


    public LiveData<List<SliderItem>> getHomeBanners() {

        return respository.getHomeBanners();
    }


   public LiveData<List<FestivalItem>> getFestivals(String type, boolean video) {

        return respository.getFestivals(type,video);
    }

    public LiveData<HomeItem> getSubBusinessCatHome(String subCategoryId) {

        return respository.getSubBusinessCatHome(subCategoryId);
    }

    public LiveData<List<DigitalCardModel>> getBusinessCards() {
        return respository.getBusinessCards();

    }

    public LiveData<List<DigitalCardModel>> getMybusinesslist(String userId) {
        return respository.getMybusinesslist(userId);

    }

    public LiveData<ApiStatus> addBusinessCard(String userId, Integer cardId) {
        return respository.addBusinessCard(userId, cardId);

    }

    public LiveData<WatermarkResponse> getWatermark(){
        return respository.getWatermark();
    }

    public LiveData<TokenResponse> updateToken(String userId, String deviceType, String deviceToken){
        return respository.updateToken(userId,deviceType,deviceToken);
    }

    public LiveData<LoginStatus> getLoginStatus(String userId){
        return respository.getLoginStatus(userId);
    }

    public LiveData<LoginStatus> updateLoginStatus(String userId,String isLogin){
        return respository.updateLoginStatus(userId,isLogin);
    }
    public LiveData<List<FeatureItem>> getFeaturedGreeting() {
        return respository.getFeaturedGreeting();
    }
}
