package com.sanskruti.volotek.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.sanskruti.volotek.R;
import com.sanskruti.volotek.binding.GlideDataBinding;
import com.sanskruti.volotek.databinding.ActivityProfileEditBinding;
import com.sanskruti.volotek.model.UserItem;
import com.sanskruti.volotek.ui.dialog.UniversalDialog;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.FrameUtils;
import com.sanskruti.volotek.utils.ImageCropperFragment;
import com.sanskruti.volotek.utils.MyUtils;
import com.sanskruti.volotek.utils.NetworkConnectivity;
import com.sanskruti.volotek.utils.PreferenceManager;
import com.sanskruti.volotek.utils.Util;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropFragment;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ProfileEditActivity extends AppCompatActivity {

    ActivityProfileEditBinding binding;
    PreferenceManager preferenceManager;
    Uri imageUri;
    String profileImagePath;
    ProgressDialog prgDialog;
    UniversalDialog universalDialog;
    NetworkConnectivity networkConnectivity;

    UserItem userItem;

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
//                                        GlideDataBinding.bindImage(binding.ivAddImg, out);
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
                profileImagePath = cursor.getString(columnIndex);
                cursor.close();

                imageUri = selectedImage;

                beginCrop(imageUri);


            }
        }
    }


    private void beginCrop(Uri uri) {
        if (uri != null) {
            try {

                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), UUID.randomUUID().toString()));
                UCrop.Options options2 = new UCrop.Options();
                options2.setCompressionFormat(Bitmap.CompressFormat.PNG);
                options2.setFreeStyleCropEnabled(false);
                options2.withAspectRatio(1,1);

                UCrop.of(uri, destinationUri)
                        .withOptions(options2)
                        .start(this);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
//            Uri resultUri = UCrop.getOutput(data);
//            if (resultUri != null) {
//                profileImagePath = resultUri.getPath();
//                imageUri = resultUri;
//                GlideDataBinding.bindImage(binding.ivAddImg, resultUri.toString());
//            }
//        }
        if (requestCode == UCrop.REQUEST_CROP) {
            if (data != null) {


                new ImageCropperFragment(0, MyUtils.getPathFromURI(this, UCrop.getOutput(data)), (id, out) -> {
                    imageUri = Uri.parse(out);
                    profileImagePath = imageUri.toString();
                    GlideDataBinding.bindImage(binding.ivAddImg, imageUri.toString());
                }).show(getSupportFragmentManager(), "");
            }

        }
        else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            cropError.printStackTrace();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileEditBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);

        userItem = Constant.getUserItem(ProfileEditActivity.this);

        if (userItem != null) {
            binding.etName.setText(userItem.getUserName());
            binding.etEmail.setText(userItem.getEmail());
            binding.etPhone.setText(userItem.getPhone());
            binding.etDesignation.setText(userItem.getDesignation());

        }


        GlideDataBinding.bindImage(binding.ivAddImg, userItem.getUserImage());


        prgDialog = new ProgressDialog(this);
        networkConnectivity = new NetworkConnectivity(this);
        prgDialog.setMessage(getString(R.string.login_loading));
        prgDialog.setCancelable(false);

        universalDialog = new UniversalDialog(this, false);


        // Email & phone are unique values so we can't update them.


        if (userItem.getLogin_type() != null && userItem.getLogin_type().equals(Constant.GOOGLE)) {

            // Update phone & not the emails
            binding.etEmail.setEnabled(false);
            binding.etPhone.setEnabled(true);

        } else {

            // Update email & not the phone

            binding.etEmail.setEnabled(true);
            binding.etPhone.setEnabled(false);

        }


        setUpUi();

    }


    private void setUpUi() {
        Boolean isFromLogin = getIntent().getBooleanExtra("isNameNull",false);

        binding.toolbar.toolName.setText(getResources().getString(R.string.edit_profile));
        if(isFromLogin){
            binding.toolbar.toolName.setPadding(40,0,0,0);
         binding.toolbar.back.setVisibility(View.GONE);
        }
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());

        binding.btnSave.setOnClickListener(v -> {

            if (validate()) {

                if (!networkConnectivity.isConnected()) {
                    Util.showToast(this, getString(R.string.error_message__no_internet));
                    return;
                }
                prgDialog.show();

                String userName = binding.etName.getText().toString().trim();
                String userEmail = binding.etEmail.getText().toString().trim();
                String userPhone = binding.etPhone.getText().toString().trim();
                String userDesignation = binding.etDesignation.getText().toString().trim();

                Constant.getUserViewModel(this).profileUpdate(userItem.userId, profileImagePath, userName, userEmail, userPhone, userDesignation)
                        .observe(this, userItem -> {

                            if (userItem != null) {
                                MyUtils.showResponse(userItem);

                                prgDialog.dismiss();
                                preferenceManager.setString(Constant.USER_EMAIL, userItem.email);
                                preferenceManager.setString(Constant.USER_NAME, userItem.userName);
                                preferenceManager.setString(Constant.USER_IMAGE, userItem.userImage);
                                preferenceManager.setString(Constant.USER_PHONE, userItem.phone);
                                preferenceManager.setString(Constant.USER_DESIGNATION, userItem.designation);

                                MyUtils.saveLogoPath(this, userItem.getUserImage(), new FrameUtils.OnLogoDownloadListener() {
                                    @Override
                                    public void onLogoDownloaded(String logoPath) {

                                        preferenceManager.setString(Constant.USER_IMAGE_PATH, logoPath);
                                        Log.d("logssso", "logo profile : " + logoPath);

                                    }

                                    @Override
                                    public void onLogoDownloadError() {

                                    }
                                });

                                if(isFromLogin){
                                    Intent intent = new Intent(this,MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    finish();
                                }
                            }
                        });

            }


        });

        binding.ivAddImg.setOnClickListener(v -> {
            Log.i("Saqlain","Image click");
            Intent i = new Intent(
                    Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            someActivityResultLauncher.launch(i);

        });

    }


    private Boolean validate() {
        if (binding.etName.getText().toString().trim().isEmpty()) {
            binding.etName.setError(getResources().getString(R.string.hint_name));
            binding.etName.requestFocus();
            return false;
        }/* else if (binding.etEmail.getText().toString().trim().isEmpty() && userItem.getLogin_type().equals(Constant.PHONE)) {
            binding.etEmail.setError(getResources().getString(R.string.hint_email));
            binding.etEmail.requestFocus();
            return false;

        } else if (binding.etDesignation.getText().toString().isEmpty()) {
            binding.etDesignation.setError(getResources().getString(R.string.hint_designation));
            binding.etDesignation.requestFocus();
            return false;

        } else if (binding.etPhone.getText().toString().isEmpty() && userItem.getLogin_type().equals(Constant.GOOGLE)) {
            binding.etPhone.setError(getResources().getString(R.string.hint_phone_number));
            binding.etPhone.requestFocus();
            return false;

        }*/  else {
            return true;
        }
    }


}