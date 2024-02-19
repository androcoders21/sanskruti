package com.sanskruti.volotek.ui.activities;

import static android.view.View.VISIBLE;
import static com.sanskruti.volotek.MyApplication.context;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaredrummler.android.colorpicker.ColorPickerView;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.binding.GlideDataBinding;
import com.sanskruti.volotek.custom.poster.adapter.FontAdapter;
import com.sanskruti.volotek.custom.poster.listener.OnClickCallback;
import com.sanskruti.volotek.model.ItemPolitical;
import com.sanskruti.volotek.ui.dialog.UniversalDialog;
import com.sanskruti.volotek.ui.fragments.FontFamilyBottomSheetDialogFragment;
import com.sanskruti.volotek.utils.Configure;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.MyUtils;
import com.sanskruti.volotek.utils.PreferenceManager;
import com.sanskruti.volotek.viewmodel.UserViewModel;
import com.squareup.otto.Bus;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EditPersonalProfileDetailsActivity extends AppCompatActivity {
    LinearLayout llFramesLl, llProfilePhotoLl, llNameLl, btnDownload, llDesignation1Ll, llDesignation2Ll, llMobileLl,
            llLeadersPhotoLl, llSocialMediaIconsLl, llPartyIconLayout, llcolorll, llcolord1ll, llcolord2ll, llcolorMobilell;
    FontAdapter adapter;
    LinearLayout llfontll, llfontd1ll, llfontd2ll, llfontMobilell, fm ;

    LinearLayout lay_profile_photo_ll, lay_party_photo_ll, lay_name_ll, lay_Designation1_ll, lay_Designation2_ll, lay_Mobile_ll, lay_SocialMedia_ll, lay_LeadersPhoto_ll, lay_frames_ll;

    LinearLayout profilePhotoShowLLll, partyPhotoShowLLll, nameShowLLll, designation1ShowLLll, designation2ShowLLll, mobileShowLLll, socialMediaShowLLll;

    boolean checkProfilePhoto = true, checkPartyPhoto = true, checkName = true, checkDesignation1 = true, checkDesignation2 = true, checkMobile = true, checkSocialMedia = true;
    private ImageView ivFlipIv;
    private SeekBar btnseekBarProfilePhoto, btnseekBarPartyPhoto, btnseekBarName, btnseekBarDesignation1, btnseekBarDesignation2, btnseekBarMobile;

    private String position;

    private ImageView ivbtnBoldFontName, ivbtnItalicFontName, ivbtnUnderlineFontName,
            ivbtnBoldFontDesignation1, ivbtnItalicFontDesignation1, ivbtnUnderlineFontDesignation1,
            ivbtnBoldFontDesignation2, ivbtnItalicFontDesignation2, ivbtnUnderlineFontDesignation2,
            ivbtnBoldFontMobile, ivbtnItalicFontMobile, ivbtnUnderlineFontMobile;


    private ImageView visibleProfilePhotoEyeIconIv, hideProfilePhotoEyeIconIv, visiblePartyPhotoEyeIconIv, hidePartyPhotoEyeIconIv, visibleNameKyeIconIv, hideNameEyeIconIv,
            visibleDesignation1KyeIconIv, hideDesignation1EyeIconIv, visibleDesignation2KyeIconIv, hideDesignation2EyeIconIv,
            visibleMobileKyeIconIv, hideMobileEyeIconIv, visibleSocialMediaKyeIconIv, hideSocialMediaEyeIconIv;

    private TextView tvProfilePhotoShowTv, tvPartyPhotoShowTv, tvNameShowTv, tvDesignation1ShowTv, tvDesignation2ShowTv, tvMobileShowTv, tvSocialMediaShowTv;
    UniversalDialog universalDialog;
    RelativeLayout constraint;
    PreferenceManager preferenceManager;
    JSONArray jsonArray = new JSONArray();
    ImageView ivAddImg, ivAddImgParty, ivAddImgLeader1, ivAddImgLeader2, ivAddImgLeader3, ivAddImgLeader4, ivAddImgLeader5, ivAddImgLeader6, ivSocialMediaIv;


    ImageView ivAddImgLeader11, ivAddImgLeader22, ivAddImgLeader33, ivAddImgLeader44, ivAddImgLeader55, ivAddImgLeader66;

    ImageView ivFrames00, ivFrames11, ivFrames22, ivFrames33;
    private TextView tvNameTv, tvDesignation1Tv, tvDesignation2Tv, tvMobileNoTv;

    private String pName="", pPhone="", pEmail="", pFacebookUsername="", pInstagramUsername="", pTwitterUsername="",
            pDesignation1="", pDesignation2="", pProfileImg="", pPartyImg="", pLeaderImg1 = "", pLeaderImg2="",
            pLeaderImg3="", pLeaderImg4="", pLeaderImg5="", pLeaderImg6="";
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_profile_details);


        init();


        onClick();
        onShowHide();
        getDataShare();


        showBackDialog();
    }
    private void showColorPickerDialog(TextView tv) {
        // Inflate the custom layout for the color picker
        View view = LayoutInflater.from(this).inflate(R.layout.color_picker_layout, null);

        // Find views in the custom layout
        final ColorPickerView colorPickerView = view.findViewById(R.id.colorPickerView);

        // Build the AlertDialog
        AlertDialog colorPickerDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the selected color when the OK button is clicked
                        int selectedColor = colorPickerView.getColor();
                        tv.setTextColor(selectedColor);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();

        // Show the AlertDialog
        colorPickerDialog.show();
    }
    private void showFontFamilyBottomSheet(TextView textView) {
        FontFamilyBottomSheetDialogFragment bottomSheetFragment = new FontFamilyBottomSheetDialogFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

        bottomSheetFragment.setOnFontFamilySelectedListener(new FontFamilyBottomSheetDialogFragment.OnFontFamilySelectedListener() {
            @Override
            public void onFontFamilySelected(String fontFamily) {
                Log.i("checkfontFamily", "fontFamily 6 = " + String.valueOf(fontFamily));

                applyFontFamily(fontFamily, textView);
            }
        });
    }
    public void onBackPressed() {

        if (!dialog.isShowing()) {

            dialog.show();
        }

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
    private void applyFontFamily(String fontFamily, TextView textView) {
        Typeface typeface;
        Log.i("checkfontFamily", "fontFamily 7 = " + String.valueOf(fontFamily));
        switch (fontFamily) {
            case "Roboto":
                typeface = Typeface.create("Roboto", Typeface.NORMAL);
                break;
            case "Caveat":
                typeface = Typeface.create("caveat_regular", Typeface.NORMAL);
                break;
            case "Arial":
                typeface = Typeface.create("arial", Typeface.NORMAL);
                break;
            // Add more font family cases as needed
            case "Nunito":
                typeface = Typeface.create("nunito_sans_regular", Typeface.NORMAL);
                break;
            case "Georgia":
                typeface = Typeface.create("georgia", Typeface.NORMAL);
                break;
            case "Merriweather":
                typeface = Typeface.create("merriweather", Typeface.NORMAL);
            default:
                typeface = Typeface.DEFAULT;
                break;
        }

        // Apply the selected font family to the TextView
        textView.setTypeface(typeface);
    }
    private class DownloadImageTaskThum extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            Log.i("thisisknowdowload", "AsyncTask urls = " + urls.toString());
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("thisisknowdowload", "img = " + e.getMessage());
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            Log.i("thisisknowdowload", "2 img = " + result.toString());
            if (result != null) {
                // Set the downloaded image as the background of the RelativeLayout

                RelativeLayout Thumpost = findViewById(R.id.constraintThum);
                Thumpost.setBackground(new BitmapDrawable(getResources(), result));
            }
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            Log.i("thisisknowdowload", "AsyncTask urls = " + urls.toString());
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("thisisknowdowload", "img = " + e.getMessage());
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            Log.i("thisisknowdowload", "2 img = " + result.toString());
            if (result != null) {
                // Set the downloaded image as the background of the RelativeLayout

                constraint.setBackground(new BitmapDrawable(getResources(), result));
            }
        }
    }
    List<ItemPolitical> items = new ArrayList<>();
    UserViewModel userViewModel;
    private void getDataShare() {


        String userDataString = preferenceManager.getStringTwo(Constant.USER_POLITICAL_PROFILE);
        // Retrieve JSONArray string from SharedPreferences

        // Convert JSONArray string back to List<User>
        Log.i("getJSONData", "userDataString = " + userDataString.toString());

        position = getIntent().getStringExtra("index");
        String imgUrl = getIntent().getStringExtra("img");

        if (getIntent().getStringExtra("imgThum") != null) {
            //    Toast.makeText(this, "not null", Toast.LENGTH_SHORT).show();
            LinearLayout layout = (LinearLayout) findViewById(R.id.bottomLay);
            layout.setVisibility(View.GONE);


            fm.setVisibility(View.GONE);

            LinearLayout fmlogo = (LinearLayout) findViewById(R.id.linearLogo);
            fmlogo.setVisibility(View.GONE);
            String imgUrlThum = getIntent().getStringExtra("imgThum");

            new DownloadImageTaskThum().execute(imgUrlThum);
        } else {
            //    Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        String userImg = getIntent().getStringExtra("userImg");
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        if(name != null){
            pName = name;
        }
      /*  if(businessItem.profiles.pPhone != null){
            pPhone = businessItem.profiles.pPhone;
        }*/
        if(email != null){
            pPhone  = email;
        }
       /* if(businessItem.profiles.pFacebookUsername != null){
            pFacebookUsername = businessItem.profiles.pFacebookUsername;
        }

        if(businessItem.profiles.pInstagramUsername != null){
            pInstagramUsername = businessItem.profiles.pInstagramUsername;
        }
        if(businessItem.profiles.pTwitterUsername != null){
            pTwitterUsername = businessItem.profiles.pTwitterUsername;
        }*/

    /*    if(businessItem.profiles.pDesignation1 != null){
            pDesignation1 = businessItem.profiles.pDesignation1;
        }
        if(businessItem.profiles.pDesignation2 != null){
            pDesignation2 = businessItem.profiles.pDesignation2;
        }*/

        if(userImg != null){
            pProfileImg = userImg;
        }
  /*      if(businessItem.profiles.pPartyImg != null){
            pPartyImg = businessItem.profiles.pPartyImg;
        }

        if(businessItem.profiles.pLeaderImg1 != null){
            pLeaderImg1 = businessItem.profiles.pLeaderImg1;
        }
        if(businessItem.profiles.pLeaderImg2 != null){
            pLeaderImg2 = businessItem.profiles.pLeaderImg2;
        }
        if(businessItem.profiles.pLeaderImg3 != null){
            pLeaderImg3 = businessItem.profiles.pLeaderImg3;
        }
        if(businessItem.profiles.pLeaderImg4 != null){
            pLeaderImg4 = businessItem.profiles.pLeaderImg4;
        }

        if(businessItem.profiles.pLeaderImg5 != null){
            pLeaderImg5 = businessItem.profiles.pLeaderImg5;
        }
        if(businessItem.profiles.pLeaderImg6 != null){
            pLeaderImg6 = businessItem.profiles.pLeaderImg6;
        }
*/



        setData();

        Log.i("getJSONDataLuc", "RESPONSE profileIdOther 777-->" + String.valueOf(name));
        //    String profileId = Action.equalsIgnoreCase("update") ? profileIdOther : null;
/*        userViewModel.getPolitical(profileIdOther).observe(this, businessItem -> {

            if (businessItem != null) {
                //  prgDialog.dismiss();
                Log.i("getJSONDataLuc", "RESPONSE 777-->" + new Gson().toJson(businessItem));


                //    Toast.makeText(PoliticalProfileDetailsEditActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();

                String id = businessItem.profiles.pUserId;


//                            setResult(RESULT_OK);
//                            finish();

            }


        });*/

        // Load the image from the URL and set it as the background
        new DownloadImageTask().execute(imgUrl);
        int index = Integer.valueOf(position);
       /* if (!userDataString.isEmpty()) {
            try {
                JSONArray jsonArrayModel = new JSONArray(userDataString);

                items = new ArrayList<>();

                for (int i = 0; i < jsonArrayModel.length(); i++) {
                    JSONObject userObject = jsonArrayModel.getJSONObject(i);
                    String id = userObject.getString("pUserId");
                    String profileId = userObject.getString("profileId");
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

                    if(preferenceManager.getString(Constant.USER_ID).equalsIgnoreCase(id)){
                        items.add(new ItemPolitical(profileId,id, pName, pPhone, pEmail, pFacebookUsername, pInstagramUsername, pTwitterUsername,
                                pDesignation1, pDesignation2, pProfileImg, pPartyImg, pLeaderImg1, pLeaderImg2,
                                pLeaderImg3, pLeaderImg4, pLeaderImg5, pLeaderImg6));
                    }
                }

                if (items.size()>index){
                    pName = items.get(index).pName;
                    pPhone = items.get(index).pPhone;
                    pEmail = items.get(index).pEmail;

                    pFacebookUsername = items.get(index).pFacebookUsername;
                    pInstagramUsername = items.get(index).pInstagramUsername;
                    pTwitterUsername = items.get(index).pTwitterUsername;

                    pDesignation1 = items.get(index).pDesignation1;
                    pDesignation2 = items.get(index).pDesignation2;



                    pProfileImg = items.get(index).pProfileImg;
                    pPartyImg = items.get(index).pPartyImg;
                    pLeaderImg1 = items.get(index).pLeaderImg1;
                    pLeaderImg2 = items.get(index).pLeaderImg2;
                    pLeaderImg3 = items.get(index).pLeaderImg3;
                    pLeaderImg4 = items.get(index).pLeaderImg4;
                    pLeaderImg5 = items.get(index).pLeaderImg5;
                    pLeaderImg6 = items.get(index).pLeaderImg6;

                    setData();
                }





                Log.i("getJSONData", "JSON Data = " + jsonArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                FirebaseCrashlytics.getInstance().recordException(e);
                Log.i("getJSONData", "error two = " + e.getMessage().toString());
            }
        } else {
            Log.i("getJSONData", "userDataString two = " + userDataString.toString());
        }*/

    }
    private void setData() {
        tvNameTv.setText(pName);
        tvDesignation1Tv.setText(pDesignation1);
        tvDesignation2Tv.setText(pDesignation2);
        tvMobileNoTv.setText(pPhone);


        if (pProfileImg.equalsIgnoreCase("")){
            ivAddImg.setVisibility(View.GONE);

        }else {
            ivAddImg.setVisibility(VISIBLE);
            GlideDataBinding.bindImage(ivAddImg, pProfileImg);
        }
        if (pPartyImg.equalsIgnoreCase("")){
            ivAddImgParty.setVisibility(View.GONE);

        }else {
            ivAddImgParty.setVisibility(VISIBLE);
            GlideDataBinding.bindImage(ivAddImgParty, pPartyImg);
        }

        if (pLeaderImg1.equalsIgnoreCase("")){
            ivAddImgLeader1.setVisibility(View.GONE);
            ivAddImgLeader11.setVisibility(View.GONE);

        }else {
            ivAddImgLeader1.setVisibility(VISIBLE);
            ivAddImgLeader11.setVisibility(VISIBLE);
            GlideDataBinding.bindImage(ivAddImgLeader1, pLeaderImg1);
            GlideDataBinding.bindImage(ivAddImgLeader11, pLeaderImg1);
        }

        if (pLeaderImg2.equalsIgnoreCase("")){
            ivAddImgLeader2.setVisibility(View.GONE);
            ivAddImgLeader22.setVisibility(View.GONE);
        }else {
            ivAddImgLeader2.setVisibility(VISIBLE);
            ivAddImgLeader22.setVisibility(VISIBLE);
            GlideDataBinding.bindImage(ivAddImgLeader2, pLeaderImg2);
            GlideDataBinding.bindImage(ivAddImgLeader22, pLeaderImg2);
        }

        if (pLeaderImg3.equalsIgnoreCase("")){
            ivAddImgLeader3.setVisibility(View.GONE);
            ivAddImgLeader33.setVisibility(View.GONE);
        }else {
            ivAddImgLeader3.setVisibility(VISIBLE);
            ivAddImgLeader33.setVisibility(VISIBLE);
            GlideDataBinding.bindImage(ivAddImgLeader3, pLeaderImg3);
            GlideDataBinding.bindImage(ivAddImgLeader33, pLeaderImg3);
        }

        if (pLeaderImg4.equalsIgnoreCase("")){
            ivAddImgLeader4.setVisibility(View.GONE);
            ivAddImgLeader44.setVisibility(View.GONE);
        }else {
            ivAddImgLeader4.setVisibility(VISIBLE);
            ivAddImgLeader44.setVisibility(VISIBLE);
            GlideDataBinding.bindImage(ivAddImgLeader4, pLeaderImg4);
            GlideDataBinding.bindImage(ivAddImgLeader44, pLeaderImg4);
        }

        if (pLeaderImg5.equalsIgnoreCase("")){
            ivAddImgLeader5.setVisibility(View.GONE);
            ivAddImgLeader55.setVisibility(View.GONE);
        }else {
            ivAddImgLeader5.setVisibility(VISIBLE);
            ivAddImgLeader55.setVisibility(VISIBLE);
            GlideDataBinding.bindImage(ivAddImgLeader5, pLeaderImg5);
            GlideDataBinding.bindImage(ivAddImgLeader55, pLeaderImg5);
        }

        if (pLeaderImg6.equalsIgnoreCase("")){
            ivAddImgLeader6.setVisibility(View.GONE);
            ivAddImgLeader66.setVisibility(View.GONE);
        }else {
            ivAddImgLeader6.setVisibility(VISIBLE);
            ivAddImgLeader66.setVisibility(VISIBLE);
            GlideDataBinding.bindImage(ivAddImgLeader6, pLeaderImg6);
            GlideDataBinding.bindImage(ivAddImgLeader66, pLeaderImg6);
        }







    }
    private boolean isMirrored = false;
    private void onShowHide() {
        ivFlipIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipImage(ivAddImg);
            }
        });
        tvProfilePhotoShowTv.setText("Hide");
        visibleProfilePhotoEyeIconIv.setVisibility(View.GONE);
        hideProfilePhotoEyeIconIv.setVisibility(View.VISIBLE);
        profilePhotoShowLLll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pProfileImg.equalsIgnoreCase("")){
                    ivAddImg.setVisibility(View.GONE);

                }else {
                    if (checkProfilePhoto) {
                        ivAddImg.setVisibility(View.GONE);

                        tvProfilePhotoShowTv.setText("Show");
                        visibleProfilePhotoEyeIconIv.setVisibility(View.VISIBLE);
                        hideProfilePhotoEyeIconIv.setVisibility(View.GONE);
                        checkProfilePhoto = false;
                    } else {
                        ivAddImg.setVisibility(View.VISIBLE);

                        tvProfilePhotoShowTv.setText("Hide");
                        visibleProfilePhotoEyeIconIv.setVisibility(View.GONE);
                        hideProfilePhotoEyeIconIv.setVisibility(View.VISIBLE);
                        checkProfilePhoto = true;
                    }
                }

            }
        });


        tvPartyPhotoShowTv.setText("Hide");
        visiblePartyPhotoEyeIconIv.setVisibility(View.GONE);
        hidePartyPhotoEyeIconIv.setVisibility(View.VISIBLE);
        partyPhotoShowLLll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pPartyImg.equalsIgnoreCase("")){
                    ivAddImgParty.setVisibility(View.GONE);

                }else {
                    if (checkPartyPhoto) {

                        ivAddImgParty.setVisibility(View.GONE);


                        tvPartyPhotoShowTv.setText("Show");
                        visiblePartyPhotoEyeIconIv.setVisibility(View.VISIBLE);
                        hidePartyPhotoEyeIconIv.setVisibility(View.GONE);
                        checkPartyPhoto = false;
                    } else {
                        ivAddImgParty.setVisibility(View.VISIBLE);


                        tvPartyPhotoShowTv.setText("Hide");
                        visiblePartyPhotoEyeIconIv.setVisibility(View.GONE);
                        hidePartyPhotoEyeIconIv.setVisibility(View.VISIBLE);
                        checkPartyPhoto = true;
                    }
                }


            }
        });


        tvNameShowTv.setText("Hide");
        visibleNameKyeIconIv.setVisibility(View.GONE);
        hideNameEyeIconIv.setVisibility(View.VISIBLE);
        nameShowLLll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkName) {

                    tvNameTv.setVisibility(View.GONE);


                    tvNameShowTv.setText("Show");
                    visibleNameKyeIconIv.setVisibility(View.VISIBLE);
                    hideNameEyeIconIv.setVisibility(View.GONE);
                    checkName = false;
                } else {
                    tvNameTv.setVisibility(View.VISIBLE);


                    tvNameShowTv.setText("Hide");
                    visibleNameKyeIconIv.setVisibility(View.GONE);
                    hideNameEyeIconIv.setVisibility(View.VISIBLE);
                    checkName = true;
                }
            }
        });

        ivbtnBoldFontName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Check if the TextView has a non-null Typeface
                if (tvNameTv.getTypeface() != null) {
                    // Check if the current Typeface is bold
                    if ((tvNameTv.getTypeface().getStyle() & Typeface.BOLD) != 0) {
                        // If bold, set to normal
                        tvNameTv.setTypeface(null, Typeface.NORMAL);
                    } else {
                        // If not bold, set to bold
                        tvNameTv.setTypeface(null, Typeface.BOLD);
                    }
                } else {
                    tvNameTv.setTypeface(null, Typeface.BOLD);
                    // Handle the case where the Typeface is null
                    // You may want to set a default Typeface in this case
                }
            }
        });

        ivbtnItalicFontName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (tvNameTv.getTypeface() != null) {
                    // Check if the current Typeface is italic
                    if (tvNameTv.getTypeface().isItalic()) {
                        // If italic, set to normal
                        tvNameTv.setTypeface(null, Typeface.NORMAL);
                    } else {
                        // If not italic, set to italic
                        tvNameTv.setTypeface(null, Typeface.ITALIC);
                    }
                } else {
                    tvNameTv.setTypeface(null, Typeface.ITALIC);
                    // Handle the case where the Typeface is null
                    // You may want to set a default Typeface in this case
                }
            }
        });

        ivbtnUnderlineFontName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((tvNameTv.getPaintFlags() & Paint.UNDERLINE_TEXT_FLAG) != 0) {
                    // If underlined, remove underline
                    tvNameTv.setPaintFlags(tvNameTv.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                } else {
                    // If not underlined, add underline
                    tvNameTv.setPaintFlags(tvNameTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                }
            }
        });


        tvDesignation1ShowTv.setText("Hide");
        visibleDesignation1KyeIconIv.setVisibility(View.GONE);
        hideDesignation1EyeIconIv.setVisibility(View.VISIBLE);
        designation1ShowLLll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDesignation1) {

                    tvDesignation1Tv.setVisibility(View.GONE);


                    tvDesignation1ShowTv.setText("Show");
                    visibleDesignation1KyeIconIv.setVisibility(View.VISIBLE);
                    hideDesignation1EyeIconIv.setVisibility(View.GONE);
                    checkDesignation1 = false;
                } else {
                    tvDesignation1Tv.setVisibility(View.VISIBLE);


                    tvDesignation1ShowTv.setText("Hide");
                    visibleDesignation1KyeIconIv.setVisibility(View.GONE);
                    hideDesignation1EyeIconIv.setVisibility(View.VISIBLE);
                    checkDesignation1 = true;
                }
            }
        });

        ivbtnBoldFontDesignation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Check if the TextView has a non-null Typeface
                if (tvDesignation1Tv.getTypeface() != null) {
                    // Check if the current Typeface is bold
                    if ((tvDesignation1Tv.getTypeface().getStyle() & Typeface.BOLD) != 0) {
                        // If bold, set to normal
                        tvDesignation1Tv.setTypeface(null, Typeface.NORMAL);
                    } else {
                        // If not bold, set to bold
                        tvDesignation1Tv.setTypeface(null, Typeface.BOLD);
                    }
                } else {
                    tvDesignation1Tv.setTypeface(null, Typeface.BOLD);
                    // Handle the case where the Typeface is null
                    // You may want to set a default Typeface in this case
                }
            }
        });

        ivbtnItalicFontDesignation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (tvDesignation1Tv.getTypeface() != null) {
                    // Check if the current Typeface is italic
                    if (tvDesignation1Tv.getTypeface().isItalic()) {
                        // If italic, set to normal
                        tvDesignation1Tv.setTypeface(null, Typeface.NORMAL);
                    } else {
                        // If not italic, set to italic
                        tvDesignation1Tv.setTypeface(null, Typeface.ITALIC);
                    }
                } else {
                    tvDesignation1Tv.setTypeface(null, Typeface.ITALIC);
                    // Handle the case where the Typeface is null
                    // You may want to set a default Typeface in this case
                }
            }
        });

        ivbtnUnderlineFontDesignation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((tvDesignation1Tv.getPaintFlags() & Paint.UNDERLINE_TEXT_FLAG) != 0) {
                    // If underlined, remove underline
                    tvDesignation1Tv.setPaintFlags(tvDesignation1Tv.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                } else {
                    // If not underlined, add underline
                    tvDesignation1Tv.setPaintFlags(tvDesignation1Tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                }
            }
        });


        tvDesignation2ShowTv.setText("Hide");
        visibleDesignation2KyeIconIv.setVisibility(View.GONE);
        hideDesignation2EyeIconIv.setVisibility(View.VISIBLE);
        designation2ShowLLll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDesignation2) {

                    tvDesignation2Tv.setVisibility(View.GONE);


                    tvDesignation2ShowTv.setText("Show");
                    visibleDesignation2KyeIconIv.setVisibility(View.VISIBLE);
                    hideDesignation2EyeIconIv.setVisibility(View.GONE);
                    checkDesignation2 = false;
                } else {
                    tvDesignation2Tv.setVisibility(View.VISIBLE);


                    tvDesignation2ShowTv.setText("Hide");
                    visibleDesignation2KyeIconIv.setVisibility(View.GONE);
                    hideDesignation2EyeIconIv.setVisibility(View.VISIBLE);
                    checkDesignation2 = true;
                }
            }
        });

        ivbtnBoldFontDesignation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Check if the TextView has a non-null Typeface
                if (tvDesignation2Tv.getTypeface() != null) {
                    // Check if the current Typeface is bold
                    if ((tvDesignation2Tv.getTypeface().getStyle() & Typeface.BOLD) != 0) {
                        // If bold, set to normal
                        tvDesignation2Tv.setTypeface(null, Typeface.NORMAL);
                    } else {
                        // If not bold, set to bold
                        tvDesignation2Tv.setTypeface(null, Typeface.BOLD);
                    }
                } else {
                    tvDesignation2Tv.setTypeface(null, Typeface.BOLD);
                    // Handle the case where the Typeface is null
                    // You may want to set a default Typeface in this case
                }
            }
        });

        ivbtnItalicFontDesignation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (tvDesignation2Tv.getTypeface() != null) {
                    // Check if the current Typeface is italic
                    if (tvDesignation2Tv.getTypeface().isItalic()) {
                        // If italic, set to normal
                        tvDesignation2Tv.setTypeface(null, Typeface.NORMAL);
                    } else {
                        // If not italic, set to italic
                        tvDesignation2Tv.setTypeface(null, Typeface.ITALIC);
                    }
                } else {
                    tvDesignation2Tv.setTypeface(null, Typeface.ITALIC);
                    // Handle the case where the Typeface is null
                    // You may want to set a default Typeface in this case
                }
            }
        });

        ivbtnUnderlineFontDesignation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((tvDesignation2Tv.getPaintFlags() & Paint.UNDERLINE_TEXT_FLAG) != 0) {
                    // If underlined, remove underline
                    tvDesignation2Tv.setPaintFlags(tvDesignation2Tv.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                } else {
                    // If not underlined, add underline
                    tvDesignation2Tv.setPaintFlags(tvDesignation2Tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                }
            }
        });


        //tvMobileNoTv

        tvMobileShowTv.setText("Hide");
        visibleMobileKyeIconIv.setVisibility(View.GONE);
        hideMobileEyeIconIv.setVisibility(View.VISIBLE);
        mobileShowLLll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMobile) {

                    tvMobileNoTv.setVisibility(View.GONE);


                    tvMobileShowTv.setText("Show");
                    visibleMobileKyeIconIv.setVisibility(View.VISIBLE);
                    hideMobileEyeIconIv.setVisibility(View.GONE);
                    checkMobile = false;
                } else {
                    tvMobileNoTv.setVisibility(View.VISIBLE);


                    tvMobileShowTv.setText("Hide");
                    visibleMobileKyeIconIv.setVisibility(View.GONE);
                    hideMobileEyeIconIv.setVisibility(View.VISIBLE);
                    checkMobile = true;
                }
            }
        });

        ivbtnBoldFontMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Check if the TextView has a non-null Typeface
                if (tvMobileNoTv.getTypeface() != null) {
                    // Check if the current Typeface is bold
                    if ((tvMobileNoTv.getTypeface().getStyle() & Typeface.BOLD) != 0) {
                        // If bold, set to normal
                        tvMobileNoTv.setTypeface(null, Typeface.NORMAL);
                    } else {
                        // If not bold, set to bold
                        tvMobileNoTv.setTypeface(null, Typeface.BOLD);
                    }
                } else {
                    tvMobileNoTv.setTypeface(null, Typeface.BOLD);
                    // Handle the case where the Typeface is null
                    // You may want to set a default Typeface in this case
                }
            }
        });

        ivbtnItalicFontMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (tvMobileNoTv.getTypeface() != null) {
                    // Check if the current Typeface is italic
                    if (tvMobileNoTv.getTypeface().isItalic()) {
                        // If italic, set to normal
                        tvMobileNoTv.setTypeface(null, Typeface.NORMAL);
                    } else {
                        // If not italic, set to italic
                        tvMobileNoTv.setTypeface(null, Typeface.ITALIC);
                    }
                } else {
                    tvMobileNoTv.setTypeface(null, Typeface.ITALIC);
                    // Handle the case where the Typeface is null
                    // You may want to set a default Typeface in this case
                }
            }
        });

        ivbtnUnderlineFontMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((tvMobileNoTv.getPaintFlags() & Paint.UNDERLINE_TEXT_FLAG) != 0) {
                    // If underlined, remove underline
                    tvMobileNoTv.setPaintFlags(tvMobileNoTv.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                } else {
                    // If not underlined, add underline
                    tvMobileNoTv.setPaintFlags(tvMobileNoTv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                }
            }
        });


        tvSocialMediaShowTv.setText("Hide");
        visibleSocialMediaKyeIconIv.setVisibility(View.GONE);
        hideSocialMediaEyeIconIv.setVisibility(View.VISIBLE);
        socialMediaShowLLll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("checkdatamethdo","step 1");
                if (checkSocialMedia) {
                    Log.i("checkdatamethdo","step 2");
                    ivSocialMediaIv.setVisibility(View.GONE);


                    tvSocialMediaShowTv.setText("Show");
                    visibleSocialMediaKyeIconIv.setVisibility(View.VISIBLE);
                    hideSocialMediaEyeIconIv.setVisibility(View.GONE);
                    checkSocialMedia = false;
                } else {
                    ivSocialMediaIv.setVisibility(View.VISIBLE);
                    Log.i("checkdatamethdo","step 3");

                    tvSocialMediaShowTv.setText("Hide");
                    visibleSocialMediaKyeIconIv.setVisibility(View.GONE);
                    hideSocialMediaEyeIconIv.setVisibility(View.VISIBLE);
                    checkSocialMedia = true;
                }
            }
        });


        ivFrames00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPoliticalFrame0();
                ivFrames00.setBackground(getDrawable(R.drawable.images_background));
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
            }
        });

        ivFrames11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPoliticalFrame1();
                ivFrames00.setBackground(null);
                ivFrames11.setBackground(getDrawable(R.drawable.images_background));
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
            }
        });


        ivFrames22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
                switchToPoliticalFrame2();
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(getDrawable(R.drawable.images_background));
                ivFrames33.setBackground(null);
            }
        });


        ivFrames33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
                switchToPoliticalFrame3();
                ivFrames22.setBackground(null);
                ivFrames11.setBackground(null);
                ivFrames33.setBackground(getDrawable(R.drawable.images_background));
            }
        });

        ivAddImgLeader11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivAddImgLeader1.getVisibility() == View.VISIBLE) {
                    ivAddImgLeader1.setVisibility(View.GONE);
                    ivAddImgLeader11.setBackground(null);
                } else {
                    ivAddImgLeader1.setVisibility(View.VISIBLE);
                    ivAddImgLeader11.setBackground(getDrawable(R.drawable.images_background));
                }
            }
        });

        ivAddImgLeader22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivAddImgLeader2.getVisibility() == View.VISIBLE) {
                    ivAddImgLeader2.setVisibility(View.GONE);
                    ivAddImgLeader22.setBackground(null);
                } else {
                    ivAddImgLeader2.setVisibility(View.VISIBLE);
                    ivAddImgLeader22.setBackground(getDrawable(R.drawable.images_background));
                }
            }
        });

        ivAddImgLeader33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivAddImgLeader3.getVisibility() == View.VISIBLE) {
                    ivAddImgLeader3.setVisibility(View.GONE);
                    ivAddImgLeader33.setBackground(null);
                } else {
                    ivAddImgLeader3.setVisibility(View.VISIBLE);
                    ivAddImgLeader33.setBackground(getDrawable(R.drawable.images_background));
                }
            }
        });

        ivAddImgLeader44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivAddImgLeader4.getVisibility() == View.VISIBLE) {
                    ivAddImgLeader4.setVisibility(View.GONE);
                    ivAddImgLeader44.setBackground(null);
                } else {
                    ivAddImgLeader4.setVisibility(View.VISIBLE);
                    ivAddImgLeader44.setBackground(getDrawable(R.drawable.images_background));
                }
            }
        });

        ivAddImgLeader55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivAddImgLeader5.getVisibility() == View.VISIBLE) {
                    ivAddImgLeader5.setVisibility(View.GONE);
                    ivAddImgLeader55.setBackground(null);
                } else {
                    ivAddImgLeader5.setVisibility(View.VISIBLE);
                    ivAddImgLeader55.setBackground(getDrawable(R.drawable.images_background));
                }
            }
        });
        ivAddImgLeader66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivAddImgLeader6.getVisibility() == View.VISIBLE) {
                    ivAddImgLeader6.setVisibility(View.GONE);
                    ivAddImgLeader66.setBackground(null);
                } else {
                    ivAddImgLeader6.setVisibility(View.VISIBLE);
                    ivAddImgLeader66.setBackground(getDrawable(R.drawable.images_background));
                }
            }
        });


    }
    private void onClick() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        llcolorMobilell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPickerDialog(tvMobileNoTv);
            }
        });
        llcolord2ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPickerDialog(tvDesignation2Tv);
            }
        });
        llcolord1ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPickerDialog(tvDesignation1Tv);
            }
        });
        llcolorll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPickerDialog(tvNameTv);
            }
        });

        llfontll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   showFontFamilyBottomSheet(tvNameTv);

                adapter = new FontAdapter(EditPersonalProfileDetailsActivity.this, getResources().getStringArray(R.array.fonts_array));
                adapter.setSelected(0);
                ((GridView) findViewById(R.id.font_gridview_name)).setAdapter(adapter);

                adapter.setItemClickCallback((OnClickCallback<ArrayList<String>, Integer, String, Activity>) (arrayList, num, str, activity) -> {

                    // Apply the selected font family to the TextView
                    setTextFont(str, tvNameTv);
                    adapter.setSelected(num.intValue());
                });
            }
        });
        llfontd1ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showFontFamilyBottomSheet(tvDesignation1Tv);

                adapter = new FontAdapter(EditPersonalProfileDetailsActivity.this, getResources().getStringArray(R.array.fonts_array));
                adapter.setSelected(0);
                ((GridView) findViewById(R.id.font_gridview_d1)).setAdapter(adapter);

                adapter.setItemClickCallback((OnClickCallback<ArrayList<String>, Integer, String, Activity>) (arrayList, num, str, activity) -> {

                    // Apply the selected font family to the TextView
                    setTextFont(str, tvDesignation1Tv);
                    adapter.setSelected(num.intValue());
                });
            }
        });
        llfontd2ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showFontFamilyBottomSheet(tvDesignation2Tv);

                adapter = new FontAdapter(EditPersonalProfileDetailsActivity.this, getResources().getStringArray(R.array.fonts_array));
                adapter.setSelected(0);
                ((GridView) findViewById(R.id.font_gridview_d2)).setAdapter(adapter);

                adapter.setItemClickCallback((OnClickCallback<ArrayList<String>, Integer, String, Activity>) (arrayList, num, str, activity) -> {

                    // Apply the selected font family to the TextView
                    setTextFont(str, tvDesignation2Tv);
                    adapter.setSelected(num.intValue());
                });
            }
        });
        llfontMobilell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showFontFamilyBottomSheet(tvMobileNoTv);

                adapter = new FontAdapter(EditPersonalProfileDetailsActivity.this, getResources().getStringArray(R.array.fonts_array));
                adapter.setSelected(0);
                ((GridView) findViewById(R.id.font_gridview_mobile)).setAdapter(adapter);

                adapter.setItemClickCallback((OnClickCallback<ArrayList<String>, Integer, String, Activity>) (arrayList, num, str, activity) -> {

                    // Apply the selected font family to the TextView
                    setTextFont(str, tvMobileNoTv);
                    adapter.setSelected(num.intValue());
                });
            }
        });
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage(viewToBitmap(constraint), true);
            }
        });

        llFramesLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(VISIBLE);
            }
        });
        llProfilePhotoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.VISIBLE);
                lay_name_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
            }
        });
        llNameLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.VISIBLE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
            }
        });
        llDesignation1Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.VISIBLE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
            }
        });
        llDesignation2Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.VISIBLE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
            }
        });
        llMobileLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.VISIBLE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
            }
        });

        llLeadersPhotoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.VISIBLE);
                lay_frames_ll.setVisibility(View.GONE);
            }
        });
        llSocialMediaIconsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.VISIBLE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
            }
        });

        llPartyIconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.VISIBLE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
            }
        });
    }
    public void setTextFont(String str, TextView autoResizeTextView) {
        try {
            if (str.equals(Bus.DEFAULT_IDENTIFIER)) {
                autoResizeTextView.setTypeface(Typeface.createFromAsset(this.getAssets(), "font/Default.ttf"));

                return;
            }

            String str1 = str;


            File file1 = new File(Configure.GetFileDir(context).getPath() + File.separator + "font");


            if (str1.contains(".ttf") || str1.contains(".otf")) {


                autoResizeTextView.setTypeface(Typeface.createFromFile(file1.getAbsolutePath() + "/" + str1));


            } else {

                str1 = str + ".otf";

                if (new File(file1.getAbsolutePath(), str1).exists()) {

                    autoResizeTextView.setTypeface(Typeface.createFromFile(file1.getAbsolutePath() + "/" + str1));

                } else {
                    str1 = str + ".ttf";

                    autoResizeTextView.setTypeface(Typeface.createFromFile(file1.getAbsolutePath() + "/" + str1));

                }


            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    SeekBar.OnSeekBarChangeListener seekBarChangeListenerProfilePhoto = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            // Update the width and height of the ImageView based on SeekBar progress
            int newWidth = 200 + progress * 10; // Adjust as needed
            int newHeight = 200 + progress * 10; // Adjust as needed


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);

            ivAddImg.setLayoutParams(params);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    SeekBar.OnSeekBarChangeListener seekBarChangeListenerPartyPhoto = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            // Update the width and height of the ImageView based on SeekBar progress
            int newWidth = 200 + progress * 10; // Adjust as needed
            int newHeight = 200 + progress * 10; // Adjust as needed


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(newWidth, newHeight);

            ivAddImgParty.setLayoutParams(params);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    SeekBar.OnSeekBarChangeListener seekBarChangeListenerName = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            // Update the width and height of the ImageView based on SeekBar progress

            float newTextSize = 16 + progress;

            tvNameTv.setTextSize(newTextSize);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    SeekBar.OnSeekBarChangeListener seekBarChangeListenerDesignation1 = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            // Update the width and height of the ImageView based on SeekBar progress

            float newTextSize = 10 + progress;

            tvDesignation1Tv.setTextSize(newTextSize);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    SeekBar.OnSeekBarChangeListener seekBarChangeListenerDesignation2 = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            // Update the width and height of the ImageView based on SeekBar progress

            float newTextSize = 10 + progress;

            tvDesignation2Tv.setTextSize(newTextSize);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    SeekBar.OnSeekBarChangeListener seekBarChangeListenerMobile = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            // Update the width and height of the ImageView based on SeekBar progress

            float newTextSize = 10 + progress;

            tvMobileNoTv.setTextSize(newTextSize);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    private void flipImage(ImageView imageView) {
        if (!isMirrored) {
            // Flip the image horizontally
            imageView.setScaleX(-1f);
        } else {
            // Reset the image to its original state
            imageView.setScaleX(1f);
        }// Flip the image horizontally
        // Toggle the mirror state
        isMirrored = !isMirrored;
       /* ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, View.ROTATION_Y, 0f, 180f);

        // Set the duration of the animation in milliseconds
        objectAnimator.setDuration(1000);

        // Reverse the animation when it ends to provide a smooth transition
        objectAnimator.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) {}

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                if (!isMirrored) {
                    // Flip the image horizontally
                    imageView.setScaleX(-1f);
                } else {
                    // Reset the image to its original state
                    imageView.setScaleX(1f);
                }// Flip the image horizontally
                // Toggle the mirror state
                isMirrored = !isMirrored;
                *//*objectAnimator.removeAllListeners();
                objectAnimator.reverse();*//*
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animation) {}

            @Override
            public void onAnimationRepeat(android.animation.Animator animation) {}
        });

        // Start the animation
        objectAnimator.start();*/
    }
    private void switchToPoliticalFrame0() {

        // Assuming you have a reference to the parent ViewGroup
        ViewGroup parentLayout  = findViewById(R.id.parent_layout);

        // Remove the current toolbar from the parent layout
        View currentToolbar = findViewById(R.id.toolbar);

        parentLayout.removeView(currentToolbar);

        // Inflate the new layout (political_frame_2)
        View newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_1, parentLayout, false);


        // Add the new toolbar to the parent layout
        // Set the ID for the new toolbar
        newToolbar.setId(R.id.toolbar);
        parentLayout.addView(newToolbar);


        initView(newToolbar);
        getDataShare();
    }
    private void switchToPoliticalFrame1() {
        // Assuming you have a reference to the parent ViewGroup
        ViewGroup parentLayout  = findViewById(R.id.parent_layout);

        // Remove the current toolbar from the parent layout
        View currentToolbar = findViewById(R.id.toolbar);
        parentLayout.removeView(currentToolbar);

        // Inflate the new layout (political_frame_2)
        View newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_2, parentLayout, false);


        // Add the new toolbar to the parent layout
        // Set the ID for the new toolbar
        newToolbar.setId(R.id.toolbar);
        parentLayout.addView(newToolbar);


        initView(newToolbar);
        getDataShare();
    }
    private void switchToPoliticalFrame2() {
        // Assuming you have a reference to the parent ViewGroup
        ViewGroup parentLayout  = findViewById(R.id.parent_layout);

        // Remove the current toolbar from the parent layout
        View currentToolbar = findViewById(R.id.toolbar);
        parentLayout.removeView(currentToolbar);

        // Inflate the new layout (political_frame_2)
        View newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_3, parentLayout, false);


        // Add the new toolbar to the parent layout
        // Set the ID for the new toolbar
        newToolbar.setId(R.id.toolbar);
        parentLayout.addView(newToolbar);


        initView(newToolbar);
        getDataShare();
    }
    private void switchToPoliticalFrame3() {
        // Assuming you have a reference to the parent ViewGroup
        ViewGroup parentLayout  = findViewById(R.id.parent_layout);

        // Remove the current toolbar from the parent layout
        View currentToolbar = findViewById(R.id.toolbar);
        parentLayout.removeView(currentToolbar);

        // Inflate the new layout (political_frame_2)
        View newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_4, parentLayout, false);


        // Add the new toolbar to the parent layout
        // Set the ID for the new toolbar
        newToolbar.setId(R.id.toolbar);
        parentLayout.addView(newToolbar);


        initView(newToolbar);
        getDataShare();
    }
    private void initView(View newToolbar) {
        tvNameTv = (TextView) newToolbar.findViewById(R.id.tvName);

        tvDesignation1Tv = (TextView) newToolbar.findViewById(R.id.tvDesignation1);
        tvDesignation2Tv = (TextView) newToolbar.findViewById(R.id.tvDesignation2);
        tvMobileNoTv = (TextView) newToolbar.findViewById(R.id.tvMobileNo);

        ivAddImg = (ImageView) newToolbar.findViewById(R.id.iv_logo);
        ivAddImg.setVisibility(View.GONE);
        ivAddImgParty = (ImageView) newToolbar.findViewById(R.id.iv_party);
        ivAddImgParty.setVisibility(View.VISIBLE);
        ivAddImgLeader1 = (ImageView) newToolbar.findViewById(R.id.iv_logoL1);
        ivAddImgLeader2 = (ImageView) newToolbar.findViewById(R.id.iv_logoL2);
        ivAddImgLeader3 = (ImageView) newToolbar.findViewById(R.id.iv_logoL3);
        ivAddImgLeader4 = (ImageView) newToolbar.findViewById(R.id.iv_logoL4);
        ivAddImgLeader5 = (ImageView) newToolbar.findViewById(R.id.iv_logoL5);
        ivAddImgLeader6 = (ImageView) newToolbar.findViewById(R.id.iv_logoL6);
        ivSocialMediaIv = (ImageView) newToolbar.findViewById(R.id.socialMediaIv);
        ivSocialMediaIv.setVisibility(View.GONE);
    }
    RelativeLayout ivBack;
    private void init() {
        fm = (LinearLayout) findViewById(R.id.linearLogoL);
        fm.setVisibility(View.GONE);
        ivBack = (RelativeLayout)findViewById(R.id.btn_bckprass);
        preferenceManager = new PreferenceManager(this);
        universalDialog = new UniversalDialog(this, false);
        btnDownload = (LinearLayout) findViewById(R.id.ll_save);

        ivFlipIv = (ImageView)findViewById(R.id.flipIv);

        llcolorMobilell = (LinearLayout) findViewById(R.id.llcolorMobile);
        llcolord2ll = (LinearLayout) findViewById(R.id.llcolord2);
        llcolord1ll = (LinearLayout) findViewById(R.id.llcolord1);
        llcolorll = (LinearLayout) findViewById(R.id.llcolor);

        llfontll = (LinearLayout) findViewById(R.id.llfont);
        llfontd1ll = (LinearLayout) findViewById(R.id.llfontd1);
        llfontd2ll = (LinearLayout) findViewById(R.id.llfontd2);
        llfontMobilell = (LinearLayout) findViewById(R.id.llfontMobile);

        btnseekBarProfilePhoto = (SeekBar) findViewById(R.id.seekBarProfilePhoto);
        visibleProfilePhotoEyeIconIv = (ImageView) findViewById(R.id.visibleProfilePhotoEyeIcon);
        hideProfilePhotoEyeIconIv = (ImageView) findViewById(R.id.hideProfilePhotoEyeIcon);
        tvProfilePhotoShowTv = (TextView) findViewById(R.id.profilePhotoShowtv);
        profilePhotoShowLLll = (LinearLayout) findViewById(R.id.profilePhotoShowLL);
        btnseekBarProfilePhoto.setOnSeekBarChangeListener(seekBarChangeListenerProfilePhoto);


        btnseekBarPartyPhoto = (SeekBar) findViewById(R.id.seekBarPartyPhoto);
        visiblePartyPhotoEyeIconIv = (ImageView) findViewById(R.id.visiblePartyPhotoEyeIcon);
        hidePartyPhotoEyeIconIv = (ImageView) findViewById(R.id.hidePartyPhotoEyeIcon);
        tvPartyPhotoShowTv = (TextView) findViewById(R.id.partyPhotoShowtv);
        partyPhotoShowLLll = (LinearLayout) findViewById(R.id.parthPhotoShowLL);
        btnseekBarPartyPhoto.setOnSeekBarChangeListener(seekBarChangeListenerPartyPhoto);


        btnseekBarName = (SeekBar) findViewById(R.id.seekBarName);
        visibleNameKyeIconIv = (ImageView) findViewById(R.id.visibleNameEyeIcon);
        hideNameEyeIconIv = (ImageView) findViewById(R.id.hideNameEyeIcon);
        tvNameShowTv = (TextView) findViewById(R.id.nameShowtv);
        nameShowLLll = (LinearLayout) findViewById(R.id.nameShowLL);
        btnseekBarName.setOnSeekBarChangeListener(seekBarChangeListenerName);
        ivbtnBoldFontName = (ImageView) findViewById(R.id.btnBoldFontName);
        ivbtnItalicFontName = (ImageView) findViewById(R.id.btnItalicFontName);
        ivbtnUnderlineFontName = (ImageView) findViewById(R.id.btnUnderlineFontName);


        btnseekBarDesignation1 = (SeekBar) findViewById(R.id.seekBarDesignation1);
        visibleDesignation1KyeIconIv = (ImageView) findViewById(R.id.visibleDesignation1EyeIcon);
        hideDesignation1EyeIconIv = (ImageView) findViewById(R.id.hideDesignation1EyeIcon);
        tvDesignation1ShowTv = (TextView) findViewById(R.id.designation1Showtv);
        designation1ShowLLll = (LinearLayout) findViewById(R.id.designation1ShowLL);
        btnseekBarDesignation1.setOnSeekBarChangeListener(seekBarChangeListenerDesignation1);
        ivbtnBoldFontDesignation1 = (ImageView) findViewById(R.id.btnBoldFontDesignation1);
        ivbtnItalicFontDesignation1 = (ImageView) findViewById(R.id.btnItalicFontDesignation1);
        ivbtnUnderlineFontDesignation1 = (ImageView) findViewById(R.id.btnUnderlineFontDesignation1);
        lay_Designation1_ll = (LinearLayout) findViewById(R.id.lay_Designation1);


        btnseekBarDesignation2 = (SeekBar) findViewById(R.id.seekBarDesignation2);
        visibleDesignation2KyeIconIv = (ImageView) findViewById(R.id.visibleDesignation2EyeIcon);
        hideDesignation2EyeIconIv = (ImageView) findViewById(R.id.hideDesignation2EyeIcon);
        tvDesignation2ShowTv = (TextView) findViewById(R.id.designation2Showtv);
        designation2ShowLLll = (LinearLayout) findViewById(R.id.designation2ShowLL);
        btnseekBarDesignation2.setOnSeekBarChangeListener(seekBarChangeListenerDesignation2);
        ivbtnBoldFontDesignation2 = (ImageView) findViewById(R.id.btnBoldFontDesignation2);
        ivbtnItalicFontDesignation2 = (ImageView) findViewById(R.id.btnItalicFontDesignation2);
        ivbtnUnderlineFontDesignation2 = (ImageView) findViewById(R.id.btnUnderlineFontDesignation2);
        lay_Designation2_ll = (LinearLayout) findViewById(R.id.lay_Designation2);


        btnseekBarMobile = (SeekBar) findViewById(R.id.seekBarMobile);
        visibleMobileKyeIconIv = (ImageView) findViewById(R.id.visibleMobileNumberEyeIcon);
        hideMobileEyeIconIv = (ImageView) findViewById(R.id.hideMobileNumberEyeIcon);
        tvMobileShowTv = (TextView) findViewById(R.id.mobileShowtv);
        mobileShowLLll = (LinearLayout) findViewById(R.id.mobileShowLL);
        btnseekBarMobile.setOnSeekBarChangeListener(seekBarChangeListenerMobile);
        ivbtnBoldFontMobile = (ImageView) findViewById(R.id.btnBoldFontMobile);
        ivbtnItalicFontMobile = (ImageView) findViewById(R.id.btnItalicFontMobile);
        ivbtnUnderlineFontMobile = (ImageView) findViewById(R.id.btnUnderlineFontMobile);
        lay_Mobile_ll = (LinearLayout) findViewById(R.id.lay_mobileNumber);


        lay_SocialMedia_ll = (LinearLayout) findViewById(R.id.lay_socialMedia);
        visibleSocialMediaKyeIconIv = (ImageView) findViewById(R.id.visibleSocialMediaEyeIcon);
        hideSocialMediaEyeIconIv = (ImageView) findViewById(R.id.hideSocialMediaEyeIcon);
        tvSocialMediaShowTv = (TextView) findViewById(R.id.socialMediaShowtv);
        socialMediaShowLLll = (LinearLayout) findViewById(R.id.socialMediaShowLL);


        lay_LeadersPhoto_ll = (LinearLayout) findViewById(R.id.lay_leadersPhoto);
        lay_frames_ll = (LinearLayout) findViewById(R.id.lay_frames);


        llFramesLl = (LinearLayout) findViewById(R.id.framesLl);
        llProfilePhotoLl = (LinearLayout) findViewById(R.id.profilePhotoLl);
        llNameLl = (LinearLayout) findViewById(R.id.nameLl);
        llDesignation1Ll = (LinearLayout) findViewById(R.id.designation1Ll);
        llDesignation2Ll = (LinearLayout) findViewById(R.id.designation2Ll);
        llMobileLl = (LinearLayout) findViewById(R.id.mobileLl);
        llLeadersPhotoLl = (LinearLayout) findViewById(R.id.leadersPhotoLl);
        llSocialMediaIconsLl = (LinearLayout) findViewById(R.id.socialMediaIconsLl);
        llPartyIconLayout = (LinearLayout) findViewById(R.id.partyIconLayout);
        //lay_profile_photo
        lay_profile_photo_ll = (LinearLayout) findViewById(R.id.lay_profile_photo);
        lay_name_ll = (LinearLayout) findViewById(R.id.lay_name);
        lay_party_photo_ll = (LinearLayout) findViewById(R.id.lay_party_photo);


        constraint = (RelativeLayout) findViewById(R.id.constraint);


        ivAddImgLeader11 = (ImageView) findViewById(R.id.iv_logoL11);
        ivAddImgLeader22 = (ImageView) findViewById(R.id.iv_logoL12);
        ivAddImgLeader33 = (ImageView) findViewById(R.id.iv_logoL13);
        ivAddImgLeader44 = (ImageView) findViewById(R.id.iv_logoL14);
        ivAddImgLeader55 = (ImageView) findViewById(R.id.iv_logoL15);
        ivAddImgLeader66 = (ImageView) findViewById(R.id.iv_logoL16);

        ivFrames00 = (ImageView) findViewById(R.id.iv_logoL101);
        ivFrames11 = (ImageView) findViewById(R.id.iv_logoL111);
        ivFrames22 = (ImageView) findViewById(R.id.iv_logoL121);
        ivFrames33 = (ImageView) findViewById(R.id.iv_logoL131);






        switchToPoliticalFrame0();


    }
    private void saveImage(Bitmap bitmap, boolean z) {

        universalDialog.showLoadingDialog(this, "Saving Post...");
        /*       String file_name = System.currentTimeMillis() + ".jpg";*/

     /*   File file = new File(Environment.getExternalStorageDirectory() + File.separator
                + Environment.DIRECTORY_PICTURES + File.separator + getString(R.string.app_name) + File.separator + file_name);*/


        File file = new File(MyUtils.getFolderPath(this, "Sanskruti"));
        String extension = z ? ".png" : ".jpg";
        String fileName = System.currentTimeMillis() + extension;
        String filePath = file.getPath() + File.separator + fileName;
//        String filePath = file.getAbsolutePath();
        Log.i("checkdatafilePath", "0 files path = " + filePath+" , path file = "+file.getPath());
//        this.filePath = filePath;


        if (isSaved(bitmap, filePath)) {

            openShareActivity(filePath);
            universalDialog.dissmissLoadingDialog();

        } else {
            MyUtils.showToast(this, getString(R.string.error));
        }

    }
    private boolean isSaved(Bitmap bitmap, String filePath) {


        boolean success;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);






            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            bitmap.recycle();
            fileOutputStream.flush();

            File file = new File(filePath);
            if (file.exists()) {
                Log.i("checkdatafilePath", "file exists files path = ");
             /*   MediaScannerConnection.scanFile(this, new String[]{filePath},
                        null, (path, uri) -> {

                    //    Log.i("checkdatafilePath", "1111 files path = " + path+" ,  uri = "+uri.getPath());

                        });*/



                // Use FileProvider to get a content URI for the file
                Uri contentUri;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    contentUri = FileProvider.getUriForFile(context, "com.sanskruti.volotek.provider", file);
                } else {
                    contentUri = Uri.fromFile(file);
                }

                // Send broadcast to scan the file
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
                context.sendBroadcast(mediaScanIntent);
            }else {
                Log.i("checkdatafilePath", "1111 else files path = ");
            }


            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }
    public void openShareActivity(String filePath) {

        Log.i("checkdatafilePath", "1 files path = " + filePath);
        universalDialog.dissmissLoadingDialog();

        MediaScannerConnection.scanFile(context, new String[]{filePath}, new String[]{filePath.contains("mp4") ? "video/mp4" : "image/png"}, null);

        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(filePath))));


        Log.i("checkdatafilePath","11 file Path = "+filePath);
        Toast.makeText(EditPersonalProfileDetailsActivity.this, "Download Successfully", Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(context, ShareImageActivity.class);
        intent.putExtra("uri", filePath);
        intent.putExtra("way", "Poster");
        startActivity(intent);*/
    }
    private Bitmap viewToBitmap(View view) {

        //  tvEdit.setVisibility(GONE);

        try {

            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);


            view.draw(new Canvas(createBitmap));
            return createBitmap;
        } finally {
            view.destroyDrawingCache();
        }
    }
}