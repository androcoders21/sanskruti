package com.sanskruti.volotek.api;

import com.sanskruti.volotek.custom.animated_video.model.ModelAudio;
import com.sanskruti.volotek.custom.poster.model.ThumbBG;
import com.sanskruti.volotek.custom.poster.model.ThumbnailInfo;
import com.sanskruti.volotek.custom.poster.model.ThumbnailThumbFull;
import com.sanskruti.volotek.custom.poster.model.ThumbnailWithList;
import com.sanskruti.volotek.model.AppInfos;
import com.sanskruti.volotek.model.BusinessItem;
import com.sanskruti.volotek.model.CategoryItem;
import com.sanskruti.volotek.model.CheckReferralResponse;
import com.sanskruti.volotek.model.CouponItem;
import com.sanskruti.volotek.model.DeletePoliticalProfileBaseModel;
import com.sanskruti.volotek.model.DigitalCardModel;
import com.sanskruti.volotek.model.FeatureItem;
import com.sanskruti.volotek.model.FestivalItem;
import com.sanskruti.volotek.model.FrameResponse;
import com.sanskruti.volotek.model.GetPoliticalCreateResponse;
import com.sanskruti.volotek.model.GreetingPage;
import com.sanskruti.volotek.model.HomeItem;
import com.sanskruti.volotek.model.LanguageItem;
import com.sanskruti.volotek.model.Logout;
import com.sanskruti.volotek.model.ModelCreated;
import com.sanskruti.volotek.model.PaytmResponse;
import com.sanskruti.volotek.model.PoliticalCreateResponse;
import com.sanskruti.volotek.model.PoliticalProfileBaseModel;
import com.sanskruti.volotek.model.PostItem;
import com.sanskruti.volotek.model.ReferralResponse;
import com.sanskruti.volotek.model.SearchData;
import com.sanskruti.volotek.model.ServiceItem;
import com.sanskruti.volotek.model.SliderItem;
import com.sanskruti.volotek.model.Sticker;
import com.sanskruti.volotek.model.StickerResponse;
import com.sanskruti.volotek.model.StripeResponse;
import com.sanskruti.volotek.model.SubsPlanItem;
import com.sanskruti.volotek.model.UserItem;
import com.sanskruti.volotek.model.VideoItem;
import com.sanskruti.volotek.model.WatermarkResponse;
import com.sanskruti.volotek.model.WhatsappOtpResponse;
import com.sanskruti.volotek.model.politicalProfileModel;
import com.sanskruti.volotek.model.video.DashboardTemplateResponseMain;
import com.sanskruti.volotek.model.video.LoginStatus;
import com.sanskruti.volotek.model.video.TemplateResponse;
import com.sanskruti.volotek.model.video.TokenResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //*** get user details****

    @GET("userDetail")
    Call<UserItem> getUserById(@Query("id") String userId);

    @GET("greetingStickerData")
    Call<StickerResponse> getStickerList();

    @GET("greetingStickerData")
    Call<StickerResponse> getStickerData(@Query("search_category") String category);

    @GET("getWatermarkSetting")
    Call<WatermarkResponse> getWatermark();


    @Multipart
    @POST("update-device-token")
    Call<TokenResponse>  updateToken(
            @Part("user_id") RequestBody user_id,
            @Part("device_type") RequestBody device_type,
            @Part("device_token") RequestBody device_token);

    @Multipart
    @POST("singleDeviceLoginStatus")
    Call<LoginStatus> getLoginStatus(
            @Part("user_mobile_email") RequestBody user_id
    );

    @POST("searchfilterAPI")
    Call<SearchData> getSearchData();

    @Multipart
    @POST("updateDeviceLoginStatus")
    Call<LoginStatus> updateLoginStatus(
            @Part("user_mobile_email") RequestBody user_id,
            @Part("is_logged_in") RequestBody is_logged_in
    );
    @POST("logout")
    Call<Logout> logout();

    @Multipart
    @POST("updateReferralCode")
    Call<ReferralResponse> updateReferralCode(
            @Part("user_mobile_email") RequestBody user_mobile,
            @Part("referral_code") RequestBody referral_code
    );

    @Multipart
    @POST("leader-status-check")
    Call<CheckReferralResponse> checkReferralCode(
            @Part("user_id") RequestBody user_id
    );

    //*** login with google****

    @GET("userLoginGoogle")
    Call<UserItem> userLoginGoogle(
            @Query("username") String userName,
            @Query("email") String userEmail,
            @Query("image") String profilePhotoUrl,
            @Query("mobile_no") String userMobile,
            @Query("type") String type,
            @Query("user_id") String userId);

    //*** update profile****
    @Multipart
    @POST("profileUpdate")
    Call<UserItem> profileUpdate(
            @Part("user_id") RequestBody userId,
            @Part("image") RequestBody image,
            @Part MultipartBody.Part file,
            @Part("name") RequestBody userName,
            @Part("email") RequestBody userEmail,
            @Part("mobile_no") RequestBody phone,
            @Part("designation") RequestBody designation);


    //*** get all Business list****
    @FormUrlEncoded
    @POST("manageBusiness")
    Call<List<BusinessItem>> getBusinessList(@Field("userId") String userId, @Field("type") String type);

    //*** get default Business****
    @FormUrlEncoded
    @POST("manageBusiness")
    Call<BusinessItem> getBusinessDefaultList(@Field("userId") String userId, @Field("type") String type);

    @FormUrlEncoded
    @POST("paytm-payment")
    Call<PaytmResponse> createPaytmPayment(@Field("order_amount") String order_amount,
                                           @Field("order_id") String order_id,
                                           @Field("user_id") String user_id);

    //*** Add Business****
    @Multipart
    @POST("manageBusiness")
    Call<BusinessItem> submitBusiness(
            @Part("userId") RequestBody userId,
            @Part("business_id") RequestBody business_id,
            @Part("bussinessImage") RequestBody logo,
            @Part MultipartBody.Part file,
            @Part("business_name") RequestBody name,
            @Part("business_email") RequestBody email,
            @Part("business_number") RequestBody phone,
            @Part("business_website") RequestBody website,
            @Part("business_address") RequestBody address,
            @Part("business_instagram") RequestBody instagram,
            @Part("business_youtube") RequestBody youtube,
            @Part("business_facebook") RequestBody facebook,
            @Part("business_twitter") RequestBody twitter,
            @Part("business_tagline") RequestBody tagline,
            @Part("type") RequestBody type,
            @Part("business_category") RequestBody businesscategories);




    @Multipart
    @POST("create-profile")
    Call<PoliticalCreateResponse> submitPoliticak(
            @Part("pUserId") RequestBody userId,
            @Part MultipartBody.Part logoFile,
            @Part MultipartBody.Part signatureFile,
            @Part MultipartBody.Part partyFile,
            @Part MultipartBody.Part leader1File,
            @Part MultipartBody.Part leader2File,
            @Part MultipartBody.Part leader3File,
            @Part MultipartBody.Part leader4File,
            @Part MultipartBody.Part leader5File,
            @Part MultipartBody.Part leader6File,
            @Part("pName") RequestBody name,
            @Part("pPhone") RequestBody phone,
            @Part("pEmail") RequestBody email,
            @Part("pInstagramUsername") RequestBody instagram,
            @Part("pFacebookUsername") RequestBody facebook,
            @Part("pTwitterUsername") RequestBody twitter,
            @Part("pDesignation1") RequestBody designation1,
            @Part("pDesignation2") RequestBody designation2);




    @Multipart
    @POST("profile/{profileId}")
    Call<PoliticalCreateResponse> updatePolitical(
            @Path("profileId") String profileId,
            @Part("pUserId") RequestBody userId,

            @Part MultipartBody.Part logoFile,
            @Part MultipartBody.Part signatureFile,
            @Part MultipartBody.Part partyFile,
            @Part MultipartBody.Part leader1File,
            @Part MultipartBody.Part leader2File,
            @Part MultipartBody.Part leader3File,
            @Part MultipartBody.Part leader4File,
            @Part MultipartBody.Part leader5File,
            @Part MultipartBody.Part leader6File,
            @Part("pName") RequestBody name,
            @Part("pPhone") RequestBody phone,
            @Part("pEmail") RequestBody email,
            @Part("pInstagramUsername") RequestBody instagram,
            @Part("pFacebookUsername") RequestBody facebook,
            @Part("pTwitterUsername") RequestBody twitter,
            @Part("pDesignation1") RequestBody designation1,
            @Part("pDesignation2") RequestBody designation2,
            @Part("_method") RequestBody method
    );

    @GET("profile/{profileId}")
    Call<GetPoliticalCreateResponse> getPolitical(
            @Path("profileId") String profileId);
    @GET("profiles/{userId}")
    Call<PoliticalProfileBaseModel> getAllPoliticalData(
            @Path("userId") String userId);


    @DELETE("profile/{profileId}")
    Call<DeletePoliticalProfileBaseModel> deletePoliticalProfile(
            @Path("profileId") String profileId);


    //*** UPdate Business****
    @Multipart
    @POST("manageBusiness")
    Call<BusinessItem> updateBusiness(
            @Part("userId") RequestBody userId,
            @Part("bussinessImage") RequestBody logo,
            @Part MultipartBody.Part file,
            @Part("business_id") RequestBody bussiness_id,
            @Part("bussiness_name") RequestBody name,
            @Part("bussiness_email") RequestBody email,
            @Part("bussiness_number") RequestBody phone,
            @Part("bussiness_website") RequestBody website,
            @Part("bussiness_address") RequestBody address,
            @Part("type") RequestBody type,
            @Part("bussiness_category") RequestBody businesvscategories);


    //*** Delete Business****

    //*** Set Default Business****
    @FormUrlEncoded
    @POST("manageBusiness")
    Call<BusinessItem> setDefaultBusiness(@Field("type") String type, @Field("userId") String userId,
                                       @Field("business_id") String bussinessId);


    //******** Send Contact *********

    @GET("ContactUs")
    Call<ApiStatus> contactUsMessage(@Query("user_id") String userid,
                                     @Query("name") String name,
                                     @Query("email") String email,
                                     @Query("mobile_no") String number,
                                     @Query("message") String massage);

    //*** Get All Home Data****
    @GET("getHomeData")
    Call<HomeItem> getHomeDatas();

    //*** My business Fragment Data load****

    @GET("getSubBusinessCatHome")
    Call<HomeItem> getSubBusinessCatHome(@Query("cat_id") String SubCategoryId);

    //*** Get Categories****

    @GET("getCategory")
    Call<List<CategoryItem>> getCategories(@Query("type") String type);

    //***Get Sub Category****

    @GET("getCategory")
    Call<List<CategoryItem>> getBusinessSubCategory(@Query("type") String type, @Query("sub_id") String SubCategoryId);

    //***Get Festival Category****

    @GET("getCategory")
    Call<List<FestivalItem>> getFestivals(@Query("type") String type, @Query("video") boolean video);

    //*** get All Language****
    @GET("getAllLanguages")
    Call<List<LanguageItem>> getLanguagess();


    //*** get All Language****
    @GET("getCustomFrame")
    Call<FrameResponse> getCustomFrames(@Query("user_id") String userId,@Query("frame_type") String type, @Query("ratio") String ratio);


    //******* Get All Types data in PreviewActivity *********

    @GET("getAllPost")
    Call<List<PostItem>> getAllPostsByCategory(@Query("page") Integer page, @Query("type") String type, @Query("dimension") String postType, @Query("catId") String categoryID, @Query("language") String language, @Query("video") Boolean video);


    //*** Get App Info****
    @GET("dailyPost")
    Call<List<PostItem>> getDailyPostData(@Query("page") Integer page, @Query("catId") String categoryID, @Query("language") String language);

    @GET("greetingData")
        Call<List<FeatureItem>> getGreetingData(@Query("catId") String categoryID,@Query("page")Integer page);

    @GET("previewGreetingData")
    Call<List<CategoryItem>> getFeaturedGreeting();

    @GET("trending-category")
    Call<List<CategoryItem>> getTrending();

    //*** Create Transaction after succesfull purchase****

    @POST("createTransactions")
    Call<ApiStatus> storeTransaction(@Query("user_id") String userId,
                                     @Query("plan_id") String planId,
                                     @Query("payment_id") String paymentId,
                                     @Query("payment_amount") String planPrice,
                                     @Query("coupon_code") String couponCode,
                                     @Query("payment_type") String type);

    //******** Send Payment *********



    //********  Stripe Payment *********


    @FormUrlEncoded
    @POST("stripe-payment")
    Call<StripeResponse> createStripePayment(@Field("order_amount") String order_amount);


    //*** Get Home Banners****
    @GET("getAllBanners")
    Call<List<SliderItem>> getAllBannerss();

    //*** Get Subscriptions Plans****
    @GET("getSubscriptionPlanList")
    Call<List<SubsPlanItem>> getSubscriptionsPlanList();

    //*** validate Coupons****

    @GET("couponValidation")
    Call<CouponItem> validateCoupon(@Query("user_id") String userId, @Query("coupon_code") String couponCode);

    //*** Get App Info****
    @GET("getAppInfo")
    Call<AppInfos> getAppInfo();


    //*** Custom Template Based Features****


    //*** Animated Video Templates****


    //*** Get Custom- Search Animated Video ****


    @GET("getAnimatedSearchVideo")
    Call<TemplateResponse> getSearchedTemplates(@Query("page") Integer page, @Query("cat") String cat,@Query("limit") Integer limit);


/*
    @GET("getAnimatedSearchVideo")
    Call<TemplateResponse> getAnimatedCustomTemplates(@Query("page") Integer page, @Query("cat") String cat, @Query("limit") Integer limit);

*/

    //*** Get Custom- Home Animated Video ****

    @GET("getAnimatedVideoHome")
    Call<DashboardTemplateResponseMain> getDashboardTemplate(@Query("page") Integer page);



   @GET("getAnimatedVideoHome")
    Call<DashboardTemplateResponseMain>  getAnimatedCategory(@Query("page") Integer page);

    @GET("musicList")
    Call<List<ModelAudio>> getAllMusic(@Query("page") Integer page, @Query("catId") Integer catId, @Query("langId") String  langId);

    //***  Poster Templates ****


    //***  get Home Posters data****

    @GET("getPosterHome")
    Call<ThumbnailWithList> getPosterHome(@Query("page") Integer page);


    //***  get Digital card data****
    @GET("digitalBusinessCardList")
    Call<List<DigitalCardModel>> getDigitalBusinessCardList();

    @GET("myBusinessCardList")
    Call<List<DigitalCardModel>> getMybusinesslist(@Query("user_id") String userId);


    @GET("addBusinessCard")
    Call<ApiStatus> addBusinessCard(@Query("user_id") String userId, @Query("businesscard_id") Integer cardId);


    //***  get Single Poster Data****
    @GET("getPosterData")
    Call<ThumbnailInfo> getPosterData(@Query("post_id") Integer postId);


    @GET("addCreated")
    Call<ModelCreated> addCreated(@Query("postId") Integer postId, @Query("type") String type);

    //***  get Posters backgrounds****
    @GET("getPosterBG")
    Call<ThumbBG> getPosterBackground();

    //***  get Posters Stickers****
    @GET("getPosterSticker")
    Call<ThumbBG> getPosterStickers();


    //***  Search Posters ****

    @GET("getPosterSearch")
    Call<ThumbnailThumbFull> getPosterSearch(@Query("page") Integer page, @Query("cat") String cat);


    @GET("whatsapp-otp")
    Call<WhatsappOtpResponse> createWhatsappOtp(@Query("number") String number);

    @GET("getDesiredCustomFrame")
    Call<ApiStatus> getDesiredCustomFrame(@Query("user_id") String userId, @Query("frame_type") String type);
    @GET("getAllService")
    Call<List<ServiceItem>> getAllService();

    @GET("getMainCategories")
    Call<List<CategoryItem>> getMainCategories();

    @Multipart
    @POST("getSubCategories")
    Call<List<CategoryItem>> getSubCategories(
            @Part("category_id") RequestBody category_id
    );

    @Multipart
    @POST("getSubCategoriesFrames")
    Call<List<PostItem>> getSubCategoryPosts(
            @Part("category_id") RequestBody category_id,
            @Part("sub_category_id") RequestBody sub_category_id
    );
    @Multipart
    @POST("getGreetingSubCategories")
    Call<List<CategoryItem>> getGreetingSubCategories(
            @Part("category_id") RequestBody category_id
    );
    @GET("getGreetingMainCategories")
    Call<List<CategoryItem>> getGreetingMainCategories();
    @Multipart
    @POST("getGreetingSubCategoriesFrames")
    Call<List<PostItem>> getGreetingSubCategoryPosts(
            @Part("category_id") RequestBody category_id,
            @Part("sub_category_id") RequestBody sub_category_id
    );
    @POST("getGreetingMainCategoriesHomePage")
    Call<List<GreetingPage>> getGreetingPageData();
    @GET("getVideoMainCategories")
    Call<List<CategoryItem>> getVideoMainCategories();
    @Multipart
    @POST("getVideoSubCategories")
    Call<List<CategoryItem>> getVideoSubCategories(
            @Part("category_id") RequestBody category_id
    );
    @Multipart
    @POST("getVideoSubCategoriesFrames")
    Call<List<VideoItem>> getVideoSubCategoriesFrames(
            @Part("category_id") RequestBody category_id,
            @Part("sub_category_id") RequestBody sub_category_id
    );
    @POST("getVideoMainCategoriesHomePage")
    Call<List<GreetingPage>> getVideoPageData();
}
