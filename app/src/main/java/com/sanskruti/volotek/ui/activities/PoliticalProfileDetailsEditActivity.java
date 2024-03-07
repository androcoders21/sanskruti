package com.sanskruti.volotek.ui.activities;

import static android.view.View.VISIBLE;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sanskruti.volotek.AdsUtils.AdsUtils;
import com.sanskruti.volotek.AdsUtils.InterstitialsAdsManager;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.binding.GlideDataBinding;
import com.sanskruti.volotek.model.ItemPolitical;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.ImageCropperFragment;
import com.sanskruti.volotek.utils.MyUtils;
import com.sanskruti.volotek.utils.NetworkConnectivity;
import com.sanskruti.volotek.utils.PreferenceManager;
import com.sanskruti.volotek.utils.Util;
import com.sanskruti.volotek.viewmodel.UserViewModel;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Random;

public class PoliticalProfileDetailsEditActivity extends AppCompatActivity {
    PreferenceManager preferenceManager;
    List<ItemPolitical> items;
    String Name = "NA",Phone = "NA",Email = "NA",Designation1 = "NA",Designation2 = "NA",Facebook = "NA", Instagram = "NA",Twitter = "NA", profileImage = "NA",
            PartyImage = "NA", Leader1 = "NA", Leader2 = "NA", Leader3 = "NA", Leader4 = "NA", Leader5 = "NA", Leader6 = "NA";
    String profileImagePath = "", profileImagePathParty = "", profileImagePathLeader1 = "", profileImagePathLeader2 = "", profileImagePathLeader3 = "", profileImagePathLeader4 = "", profileImagePathLeader5 = "", profileImagePathLeader6 = "";

    public static final int PROFILE_PHOTO = 6,PARTY_ICON = 7;
    CircularImageView ivAddImg, ivAddImgParty, ivAddImgLeader1, ivAddImgLeader2, ivAddImgLeader3, ivAddImgLeader4, ivAddImgLeader5, ivAddImgLeader6;
    private EditText etName, etEmail, etDesignation1, etDesignation2, etPhone, etFacebookUsername, etInstagramUsername, etTwitterUsername;
    private TextView btnSave;
    ProgressDialog prgDialog;
    NetworkConnectivity networkConnectivity;
    JSONArray jsonArray = new JSONArray();
    Uri imageUri;
    private String Action = "NA";
    private Dialog dialog;
    private boolean checkTwo = false;
    InterstitialsAdsManager interstitialsAdsManager;


    UserViewModel userViewModel;

    private static int PROFILE_PHOTO_TYPE = 100;

    // Getter method for PROFILE_PHOTO
    public static int getProfileType() {
        return PROFILE_PHOTO_TYPE;
    }

    public static void setProfileType(int profilePhoto) {
        PROFILE_PHOTO_TYPE = profilePhoto;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_political_profile_details_edit);

        init();


        onClick();

        getDataShare();


        showBackDialog();
    }

    private void init() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // load Ads
        new AdsUtils(this).showBannerAds(this);
        interstitialsAdsManager = new InterstitialsAdsManager(this);
        preferenceManager = new PreferenceManager(this);


        findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoliticalProfileDetailsEditActivity.this, EditPoliticalProfileDetailsActivity.class);
                intent.putExtra("index", String.valueOf("0"));
                startActivity(intent);
            }
        });

        prgDialog = new ProgressDialog(this);
        networkConnectivity = new NetworkConnectivity(this);
        prgDialog.setMessage(getString(R.string.login_loading));
        prgDialog.setCancelable(false);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etDesignation1 = (EditText) findViewById(R.id.etDesignation1);
        etDesignation2 = (EditText) findViewById(R.id.etDesignation2);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etFacebookUsername = (EditText) findViewById(R.id.etFacebookUsername);
        etInstagramUsername = (EditText) findViewById(R.id.etInstagramUsername);
        etTwitterUsername = (EditText) findViewById(R.id.etTwitterUsername);

        btnSave = (TextView) findViewById(R.id.btn_save);


        ivAddImg = (CircularImageView) findViewById(R.id.iv_add_img);
        ivAddImgParty = (CircularImageView) findViewById(R.id.iv_add_img_party);
        ivAddImgLeader1 = (CircularImageView) findViewById(R.id.iv_add_img_leader1);
        ivAddImgLeader2 = (CircularImageView) findViewById(R.id.iv_add_img_leader2);
        ivAddImgLeader3 = (CircularImageView) findViewById(R.id.iv_add_img_leader3);
        ivAddImgLeader4 = (CircularImageView) findViewById(R.id.iv_add_img_leader4);
        ivAddImgLeader5 = (CircularImageView) findViewById(R.id.iv_add_img_leader5);
        ivAddImgLeader6 = (CircularImageView) findViewById(R.id.iv_add_img_leader6);
    }

    public void onBackPressed() {

        /*if (!checkTwo){
            if (!dialog.isShowing()) {

                dialog.show();
            }
        }else{*/
        super.onBackPressed();
        /*}*/


    }

    private void showBackDialog() {

        dialog = new Dialog(this);
        this.dialog.requestWindowFeature(1);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.discard_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.findViewById(R.id.iv_close).setVisibility(VISIBLE);
        dialog.findViewById(R.id.iv_close).setOnClickListener(v -> dialog.dismiss());

        TextView textView = dialog.findViewById(R.id.tv_ok);
        textView.setText(R.string.save);

        LinearLayout button = dialog.findViewById(R.id.btn_yes);

        LinearLayout button2 = dialog.findViewById(R.id.btn_no);

        button.setOnClickListener(view -> {
            finish();
            dialog.dismiss();
        });

        button2.setOnClickListener(view -> {
            finish();
            dialog.dismiss();

        });

    }

    private void onClick() {
        ivAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                setProfileType(6);
                someActivityResultLauncher.launch(i);
            }
        });

        ivAddImgParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                setProfileType(7);
                someActivityResultLauncher.launch(i);
            }
        });

        ivAddImgLeader1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                setProfileType(1);
//                someActivityResultLauncherLeader1.launch(i);
                someActivityResultLauncher.launch(i);
            }

        });
        ivAddImgLeader2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                setProfileType(2);
//                someActivityResultLauncherLeader2.launch(i);
                someActivityResultLauncher.launch(i);
            }
        });
        ivAddImgLeader3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                setProfileType(3);
//                someActivityResultLauncherLeader3.launch(i);
                someActivityResultLauncher.launch(i);
            }
        });
        ivAddImgLeader4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                setProfileType(4);
                someActivityResultLauncher.launch(i);
//                someActivityResultLauncherLeader4.launch(i);
            }
        });
        ivAddImgLeader5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                setProfileType(5);
                someActivityResultLauncher.launch(i);
//                someActivityResultLauncherLeader5.launch(i);
            }
        });
        ivAddImgLeader6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                someActivityResultLauncherLeader6.launch(i);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                     makeJSON();

                    /*  makeJSON();*/
                }
            }
        });
    }

    private static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    String profileIdOther = "";

    private void makeJSON() {
        prgDialog.show();


        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String designation1 = etDesignation1.getText().toString();
        String designation2 = etDesignation2.getText().toString();
        String phone = etPhone.getText().toString();
        String facebookUsername = etFacebookUsername.getText().toString();
        String twitterUsername = etTwitterUsername.getText().toString();
        String instagramUsername = etInstagramUsername.getText().toString();


        if (Action != null) {
            if (Action.equalsIgnoreCase("update")) {
                Log.i("getJSONDataUpdate", "RESPONSE profileIdOther -->" + String.valueOf(profileIdOther));

                if(name.equalsIgnoreCase(Name)){
                    name = null;
                }
                if(phone.equalsIgnoreCase(Phone)){
                    phone = null;
                }
                if(email.equalsIgnoreCase(Email)){
                    email = null;
                }
                if(designation1.equalsIgnoreCase(Designation1)){
                    designation1 = null;
                }
                if(designation2.equalsIgnoreCase(Designation2)){
                    designation2 = null;
                }
                if(facebookUsername.equalsIgnoreCase(Facebook)){
                    facebookUsername = null;
                }
                if(twitterUsername.equalsIgnoreCase(Twitter)){
                    twitterUsername = null;
                }
                if(instagramUsername.equalsIgnoreCase(Instagram)){
                    instagramUsername = null;
                }
                if(profileImagePathLeader1.equalsIgnoreCase(Leader1)){
                    profileImagePathLeader1 = null;
                }
                if(profileImagePathLeader2.equalsIgnoreCase(Leader2)){
                    profileImagePathLeader2 = null;
                }
                if(profileImagePathLeader3.equalsIgnoreCase(Leader3)){
                    profileImagePathLeader3 = null;
                }
                if(profileImagePathLeader4.equalsIgnoreCase(Leader4)){
                    profileImagePathLeader4 = null;
                }
                if(profileImagePathLeader5.equalsIgnoreCase(Leader5)){
                    profileImagePathLeader5 = null;
                }
                if(profileImagePathLeader6.equalsIgnoreCase(Leader6)){
                    profileImagePathLeader6 = null;
                }
                if(profileImagePath.equalsIgnoreCase(profileImage)){
                    profileImagePath = null;
                }

                if(profileImagePathParty.equalsIgnoreCase(PartyImage)){
                    profileImagePathParty = null;
                }
                //    String profileId = Action.equalsIgnoreCase("update") ? profileIdOther : null;
                Log.i("getJSONDataUpdate", "profileImagePath-->" + String.valueOf(profileImagePath));
                Log.i("getJSONDataUpdate", "profileImagePathParty-->" + String.valueOf(profileImagePathParty));
                userViewModel.updatePolitical(profileIdOther,preferenceManager.getString(Constant.USER_ID),
                        profileImagePath,
                        "",
                        profileImagePathParty,
                        profileImagePathLeader1,
                        profileImagePathLeader2,
                        profileImagePathLeader3,
                        profileImagePathLeader4,
                        profileImagePathLeader5,
                        profileImagePathLeader6,
                        name,
                        phone,
                        email,
                        facebookUsername,
                        twitterUsername,
                        instagramUsername,
                        designation1,
                        designation2).observe(this, businessItem -> {

                    if (businessItem != null) {
                        prgDialog.dismiss();
                        Log.i("getJSONDataUpdate", "RESPONSE-->" + new Gson().toJson(businessItem));

                        //    Toast.makeText(PoliticalProfileDetailsEditActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();

                        Util.showToast(PoliticalProfileDetailsEditActivity.this, "Political Profile Update Successfully.");


//                        setResult(RESULT_OK);
//                        finish();

                    }


                });
            }
            else {

                Log.i("getJSONDataCreate", "RESPONSE profileImagePathParty -->" + String.valueOf(profileImagePathParty));

                Log.i("getJSONDataCreate", "RESPONSE profileImagePath -->" + String.valueOf(profileImagePath));
                //    String profileId = Action.equalsIgnoreCase("update") ? profileIdOther : null;
                userViewModel.submitPolitical(preferenceManager.getString(Constant.USER_ID),
                        profileImagePath,
                        "",
                        profileImagePathParty,
                        profileImagePathLeader1,
                        profileImagePathLeader2,
                        profileImagePathLeader3,
                        profileImagePathLeader4,
                        profileImagePathLeader5,
                        profileImagePathLeader6,
                        name,
                        phone,
                        email,
                        facebookUsername,
                        twitterUsername,
                        instagramUsername,
                        designation1,
                        designation2).observe(this, businessItem -> {

                    if (businessItem != null) {
                        prgDialog.dismiss();
                        Log.i("getJSONDataCreate", "RESPONSE-->" + new Gson().toJson(businessItem));
                        Log.i("getJSONDataCreate", "RESPONSE-->" + new Gson().toJson(businessItem));

                    //    Toast.makeText(PoliticalProfileDetailsEditActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                        prgDialog.dismiss();
                        Util.showToast(PoliticalProfileDetailsEditActivity.this, "Political Profile Create Successfully.");


                        setResult(RESULT_OK);
                        finish();

                    }


                });


            }
        }
      /*  try {
            if (!networkConnectivity.isConnected()) {
                Util.showToast(this, getString(R.string.error_message__no_internet));
                return;
            }
            prgDialog.show();

            JSONObject userObject = new JSONObject();

            // Add data to the JSONObject

            userObject.put("pUserId", preferenceManager.getString(Constant.USER_ID));
            userObject.put("pProfileImg", profileImagePath);
            userObject.put("pPartyImg", profileImagePathParty);

            userObject.put("pLeaderImg1", profileImagePathLeader1);
            userObject.put("pLeaderImg2", profileImagePathLeader2);
            userObject.put("pLeaderImg3", profileImagePathLeader3);
            userObject.put("pLeaderImg4", profileImagePathLeader4);
            userObject.put("pLeaderImg5", profileImagePathLeader5);
            userObject.put("pLeaderImg6", profileImagePathLeader6);

            userObject.put("pName", etName.getText().toString());
            userObject.put("pEmail", etEmail.getText().toString());
            userObject.put("pDesignation1", etDesignation1.getText().toString());
            userObject.put("pDesignation2", etDesignation2.getText().toString());
            userObject.put("pPhone", etPhone.getText().toString());

            userObject.put("pFacebookUsername", etFacebookUsername.getText().toString());
            userObject.put("pInstagramUsername", etInstagramUsername.getText().toString());
            userObject.put("pTwitterUsername", etTwitterUsername.getText().toString());

            // Add the userObject to the jsonArray

            if (Action != null) {
                if (Action.equalsIgnoreCase("update")) {
                    Log.i("getJSONData", "Step 4 update = " );
                    userObject.put("profileId", String.valueOf(generateRandomNumber(1000, 9999)));

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject userObjectTwo = jsonArray.getJSONObject(i);
                        String profileId = userObjectTwo.getString("profileId");
                        if(profileId.equalsIgnoreCase(String.valueOf(profileIdOther))){
                            jsonArray.put(i, userObject);
                        }
                    }

                } else {
                    userObject.put("profileId", String.valueOf(generateRandomNumber(1000, 9999)));
                    Log.i("getJSONData", "Step 4 save = " );
                    jsonArray.put(userObject);
                }
            }
            Log.i("getJSONData", "Step 5 final = " + String.valueOf(jsonArray));


            preferenceManager.setStringTwo(Constant.USER_POLITICAL_PROFILE, jsonArray.toString());
            Log.i("getJSONData", "JSON Data = " + jsonArray.toString());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Code to be executed after 3 seconds
                    // This is where you can put the logic you want to delay
                    prgDialog.dismiss();
                    Util.showToast(PoliticalProfileDetailsEditActivity.this, "Political Profile Create Successfully.");

                    getData();
                }
            }, 3000);

        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrashlytics.getInstance().recordException(e);
            Log.i("getJSONData", "error one = " + e.getMessage().toString());
        }*/
    }

    private void getDataShare() {
//        String userDataString = preferenceManager.getStringTwo(Constant.USER_POLITICAL_PROFILE);
//        // Retrieve JSONArray string from SharedPreferences
//
//        // Convert JSONArray string back to List<User>
//        Log.i("getJSONData", " Step 1 userDataString = " + userDataString.toString());
//        if (!userDataString.isEmpty()) {
//            try {
//                JSONArray jsonArrayModel = new JSONArray(userDataString);
//                for (int i = 0; i < jsonArrayModel.length(); i++) {
//                    JSONObject userObject = jsonArrayModel.getJSONObject(i);
//                    String id = userObject.getString("pUserId");
//
//                    jsonArray.put(jsonArrayModel.getJSONObject(i));
//
//
//                }
//                Log.i("getJSONData", "Step 2 JSON Data = " + jsonArray.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//                FirebaseCrashlytics.getInstance().recordException(e);
//                Log.i("getJSONData", "error two = " + e.getMessage().toString());
//            }
//        } else {
//            Log.i("getJSONData", "Step 3 userDataString two = " + userDataString.toString());
//        }


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Action = bundle.getString("Action");
            if (Action != null) {
                if (Action.equalsIgnoreCase("update")) {
                    profileIdOther = bundle.getString("profileId");

                    //    String profileId = Action.equalsIgnoreCase("update") ? profileIdOther : null;
                    userViewModel.getPolitical(profileIdOther).observe(this, businessItem -> {

                        if (businessItem != null) {
                            prgDialog.dismiss();
                            Log.i("getJSONData", "RESPONSE-->" + new Gson().toJson(businessItem));
                            Log.i("RESPONSE", "RESPONSE-->" + new Gson().toJson(businessItem));

                            //    Toast.makeText(PoliticalProfileDetailsEditActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();

                            String id = businessItem.profiles.pUserId;



                            if(businessItem.profiles.pName != null){
                                Name = businessItem.profiles.pName;
                                etName.setText(businessItem.profiles.pName);
                            }
                            if(businessItem.profiles.pPhone != null){
                                Name = businessItem.profiles.pName;
                                etPhone.setText(businessItem.profiles.pPhone);
                            }

                            if(businessItem.profiles.pEmail != null){
                                Email = businessItem.profiles.pEmail;
                                etEmail.setText(businessItem.profiles.pEmail);
                            }
                            if(businessItem.profiles.pDesignation1 != null){
                                Designation1 = businessItem.profiles.pDesignation1;
                                etDesignation1.setText(businessItem.profiles.pDesignation1);
                            }
                            if(businessItem.profiles.pDesignation2 != null){
                                Designation2 = businessItem.profiles.pDesignation2;
                                etDesignation2.setText(businessItem.profiles.pDesignation2);
                            }

                            if(businessItem.profiles.pFacebookUsername != null){
                                Facebook = businessItem.profiles.pFacebookUsername;
                                etFacebookUsername.setText(businessItem.profiles.pFacebookUsername);
                            }
                            if(businessItem.profiles.pInstagramUsername != null){
                                Instagram = businessItem.profiles.pInstagramUsername;
                                etInstagramUsername.setText(businessItem.profiles.pInstagramUsername);
                            }
                            if(businessItem.profiles.pTwitterUsername != null){
                                Twitter = businessItem.profiles.pTwitterUsername;
                                etTwitterUsername.setText(businessItem.profiles.pTwitterUsername);
                            }

                            if(businessItem.profiles.pProfileImg != null){
                                profileImage = businessItem.profiles.pProfileImg;
                                profileImagePath = businessItem.profiles.pProfileImg;
                            }
                            if(businessItem.profiles.pPartyImg != null){
                                PartyImage = businessItem.profiles.pPartyImg;
                                profileImagePathParty = businessItem.profiles.pPartyImg;
                            }


                            if(businessItem.profiles.pLeaderImg1 != null){
                                Leader1 = businessItem.profiles.pLeaderImg1;
                                profileImagePathLeader1 = businessItem.profiles.pLeaderImg1;
                            }
                            if(businessItem.profiles.pLeaderImg2 != null){
                                Leader2 = businessItem.profiles.pLeaderImg2;
                                profileImagePathLeader2 = businessItem.profiles.pLeaderImg2;
                            }

                            if(businessItem.profiles.pLeaderImg3 != null){
                                Leader3 = businessItem.profiles.pLeaderImg3;
                                profileImagePathLeader3 = businessItem.profiles.pLeaderImg3;
                            }
                            if(businessItem.profiles.pLeaderImg4 != null){
                                Leader4 = businessItem.profiles.pLeaderImg4;
                                profileImagePathLeader4 = businessItem.profiles.pLeaderImg4;
                            }

                            if(businessItem.profiles.pLeaderImg5 != null){
                                Leader5 = businessItem.profiles.pLeaderImg5;
                                profileImagePathLeader5 = businessItem.profiles.pLeaderImg5;
                            }
                            if(businessItem.profiles.pLeaderImg6 != null){
                                Leader6 = businessItem.profiles.pLeaderImg6;
                                profileImagePathLeader6 = businessItem.profiles.pLeaderImg6;
                            }



                            GlideDataBinding.bindImage(ivAddImg, profileImagePath);
                            GlideDataBinding.bindImage(ivAddImgParty, profileImagePathParty);
                            GlideDataBinding.bindImage(ivAddImgLeader1, profileImagePathLeader1);
                            GlideDataBinding.bindImage(ivAddImgLeader2, profileImagePathLeader2);
                            GlideDataBinding.bindImage(ivAddImgLeader3, profileImagePathLeader3);
                            GlideDataBinding.bindImage(ivAddImgLeader4, profileImagePathLeader4);
                            GlideDataBinding.bindImage(ivAddImgLeader5, profileImagePathLeader5);
                            GlideDataBinding.bindImage(ivAddImgLeader6, profileImagePathLeader6);

//
//                            setResult(RESULT_OK);
//                            finish();

                        }


                    });
                   /* try {
                        JSONArray jsonArrayModel = new JSONArray(userDataString);
                        *//* JSONObject userObject = jsonArrayModel.getJSONObject(index);*//*
                        items = new ArrayList<>();

                        for (int i = 0; i < jsonArrayModel.length(); i++) {
                            JSONObject userObject = jsonArrayModel.getJSONObject(i);
                            String profileId = userObject.getString("profileId");
                            if(profileId.equalsIgnoreCase(profileIdOther)){
                                String id = userObject.getString("pUserId");

                                String pName = userObject.getString("pName");
                                String pPhone = userObject.getString("pPhone");
                                String pEmail = userObject.getString("pEmail");
                                String pFacebookUsername = userObject.getString("pFacebookUsername");
                                String pInstagramUsername = userObject.getString("pInstagramUsername");
                                String pTwitterUsername = userObject.getString("pTwitterUsername");

                                String pDesignation1 = userObject.getString("pDesignation1");
                                String pDesignation2 = userObject.getString("pDesignation2");

                                String pProfileImg = userObject.getString("pProfileImg");
                                String pPartyImg = userObject.getString("pPartyImg");
                                String pLeaderImg1 = userObject.getString("pLeaderImg1");
                                String pLeaderImg2 = userObject.getString("pLeaderImg2");
                                String pLeaderImg3 = userObject.getString("pLeaderImg3");
                                String pLeaderImg4 = userObject.getString("pLeaderImg4");
                                String pLeaderImg5 = userObject.getString("pLeaderImg5");
                                String pLeaderImg6 = userObject.getString("pLeaderImg6");

                                etName.setText(pName);
                                etPhone.setText(pPhone);
                                etEmail.setText(pEmail);
                                etDesignation1.setText(pDesignation1);
                                etDesignation2.setText(pDesignation2);
                                etFacebookUsername.setText(pFacebookUsername);
                                etInstagramUsername.setText(pInstagramUsername);
                                etTwitterUsername.setText(pTwitterUsername);
                                profileImagePath = pProfileImg;
                                profileImagePathParty = pPartyImg;
                                profileImagePathLeader1 = pLeaderImg1;
                                profileImagePathLeader2 = pLeaderImg2;
                                profileImagePathLeader3 = pLeaderImg3;
                                profileImagePathLeader4 = pLeaderImg4;
                                profileImagePathLeader5 = pLeaderImg5;
                                profileImagePathLeader6 = pLeaderImg6;

                                GlideDataBinding.bindImage(ivAddImg, profileImagePath);
                                GlideDataBinding.bindImage(ivAddImgParty, profileImagePathParty);
                                GlideDataBinding.bindImage(ivAddImgLeader1, profileImagePathLeader1);
                                GlideDataBinding.bindImage(ivAddImgLeader2, profileImagePathLeader2);
                                GlideDataBinding.bindImage(ivAddImgLeader3, profileImagePathLeader3);
                                GlideDataBinding.bindImage(ivAddImgLeader4, profileImagePathLeader4);
                                GlideDataBinding.bindImage(ivAddImgLeader5, profileImagePathLeader5);
                                GlideDataBinding.bindImage(ivAddImgLeader6, profileImagePathLeader6);
                            }



                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        FirebaseCrashlytics.getInstance().recordException(e);
                        Log.i("getJSONData", "error two = " + e.getMessage().toString());
                    }*/


                }
            }

        }


    }

    private void getData() {
        String userDataString = preferenceManager.getStringTwo(Constant.USER_POLITICAL_PROFILE);
        Log.i("getJSONData", "JSON userDataString = " + userDataString.toString());
        try {
            JSONArray jsonArray = new JSONArray(userDataString);
// Access individual data elements
            Log.i("getJSONData", "JSON jsonArray = " + jsonArray.toString());
            // Access individual data elements
        } catch (JSONException e) {
            e.printStackTrace();
            FirebaseCrashlytics.getInstance().recordException(e);
            Log.i("getJSONData", "error three = " + e.getMessage().toString());
        }
        checkTwo = true;
        onBackPressed();
    }

    private Boolean validate() {
        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError(getResources().getString(R.string.hint_name));
            etName.requestFocus();
            return false;
        } /*else if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError(getResources().getString(R.string.hint_email));
            etEmail.requestFocus();
            return false;

        } else if (etDesignation1.getText().toString().isEmpty()) {
            etDesignation1.setError(getResources().getString(R.string.hint_designation));
            etDesignation1.requestFocus();
            return false;

        } else if (etDesignation2.getText().toString().isEmpty()) {
            etDesignation2.setError(getResources().getString(R.string.hint_designation));
            etDesignation2.requestFocus();
            return false;

        } else if (etPhone.getText().toString().isEmpty()) {
            etPhone.setError(getResources().getString(R.string.hint_phone_number));
            etPhone.requestFocus();
            return false;

        } else if (etFacebookUsername.getText().toString().isEmpty()) {
            etFacebookUsername.setError(getResources().getString(R.string.hint_facebook));
            etFacebookUsername.requestFocus();
            return false;

        } else if (etInstagramUsername.getText().toString().isEmpty()) {
            etInstagramUsername.setError(getResources().getString(R.string.hint_instagram));
            etInstagramUsername.requestFocus();
            return false;

        } else if (etTwitterUsername.getText().toString().isEmpty()) {
            etTwitterUsername.setError(getResources().getString(R.string.hint_twitter));
            etTwitterUsername.requestFocus();
            return false;

        } else if (profileImagePath.isEmpty()) {
            Toast.makeText(this, "Please Select Profile Image.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (profileImagePathParty.isEmpty()) {
            Toast.makeText(this, "Please Select Party Image.", Toast.LENGTH_SHORT).show();
            return false;
        } *//* else  if(profileImagePathLeader1.isEmpty()){
            Toast.makeText(this, "Please Select Leader 1 Image.", Toast.LENGTH_SHORT).show();
            return false;
        } else  if(profileImagePathLeader2.isEmpty()){
            Toast.makeText(this, "Please Select Leader 2 Image.", Toast.LENGTH_SHORT).show();
            return false;
        }  else  if(profileImagePathLeader3.isEmpty()){
            Toast.makeText(this, "Please Select Leader 3 Image.", Toast.LENGTH_SHORT).show();
            return false;
        } else  if(profileImagePathLeader4.isEmpty()){
            Toast.makeText(this, "Please Select Leader 4 Image.", Toast.LENGTH_SHORT).show();
            return false;
        } else  if(profileImagePathLeader5.isEmpty()){
            Toast.makeText(this, "Please Select Leader 5 Image.", Toast.LENGTH_SHORT).show();
            return false;
        } else  if(profileImagePathLeader6.isEmpty()){
            Toast.makeText(this, "Please Select Leader 6 Image.", Toast.LENGTH_SHORT).show();
            return false;
        }*/ else {
            return true;
        }
    }

    // profile photo
//    Lucky bro's code
//    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // Here, no request code
//                        if (result.getData() != null) {
//                            Uri selectedImage = result.getData().getData();
//                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                            if (selectedImage != null) {
//                                Cursor cursor = getContentResolver().query(selectedImage,
//                                        null, null, null, null);
//
//                                if (cursor != null) {
//                                    cursor.moveToFirst();
//
//                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                    profileImagePath = cursor.getString(columnIndex);
//
//                                    new ImageCropperFragment(0, profileImagePath, (id, out) -> {
//
//                                        profileImagePath = out;
//                                        imageUri = Uri.parse(out);
//
//                                        GlideDataBinding.bindImage(ivAddImg, out);
//
//
//                                    }).show(getSupportFragmentManager(), "");
//
//                                    cursor.close();
//
//                                }
//                            }
//                        }
//                    }
//                }
//            });

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Here, no request code
                    if (result.getData() != null) {
                        getImageFromURI(result);
                    }
                }
            });
    private void getImageFromURI(ActivityResult result) {
        Uri selectedImage = result.getData().getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        if (selectedImage != null) {
            Cursor cursor = getContentResolver().query(selectedImage,
                    null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                switch (getProfileType()) {
                    case 6:
                        profileImagePath = cursor.getString(columnIndex);
                        break;
                    case 7:
                        profileImagePathParty = cursor.getString(columnIndex);
                        break;
                    case 1:
                        profileImagePathLeader1 = cursor.getString(columnIndex);
                        break;
                    case 2:
                        profileImagePathLeader2 = cursor.getString(columnIndex);
                        break;
                    case 3:
                        profileImagePathLeader3 = cursor.getString(columnIndex);
                        break;
                    case 4:
                        profileImagePathLeader4 = cursor.getString(columnIndex);
                        break;
                    case 5:
                        profileImagePathLeader5 = cursor.getString(columnIndex);
                        break;
                    default:
                        // Handle default case (if necessary)
                        break;
                }
//                profileImagePath = cursor.getString(columnIndex);
                cursor.close();

                imageUri = selectedImage;

                beginCrop(imageUri);


            }
        }
    }

    private void beginCrop(Uri uri) {
        if (uri != null) {
            try {

                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), new File(uri.getPath()).getName()));
                UCrop.Options options2 = new UCrop.Options();
                options2.setCompressionFormat(Bitmap.CompressFormat.PNG);
                options2.setFreeStyleCropEnabled(true);

                UCrop.of(uri, destinationUri)
                        .withOptions(options2)
                        .start(this);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

 if (requestCode == UCrop.REQUEST_CROP) {

            if (data != null) {
                new ImageCropperFragment(0, MyUtils.getPathFromURI(this, UCrop.getOutput(data)), (id, out) -> {

//                    profileImagePath = out;
                    Log.i("saqlain",out);
                    imageUri = Uri.parse(out);
                    switch (getProfileType()) {
                        case 6:
                            GlideDataBinding.bindImage(ivAddImg, out);
                            profileImagePath = out;
                            break;
                        case 7:
                            GlideDataBinding.bindImage(ivAddImgParty, out);
                            profileImagePathParty = out;
                            break;
                        case 1:
                            GlideDataBinding.bindImage(ivAddImgLeader1, out);
                            profileImagePathLeader1 = out;
                            break;
                        case 2:
                            GlideDataBinding.bindImage(ivAddImgLeader2, out);
                            profileImagePathLeader2 = out;
                            break;
                        case 3:
                            GlideDataBinding.bindImage(ivAddImgLeader3, out);
                            profileImagePathLeader3 = out;
                            break;
                        case 4:
                            GlideDataBinding.bindImage(ivAddImgLeader4, out);
                            profileImagePathLeader4 = out;
                            break;
                        case 5:
                            GlideDataBinding.bindImage(ivAddImgLeader5, out);
                            profileImagePathLeader5 = out;
                            break;
                        default:
                            // Handle default case (if necessary)
                            break;
                    }

//                    GlideDataBinding.bindImage(ivAddImg, out);

                }).show(getSupportFragmentManager(), "");
            }

        }


    }


    ActivityResultLauncher<Intent> someActivityResultLauncherParty = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        if (result.getData() != null) {
                            Uri selectedImage = result.getData().getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            if (selectedImage != null) {
                                Cursor cursor = getContentResolver().query(selectedImage,
                                        null, null, null, null);

                                if (cursor != null) {
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    profileImagePathParty = cursor.getString(columnIndex);

                                    new ImageCropperFragment(0, profileImagePathParty, (id, out) -> {

                                        profileImagePathParty = out;
                                        imageUri = Uri.parse(out);

                                        GlideDataBinding.bindImage(ivAddImgParty, out);


                                    }).show(getSupportFragmentManager(), "");

                                    cursor.close();

                                }
                            }
                        }
                    }
                }
            });


    ActivityResultLauncher<Intent> someActivityResultLauncherLeader1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        if (result.getData() != null) {
                            Uri selectedImage = result.getData().getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            if (selectedImage != null) {
                                Cursor cursor = getContentResolver().query(selectedImage,
                                        null, null, null, null);

                                if (cursor != null) {
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    profileImagePathLeader1 = cursor.getString(columnIndex);
                                    cursor.close();

                                    // First, initiate UCrop
//                                    beginUCrop(selectedImage);
//                                    new ImageCropperFragment(0, profileImagePathLeader1, (id, out) -> {
//
//                                        profileImagePathLeader1 = out;
//                                        imageUri = Uri.parse(out);
//
//                                        GlideDataBinding.bindImage(ivAddImgLeader1, out);
//                                        beginUCrop(imageUri);
//
//                                    }).show(getSupportFragmentManager(), "");

//                                    cursor.close();

                                }
                            }
                        }
                    }
                }
            });


    ActivityResultLauncher<Intent> someActivityResultLauncherLeader2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        if (result.getData() != null) {
                            Uri selectedImage = result.getData().getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            if (selectedImage != null) {
                                Cursor cursor = getContentResolver().query(selectedImage,
                                        null, null, null, null);

                                if (cursor != null) {
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    profileImagePathLeader2 = cursor.getString(columnIndex);

                                    new ImageCropperFragment(0, profileImagePathLeader2, (id, out) -> {

                                        profileImagePathLeader2 = out;
                                        imageUri = Uri.parse(out);

                                        GlideDataBinding.bindImage(ivAddImgLeader2, out);


                                    }).show(getSupportFragmentManager(), "");

                                    cursor.close();

                                }
                            }
                        }
                    }
                }
            });


    ActivityResultLauncher<Intent> someActivityResultLauncherLeader3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        if (result.getData() != null) {
                            Uri selectedImage = result.getData().getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            if (selectedImage != null) {
                                Cursor cursor = getContentResolver().query(selectedImage,
                                        null, null, null, null);

                                if (cursor != null) {
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    profileImagePathLeader3 = cursor.getString(columnIndex);

                                    new ImageCropperFragment(0, profileImagePathLeader3, (id, out) -> {

                                        profileImagePathLeader3 = out;
                                        imageUri = Uri.parse(out);

                                        GlideDataBinding.bindImage(ivAddImgLeader3, out);


                                    }).show(getSupportFragmentManager(), "");

                                    cursor.close();

                                }
                            }
                        }
                    }
                }
            });


    ActivityResultLauncher<Intent> someActivityResultLauncherLeader4 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        if (result.getData() != null) {
                            Uri selectedImage = result.getData().getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            if (selectedImage != null) {
                                Cursor cursor = getContentResolver().query(selectedImage,
                                        null, null, null, null);

                                if (cursor != null) {
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    profileImagePathLeader4 = cursor.getString(columnIndex);

                                    new ImageCropperFragment(0, profileImagePathLeader4, (id, out) -> {

                                        profileImagePathLeader4 = out;

                                        imageUri = Uri.parse(out);
                                        Log.i("saqlain",out);
                                        GlideDataBinding.bindImage(ivAddImgLeader4, out);


                                    }).show(getSupportFragmentManager(), "");

                                    cursor.close();

                                }
                            }
                        }
                    }
                }
            });


    ActivityResultLauncher<Intent> someActivityResultLauncherLeader5 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        if (result.getData() != null) {
                            Uri selectedImage = result.getData().getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            if (selectedImage != null) {
                                Cursor cursor = getContentResolver().query(selectedImage,
                                        null, null, null, null);

                                if (cursor != null) {
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    profileImagePathLeader5 = cursor.getString(columnIndex);

                                    new ImageCropperFragment(0, profileImagePathLeader5, (id, out) -> {

                                        profileImagePathLeader5 = out;
                                        imageUri = Uri.parse(out);

                                        GlideDataBinding.bindImage(ivAddImgLeader5, out);


                                    }).show(getSupportFragmentManager(), "");

                                    cursor.close();

                                }
                            }
                        }
                    }
                }
            });


    ActivityResultLauncher<Intent> someActivityResultLauncherLeader6 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        if (result.getData() != null) {
                            Uri selectedImage = result.getData().getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            if (selectedImage != null) {
                                Cursor cursor = getContentResolver().query(selectedImage,
                                        null, null, null, null);

                                if (cursor != null) {
                                    cursor.moveToFirst();

                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    profileImagePathLeader6 = cursor.getString(columnIndex);

                                    new ImageCropperFragment(0, profileImagePathLeader6, (id, out) -> {

                                        profileImagePathLeader6 = out;
                                        imageUri = Uri.parse(out);

                                        GlideDataBinding.bindImage(ivAddImgLeader6, out);


                                    }).show(getSupportFragmentManager(), "");

                                    cursor.close();

                                }
                            }
                        }
                    }
                }
            });

//    private void beginUCrop(Uri uri) {
//        Log.i("saqlain","In begin crop");
//        if (uri != null) {
//            try {
//                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), new File(uri.getPath()).getName()));
//                UCrop.Options options2 = new UCrop.Options();
//                options2.setCompressionFormat(Bitmap.CompressFormat.PNG);
//                options2.setFreeStyleCropEnabled(true);
//
//                UCrop.of(uri, destinationUri)
//                        .withOptions(options2)
//                        .start(this);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}