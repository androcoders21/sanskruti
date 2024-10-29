package com.sanskruti.volotek.api;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.onesignal.OneSignal;
import com.sanskruti.volotek.MyApplication;
import com.sanskruti.volotek.ui.activities.CustomSplashActivity;
import com.sanskruti.volotek.ui.activities.LoginActivity;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    static {
        System.loadLibrary("brandboost_library");
    }

    public static native String baseUrlFromJNI();

    public static String App_URl = baseUrlFromJNI();

    public static native List<String> getCommands2(String tempVideoPath, String watermarkPath, boolean isWatermarkEnabled, String musicPath, boolean isMute, String outputPath);

    public static native String getFilterComplex();

    public static ApiService getApiDataService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization", new PreferenceManager(MyApplication.getAppContext()).getString(Constant.api_key))
                            .header("Content-Type", "text/plain")
                            .method(original.method(), original.body())
                            .build();

                    Response response = chain.proceed(request);

                    int tryCount = 0;
                    while (!response.isSuccessful() && tryCount < 2) {

                        Log.d("intercept", "Request is not successful - " + tryCount);

                        tryCount++;

                        // retry the request
                        response.close();
                        response = chain.proceed(request);
                    }
                    if(response.code() == 401){
                       Log.i("saqlain","User logged on some another device");
                        handleUnAuthorized();
                    }
                    return response;
                })
                .build();



        return new Retrofit.Builder()
                .baseUrl(baseUrlFromJNI())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService.class);
    }

    private static void handleUnAuthorized(){
        Context context = MyApplication.getAppContext();
        new Handler(Looper.getMainLooper()).post(()->{
            PreferenceManager preferenceManager = new PreferenceManager(context);
            preferenceManager.clearAllDataOnLogout();

                preferenceManager.setBoolean(Constant.IS_LOGIN, false);

                // Redirect to the splash screen
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
        });
    }
}
