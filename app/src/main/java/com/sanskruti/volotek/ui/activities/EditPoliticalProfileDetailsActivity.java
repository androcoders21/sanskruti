package com.sanskruti.volotek.ui.activities;

import static android.view.View.VISIBLE;
import static com.sanskruti.volotek.MyApplication.context;
import static com.sanskruti.volotek.MyApplication.getAppContext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.easystudio.rotateimageview.RotateZoomImageView;
//import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.jaredrummler.android.colorpicker.ColorPickerView;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.adapters.StickerAdapterTwo;
import com.sanskruti.volotek.adapters.StickerCatAdapter;
import com.sanskruti.volotek.api.ApiClient;
import com.sanskruti.volotek.binding.GlideDataBinding;
import com.sanskruti.volotek.custom.poster.adapter.FontAdapter;
import com.sanskruti.volotek.custom.poster.listener.OnClickCallback;
import com.sanskruti.volotek.model.ItemPolitical;
import com.sanskruti.volotek.model.Sticker;
import com.sanskruti.volotek.model.StickerResponse;
import com.sanskruti.volotek.model.StickersCategory;
import com.sanskruti.volotek.model.Watermark;
import com.sanskruti.volotek.ui.dialog.UniversalDialog;
import com.sanskruti.volotek.ui.fragments.EditTextItemDialogFragment;
import com.sanskruti.volotek.ui.fragments.FontFamilyBottomSheetDialogFragment;
import com.sanskruti.volotek.utils.Configure;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.ImageCropperFragment;
import com.sanskruti.volotek.utils.MyUtils;
import com.sanskruti.volotek.utils.PreferenceManager;
import com.sanskruti.volotek.viewmodel.UserViewModel;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPoliticalProfileDetailsActivity extends AppCompatActivity{
    LinearLayout llFramesLl, llProfilePhotoLl, llNameLl, btnDownload, llDesignation1Ll, llDesignation2Ll, llMobileLl,llStickerLl,
            llLeadersPhotoLl, llSocialMediaIconsLl, llPartyIconLayout, llcolorll, llcolord1ll, llcolord2ll, llcolorMobilell, uploadedPhoto, add_photoLL,add_textLL,lay_editText,textPhotoAddll,add_StickerLL, add_bgImage,contact_whatsapp;
    FontAdapter adapter;
    LinearLayout llfontll, llfontd1ll, llfontd2ll, llfontMobilell;

    LinearLayout lay_profile_photo_ll, lay_party_photo_ll, lay_name_ll, lay_Designation1_ll, lay_Designation2_ll, lay_Mobile_ll, lay_SocialMedia_ll, lay_LeadersPhoto_ll, lay_frames_ll, lay_sticker_ll, lay_uploaded_photo,photoViewll;

    LinearLayout profilePhotoShowLLll, partyPhotoShowLLll, nameShowLLll, designation1ShowLLll, designation2ShowLLll, mobileShowLLll, socialMediaShowLLll;

    boolean checkProfilePhoto = true, checkPartyPhoto = true, checkName = true, checkDesignation1 = true, checkDesignation2 = true, checkMobile = true, checkSocialMedia = true;
    private ImageView ivFlipIv, uploadedFlipIv;
    private SeekBar btnseekBarProfilePhoto, btnseekBarPartyPhoto, btnseekBarName, btnseekBarDesignation1, btnseekBarDesignation2, btnseekBarMobile;

    private String position,categoryName;
    ImageView ivSticker00, ivSticker01, ivSticker02, ivSticker03, ivSticker04, ivSticker05, ivSticker06, ivSticker07, ivSticker08, ivSticker09;
    private ImageView ivbtnBoldFontName, ivbtnItalicFontName, ivbtnUnderlineFontName,
            ivbtnBoldFontDesignation1, ivbtnItalicFontDesignation1, ivbtnUnderlineFontDesignation1,
            ivbtnBoldFontDesignation2, ivbtnItalicFontDesignation2, ivbtnUnderlineFontDesignation2,
            ivbtnBoldFontMobile, ivbtnItalicFontMobile, ivbtnUnderlineFontMobile;


    private ImageView visibleProfilePhotoEyeIconIv, hideProfilePhotoEyeIconIv, visiblePartyPhotoEyeIconIv, hidePartyPhotoEyeIconIv, visibleNameKyeIconIv, hideNameEyeIconIv,
            visibleDesignation1KyeIconIv, hideDesignation1EyeIconIv, visibleDesignation2KyeIconIv, hideDesignation2EyeIconIv,
            visibleMobileKyeIconIv, hideMobileEyeIconIv, visibleSocialMediaKyeIconIv, hideSocialMediaEyeIconIv;

    private TextView tvProfilePhotoShowTv, tvPartyPhotoShowTv, tvNameShowTv, tvDesignation1ShowTv, tvDesignation2ShowTv, tvMobileShowTv, tvSocialMediaShowTv,category_text;
    UniversalDialog universalDialog;
    RelativeLayout constraint, constraintTwo,constraintThumbnail;
    PreferenceManager preferenceManager;
    JSONArray jsonArray = new JSONArray();
    EditText etText;
    ImageView ivAddImg, ivAddImgParty, ivAddImgLeader1, ivAddImgLeader2, ivAddImgLeader3, ivAddImgLeader4, ivAddImgLeader5, ivAddImgLeader6, ivSocialMediaIv,rightThumbnailImage,leftThumbnailImage,centerThumbnailImage;


    ImageView ivAddImgLeader11, ivAddImgLeader22, ivAddImgLeader33, ivAddImgLeader44, ivAddImgLeader55, ivAddImgLeader66;

    ImageView ivFrames00, ivFrames11, ivFrames22, ivFrames33, ivFrames44, ivFrames55, ivFrames66, ivFrames77, ivFrames88, ivFrames99, ivFrames010, ivFrames011, ivFrames012, ivFrames013, ivFrames014, ivFrames015, ivFrames016, ivFrames017, ivFrames018, ivFrames019, ivFrames020, ivFrames021 ;
    private TextView tvNameTv, tvDesignation1Tv, tvDesignation2Tv, tvMobileNoTv,btn_save;

    private String pName, pPhone, pEmail, pFacebookUsername, pInstagramUsername, pTwitterUsername,
            pDesignation1, pDesignation2, pProfileImg = "", pPartyImg = "", pLeaderImg1 = "", pLeaderImg2="",
            pLeaderImg3="", pLeaderImg4="", pLeaderImg5="", pLeaderImg6="";
    private Dialog dialog;
//    private RotateZoomImageView  photoView;

    private ImageView photoView,photoViewFlip;
    private static final int PICK_IMAGE_REQUEST = 1;

    List<ImageView> closeButtons = new ArrayList<>();

    Uri imageUri;

    private int slectedFontColor = -16056320;

    private String selectedFontFamily =  "Baloo-Bold.ttf";

    private float selectedFontSize =  20;

    private boolean isAddImage = false;
    Watermark watermarkDetails;

    RecyclerView stickerRecyclerView,recyclerViewStickerCat;

    private void setIsAddImage(boolean value){
        this.isAddImage = value;
    }
    private Boolean getIsAddImage(){
        return isAddImage;
    }
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
                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), UUID.randomUUID().toString()));
                UCrop.Options options2 = new UCrop.Options();
                options2.setCompressionFormat(Bitmap.CompressFormat.PNG);
                options2.setFreeStyleCropEnabled(false);
                options2.setAllowedGestures(UCropActivity.SCALE,UCropActivity.NONE,UCropActivity.SCALE);

                UCrop.of(uri, destinationUri)
                        .withOptions(options2)
                        .start(this);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
        someActivityResultLauncher.launch(intent);
    }
    private boolean greeting = false;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == UCrop.REQUEST_CROP) {

            if (data != null) {
                new ImageCropperFragment(0, MyUtils.getPathFromURI(this, UCrop.getOutput(data)), (id, out) -> {
                    imageUri = Uri.parse(out);
                    if(isAddImage){
                        createImage(getAppContext(),imageUri);
                        setIsAddImage(false);
                    }else {
                        photoView.setImageURI(imageUri);
                        uploadedPhoto.setVisibility(View.GONE);
                    }
                }).show(getSupportFragmentManager(), "");
            }

        }


    }
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
//            // Get the image URI
//            Uri imageUri = data.getData();
//            /*    photoView.setImageResource(R.drawable.demo_img);*/
//            photoView.setImageURI(imageUri);
//
//            // Now you can use the imageUri to do further processing (e.g., upload the image)
//            // You might want to display the selected image in an ImageView
//        }
//    }
    private RelativeLayout movableImageView;

    private ImageView ivclose,ivStickerImg;
    private float xDelta, yDelta;

    private List<Sticker> stickerArrayList;

    private List<StickersCategory> stickersCategoryList,stickersCategoryList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_edit_political_profile_details);
        movableImageView = findViewById(R.id.movableImageView);
        llStickerLl = (LinearLayout)findViewById(R.id.stickerLl);
        add_StickerLL = (LinearLayout) findViewById(R.id.add_StickerLL);
        ivclose = findViewById(R.id.movableImageViewClose);
        lay_sticker_ll  = (LinearLayout) findViewById(R.id.lay_sticker);
        uploadedPhoto = (LinearLayout) findViewById(R.id.uploadedPhoto);
        contact_whatsapp = (LinearLayout) findViewById(R.id.contact_whatsapp);
        ivSticker00 = (ImageView) findViewById(R.id.iv_sticker_01);
        ivSticker01 = (ImageView) findViewById(R.id.iv_sticker_02);
        ivSticker02 = (ImageView) findViewById(R.id.iv_sticker_03);
        ivSticker03 = (ImageView) findViewById(R.id.iv_sticker_04);
        ivSticker04 = (ImageView) findViewById(R.id.iv_sticker_05);
        ivSticker05 = (ImageView) findViewById(R.id.iv_sticker_06);
        ivSticker06 = (ImageView) findViewById(R.id.iv_sticker_07);
        ivSticker07 = (ImageView) findViewById(R.id.iv_sticker_08);
        ivSticker08 = (ImageView) findViewById(R.id.iv_sticker_09);
        ivSticker09 = (ImageView) findViewById(R.id.iv_sticker_10);
        ivStickerImg = (ImageView)findViewById(R.id.stickerImg);
        constraint = (RelativeLayout) findViewById(R.id.constraint);
        constraintTwo = (RelativeLayout) findViewById(R.id.constraintTwo);
        constraintThumbnail = findViewById(R.id.constraintThum);
        rightThumbnailImage = findViewById(R.id.rightThumbnailImage);
        leftThumbnailImage = findViewById(R.id.leftThumbnailImage);
        centerThumbnailImage = findViewById(R.id.centerThumbnailImage);
        stickerRecyclerView = findViewById(R.id.recyclerViewSticker);
        recyclerViewStickerCat = findViewById(R.id.recyclerViewStickerCat);
        movableImageView.setVisibility(View.GONE);
        ivclose.setVisibility(View.GONE);
        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movableImageView.setVisibility(View.GONE);
            }
        });



        // Set touch listener to the image view
        movableImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final float x = event.getRawX();
                final float y = event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        // Save the initial touch coordinates
                        xDelta = x - view.getX();
                        yDelta = y - view.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // Update the image view's position based on finger movement
                        view.setX(x - xDelta);
                        view.setY(y - yDelta);
                        break;
                }

                return true;
            }
        });

        contact_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "+918553537373";
                String message = "नमस्कार, स्पेशल फ्रेम हवी आहे।";
                openWhatsAppChat(phoneNumber,message);
            }
        });


        llStickerLl.setOnClickListener(new View.OnClickListener() {
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
                    lay_sticker_ll.setVisibility(VISIBLE);
                    lay_frames_ll.setVisibility(View.GONE);
                    lay_editText.setVisibility(View.GONE);
                    textPhotoAddll.setVisibility(VISIBLE);
            }
        });

        add_StickerLL.setOnClickListener(new View.OnClickListener() {
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
                    lay_sticker_ll.setVisibility(VISIBLE);
                    lay_frames_ll.setVisibility(View.GONE);
                    lay_editText.setVisibility(View.GONE);
                    textPhotoAddll.setVisibility(VISIBLE);
            }
        });
        photoViewll = findViewById(R.id.photoViewll);
        photoViewFlip = findViewById(R.id.photoViewFlip);
        photoView = findViewById(R.id.photoView);

        add_photoLL = (LinearLayout) findViewById(R.id.add_photoLL);
        add_textLL = (LinearLayout) findViewById(R.id.add_textLL);
        lay_editText = (LinearLayout) findViewById(R.id.lay_editText);
        textPhotoAddll = (LinearLayout) findViewById(R.id.textPhotoAddll);

        // Load your image into the PhotoView
//        photoView.setImageResource(R.drawable.photo_upload);

        // Enable zoom and pan gestures
//        photoView.setMaximumScale(5.0f);
//        photoView.setMediumScale(2.5f);
//        photoView.setMinimumScale(0.5f);
//        photoView.setZoomable(true);

        photoView.setVisibility(View.GONE);
        constraintTwo.setOnClickListener(new View.OnClickListener() {
            private static final long DOUBLE_CLICK_TIME_DELTA = 300; // Time in milliseconds for double click detection
            long lastClickTime = 0;

            @Override
            public void onClick(View v) {
                if(greeting) {
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                        // Double click detected
                        openGallery();
                        photoViewFlip.setVisibility(VISIBLE);
                    }
                    lastClickTime = clickTime;
                    handleCloseButtons(true);
                }
            }
        });


        photoViewll.setOnTouchListener(new View.OnTouchListener() {
            private static final int INVALID_POINTER_ID = -1;
            private static final long DOUBLE_TAP_TIME_DELTA = 300;

            private float xDelta;
            private float yDelta;
            private float lastRotation = 0;
            private float lastScale = 1f;

            private int activePointerId1 = INVALID_POINTER_ID;
            private int activePointerId2 = INVALID_POINTER_ID;
            private long lastClickTime = 0;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Save the initial touch coordinates
                        xDelta = event.getRawX() - view.getX();
                        yDelta = event.getRawY() - view.getY();
                        activePointerId1 = event.getPointerId(0);
                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (event.getPointerCount() == 2) {
                            // Two fingers are down
                            lastRotation = getRotation(event);
                            lastScale = getDistance(event);
                            activePointerId2 = event.getPointerId(1);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (event.getPointerCount() == 1 && activePointerId1 != INVALID_POINTER_ID) {
                            // Single finger move
                            view.setX(event.getRawX() - xDelta);
                            view.setY(event.getRawY() - yDelta);
                        } else if (event.getPointerCount() == 2 && activePointerId2 != INVALID_POINTER_ID) {
                            // Two fingers move
                            float rotation = getRotation(event) - lastRotation;
                            view.setRotation(view.getRotation() + rotation);

                            float scale = getDistance(event) / lastScale;
                            view.setScaleX(view.getScaleX() * scale);
                            view.setScaleY(view.getScaleY() * scale);

                            lastRotation = getRotation(event);
                            lastScale = getDistance(event);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        long clickTime = System.currentTimeMillis();
                        if (clickTime - lastClickTime < DOUBLE_TAP_TIME_DELTA) {
                            // Double tap detected, open gallery
                            openGallery();
                        }
                        lastClickTime = clickTime;
                        activePointerId1 = INVALID_POINTER_ID;
                        activePointerId2 = INVALID_POINTER_ID;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        activePointerId1 = INVALID_POINTER_ID;
                        activePointerId2 = INVALID_POINTER_ID;
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        // Check if one of the two fingers goes up
                        int pointerIndex = event.getActionIndex();
                        int pointerId = event.getPointerId(pointerIndex);
                        if (pointerId == activePointerId1) {
                            activePointerId1 = INVALID_POINTER_ID;
                        } else if (pointerId == activePointerId2) {
                            activePointerId2 = INVALID_POINTER_ID;
                        }
                        break;
                }

                return true;
            }

            private float getRotation(MotionEvent event) {
                float deltaX = event.getX(0) - event.getX(1);
                float deltaY = event.getY(0) - event.getY(1);
                double radians = Math.atan2(deltaY, deltaX);
                return (float) Math.toDegrees(radians);
            }

            private float getDistance(MotionEvent event) {
                float deltaX = event.getX(0) - event.getX(1);
                float deltaY = event.getY(0) - event.getY(1);
                return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            }
        });

        photoViewFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipImage(photoView);
            }
        });

        add_photoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery when a button or some UI element is clicked
                setIsAddImage(true);
                openGallery();
            }
        });
//        add_textLL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                createText(getAppContext(),"Sanskruti Design");
//                // Open gallery when a button or some UI element is clicked
//
//            }
//        });

        init();
        loadStickerCategoryData();
        loadWaterMark();
//        loadStickerData();
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

    private void loadStickerData(String categoryType){
        ApiClient.getApiDataService().getStickerData(categoryType).enqueue(
                new Callback<StickerResponse>() {
                    @Override
                    public void onResponse(Call<StickerResponse> call, Response<StickerResponse> response) {
                        if(response.isSuccessful()){
                            stickersCategoryList2 = response.body().getData();
                            StickersCategory stickersCategory = stickersCategoryList2.get(0);
                            stickerArrayList = stickersCategory.getStickers();

                                stickerRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//
                                StickerAdapterTwo adapter = new StickerAdapterTwo(getApplicationContext(), stickerArrayList);
                                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                                stickerRecyclerView.setLayoutManager(verticalLayoutManager);
                                stickerRecyclerView.setAdapter(adapter);
                                adapter.setOnItemClickListener(new StickerAdapterTwo.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(String imageUrl) {
                                        createSticker(context,"2",imageUrl);

                                    }
                                });

                        }
                    }

                    @Override
                    public void onFailure(Call<StickerResponse> call, Throwable t) {

                    }
                }
        );
    }

    private void loadStickerCategoryData(){
        ApiClient.getApiDataService().getStickerList().enqueue(
                new Callback<StickerResponse>() {
                    @Override
                    public void onResponse(Call<StickerResponse> call, Response<StickerResponse> response) {
                        if(response.isSuccessful()){
                            StickerResponse stickerResponse = response.body();

                                stickersCategoryList = stickerResponse.getData();
                                if(stickersCategoryList.size() > 0){
                                    StickersCategory stickersCategory = stickersCategoryList.get(0);
                                    loadStickerData(stickersCategory.getCategory_title());
                                }
                                recyclerViewStickerCat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                                StickerCatAdapter adapter = new StickerCatAdapter(getApplicationContext(), stickersCategoryList);
                                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                                recyclerViewStickerCat.setLayoutManager(verticalLayoutManager);
                                recyclerViewStickerCat.setAdapter(adapter);
                                adapter.setOnItemClickListener(new StickerCatAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(String category_name) {
                                        loadStickerData(category_name);
                                    }
                                });

                        }
                    }

                    @Override
                    public void onFailure(Call<StickerResponse> call, Throwable t) {
                            Log.i("saqlain",t.toString());
                    }
                }
        );
    }

    private void loadWaterMark(){
        Constant.getHomeViewModel(this).getWatermark().observe(this,homeItem->{
            if(homeItem != null) {
                watermarkDetails = homeItem.getData();
                GlideDataBinding.bindImage(rightThumbnailImage,watermarkDetails.watermarkImage);
                GlideDataBinding.bindImage(leftThumbnailImage,watermarkDetails.watermarkImage);
                GlideDataBinding.bindImage(centerThumbnailImage,watermarkDetails.watermarkImage);
            }
        });
    }
    private void getDataShare() {


        String userDataString = preferenceManager.getStringTwo(Constant.USER_POLITICAL_PROFILE);
        // Retrieve JSONArray string from SharedPreferences

        // Convert JSONArray string back to List<User>
        Log.i("getJSONData", "userDataString = " + userDataString.toString());

        position = getIntent().getStringExtra("index");
        String imgUrl = getIntent().getStringExtra("img");
        greeting = getIntent().getBooleanExtra("greeting", false);


   //     Toast.makeText(this, "greeting = "+String.valueOf(greeting), Toast.LENGTH_SHORT).show();
        if (greeting) {
            photoView.setVisibility(VISIBLE);
            movableImageView.setVisibility(VISIBLE);
            llStickerLl.setVisibility(View.GONE);
            textPhotoAddll.setVisibility(VISIBLE);
            add_textLL.setVisibility(VISIBLE);
        } else {
            photoView.setVisibility(View.GONE);
            movableImageView.setVisibility(View.GONE);
            llStickerLl.setVisibility(View.GONE);
            textPhotoAddll.setVisibility(VISIBLE);
            add_textLL.setVisibility(VISIBLE);
        }
        if (getIntent().getStringExtra("imgThum") != null) {
            //    Toast.makeText(this, "not null", Toast.LENGTH_SHORT).show();
//            LinearLayout layout = (LinearLayout) findViewById(R.id.bottomLay);
//            layout.setVisibility(View.GONE);
            contact_whatsapp.setVisibility(VISIBLE);
            LinearLayout fm = (LinearLayout) findViewById(R.id.linearLogoL);
            fm.setVisibility(View.GONE);

            LinearLayout fmlogo = (LinearLayout) findViewById(R.id.linearLogo);
            fmlogo.setVisibility(View.GONE);

            LinearLayout bottomButtons = (LinearLayout) findViewById(R.id.bottom_buttons);
            bottomButtons.setVisibility(View.GONE);

            lay_sticker_ll.setVisibility(VISIBLE);
            lay_frames_ll.setVisibility(View.GONE);
            ivAddImg.setVisibility(View.GONE);
            ivAddImgParty.setVisibility(View.GONE);
//            textPhotoAddll.setVisibility(View.GONE);

            String imgUrlThum = getIntent().getStringExtra("imgThum");

            new DownloadImageTaskThum().execute(imgUrlThum);
        } else {
            //    Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
       String profileIdOther = getIntent().getStringExtra("profileId");


        Log.i("getJSONDataLuc", "RESPONSE profileIdOther 777-->" + String.valueOf(profileIdOther));
        //    String profileId = Action.equalsIgnoreCase("update") ? profileIdOther : null;
        userViewModel.getPolitical(profileIdOther).observe(this, businessItem -> {

            if (businessItem != null) {
              //  prgDialog.dismiss();
                Log.i("getJSONDataLuc", "RESPONSE 777-->" + new Gson().toJson(businessItem));


                //    Toast.makeText(PoliticalProfileDetailsEditActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();

                String id = businessItem.profiles.pUserId;

                if(businessItem.profiles.pName != null){
                    pName = businessItem.profiles.pName;
                }
                if(businessItem.profiles.pPhone != null){
                    pPhone = businessItem.profiles.pPhone;
                }
                if(businessItem.profiles.pEmail != null){
                    pEmail = businessItem.profiles.pEmail;
                }
                if(businessItem.profiles.pFacebookUsername != null){
                    pFacebookUsername = businessItem.profiles.pFacebookUsername;
                }

                if(businessItem.profiles.pInstagramUsername != null){
                    pInstagramUsername = businessItem.profiles.pInstagramUsername;
                }
                if(businessItem.profiles.pTwitterUsername != null){
                    pTwitterUsername = businessItem.profiles.pTwitterUsername;
                }

                if(businessItem.profiles.pDesignation1 != null){
                    pDesignation1 = businessItem.profiles.pDesignation1;
                }
                if(businessItem.profiles.pDesignation2 != null){
                    pDesignation2 = businessItem.profiles.pDesignation2;
                }

                if(businessItem.profiles.pProfileImg != null){
                    pProfileImg = businessItem.profiles.pProfileImg;
                }
                if(businessItem.profiles.pPartyImg != null){
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




                    setData();

//                            setResult(RESULT_OK);
//                            finish();

            }


        });

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
        uploadedFlipIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipImage(photoView);
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

        ivSticker00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ivclose.setVisibility(VISIBLE);
//                movableImageView.setVisibility(VISIBLE);
//                ivStickerImg.setImageResource(R.drawable.sticker_01);
//                createSticker(getAppContext(),"1");
                ivSticker00.setBackground(getDrawable(R.drawable.images_background));
                ivSticker01.setBackground(null);
                ivSticker02.setBackground(null);
                ivSticker03.setBackground(null);
                ivSticker04.setBackground(null);

                ivSticker05.setBackground(null);
                ivSticker06.setBackground(null);
                ivSticker07.setBackground(null);
                ivSticker08.setBackground(null);

                ivSticker09.setBackground(null);
            }
        });
        ivSticker01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ivclose.setVisibility(VISIBLE);
//                movableImageView.setVisibility(VISIBLE);
//                ivStickerImg.setImageResource(R.drawable.sticker_02);
//                createSticker(getAppContext(),"2");
                ivSticker00.setBackground(null);
                ivSticker01.setBackground(getDrawable(R.drawable.images_background));

                ivSticker02.setBackground(null);
                ivSticker03.setBackground(null);
                ivSticker04.setBackground(null);

                ivSticker05.setBackground(null);
                ivSticker06.setBackground(null);
                ivSticker07.setBackground(null);
                ivSticker08.setBackground(null);

                ivSticker09.setBackground(null);
            }
        });
        ivSticker02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ivclose.setVisibility(VISIBLE);
//                movableImageView.setVisibility(VISIBLE);
//                ivStickerImg.setImageResource(R.drawable.sticker_03);
//                createSticker(getAppContext(),"3");
                ivSticker00.setBackground(null);
                ivSticker01.setBackground(null);
                ivSticker02.setBackground(getDrawable(R.drawable.images_background));


                ivSticker03.setBackground(null);
                ivSticker04.setBackground(null);

                ivSticker05.setBackground(null);
                ivSticker06.setBackground(null);
                ivSticker07.setBackground(null);
                ivSticker08.setBackground(null);

                ivSticker09.setBackground(null);
            }
        });
        ivSticker03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ivclose.setVisibility(VISIBLE);
//                movableImageView.setVisibility(VISIBLE);
//                ivStickerImg.setImageResource(R.drawable.sticker_04);
//                createSticker(getAppContext(),"4");
                ivSticker00.setBackground(null);
                ivSticker01.setBackground(null);
                ivSticker02.setBackground(null);
                ivSticker03.setBackground(getDrawable(R.drawable.images_background));

                ivSticker04.setBackground(null);

                ivSticker05.setBackground(null);
                ivSticker06.setBackground(null);
                ivSticker07.setBackground(null);
                ivSticker08.setBackground(null);

                ivSticker09.setBackground(null);
            }
        });
        ivSticker04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ivclose.setVisibility(VISIBLE);
//                movableImageView.setVisibility(VISIBLE);
//                ivStickerImg.setImageResource(R.drawable.sticker_05);
//                createSticker(getAppContext(),"5");
                ivSticker00.setBackground(null);
                ivSticker01.setBackground(null);
                ivSticker02.setBackground(null);
                ivSticker03.setBackground(null);
                ivSticker04.setBackground(getDrawable(R.drawable.images_background));


                ivSticker05.setBackground(null);
                ivSticker06.setBackground(null);
                ivSticker07.setBackground(null);
                ivSticker08.setBackground(null);

                ivSticker09.setBackground(null);
            }
        });
        ivSticker05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ivclose.setVisibility(VISIBLE);
//                movableImageView.setVisibility(VISIBLE);
//                ivStickerImg.setImageResource(R.drawable.sticker_06);
//                createSticker(getAppContext(),"6");
                ivSticker00.setBackground(null);
                ivSticker01.setBackground(null);
                ivSticker02.setBackground(null);
                ivSticker03.setBackground(null);
                ivSticker04.setBackground(null);
                ivSticker05.setBackground(getDrawable(R.drawable.images_background));

                ivSticker06.setBackground(null);
                ivSticker07.setBackground(null);
                ivSticker08.setBackground(null);
                ivSticker09.setBackground(null);
            }
        });
        ivSticker06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ivclose.setVisibility(VISIBLE);
//                movableImageView.setVisibility(VISIBLE);
//                ivStickerImg.setImageResource(R.drawable.sticker_07);
//                createSticker(getAppContext(),"7");
                ivSticker00.setBackground(null);
                ivSticker01.setBackground(null);
                ivSticker02.setBackground(null);
                ivSticker03.setBackground(null);
                ivSticker04.setBackground(null);
                ivSticker05.setBackground(null);
                ivSticker06.setBackground(getDrawable(R.drawable.images_background));

                ivSticker07.setBackground(null);
                ivSticker08.setBackground(null);
                ivSticker09.setBackground(null);
            }
        });
        ivSticker07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ivclose.setVisibility(VISIBLE);
//                movableImageView.setVisibility(VISIBLE);
//                ivStickerImg.setImageResource(R.drawable.sticker_08);
//                createSticker(getAppContext(),"8");
                ivSticker00.setBackground(null);
                ivSticker01.setBackground(null);
                ivSticker02.setBackground(null);
                ivSticker03.setBackground(null);
                ivSticker04.setBackground(null);
                ivSticker05.setBackground(null);
                ivSticker06.setBackground(null);
                ivSticker07.setBackground(getDrawable(R.drawable.images_background));

                ivSticker08.setBackground(null);
                ivSticker09.setBackground(null);
            }
        });
        ivSticker08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ivclose.setVisibility(VISIBLE);
//                movableImageView.setVisibility(VISIBLE);
//                ivStickerImg.setImageResource(R.drawable.sticker_09);
//                createSticker(getAppContext(),"9");
                ivSticker00.setBackground(null);
                ivSticker01.setBackground(null);
                ivSticker02.setBackground(null);
                ivSticker03.setBackground(null);
                ivSticker04.setBackground(null);
                ivSticker05.setBackground(null);
                ivSticker06.setBackground(null);
                ivSticker07.setBackground(null);
                ivSticker08.setBackground(getDrawable(R.drawable.images_background));

                ivSticker09.setBackground(null);
            }
        });
        ivSticker09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ivclose.setVisibility(VISIBLE);
//                movableImageView.setVisibility(VISIBLE);
//                ivStickerImg.setImageResource(R.drawable.sticker_10);
//                createSticker(getAppContext(),"10");
                ivSticker00.setBackground(null);
                ivSticker01.setBackground(null);
                ivSticker02.setBackground(null);
                ivSticker03.setBackground(null);
                ivSticker04.setBackground(null);
                ivSticker05.setBackground(null);
                ivSticker06.setBackground(null);
                ivSticker07.setBackground(null);
                ivSticker08.setBackground(null);
                ivSticker09.setBackground(getDrawable(R.drawable.images_background));


            }
        });
        ivFrames00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPoliticalFrame0();
                handleResetProgress();
                ivFrames00.setBackground(getDrawable(R.drawable.images_background));
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);
            }
        });

        ivFrames11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPoliticalFrame1();
                handleResetProgress();
                ivFrames00.setBackground(null);
                ivFrames11.setBackground(getDrawable(R.drawable.images_background));
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);
            }
        });


        ivFrames22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
                switchToPoliticalFrame2();
                handleResetProgress();
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(getDrawable(R.drawable.images_background));
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);
            }
        });


        ivFrames33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
                handleSwitchFrame("4");
                ivFrames22.setBackground(null);
                ivFrames11.setBackground(null);
                ivFrames33.setBackground(getDrawable(R.drawable.images_background));
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);
            }
        });

        ivFrames44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame4();
                handleSwitchFrame("5");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(getDrawable(R.drawable.images_background));
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);
            }
        });

        ivFrames55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("6");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(getDrawable(R.drawable.images_background));
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("7");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(getDrawable(R.drawable.images_background));
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames77.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("8");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(getDrawable(R.drawable.images_background));
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames88.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("9");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(getDrawable(R.drawable.images_background));
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames99.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("10");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(getDrawable(R.drawable.images_background));
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames010.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("11");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(getDrawable(R.drawable.images_background));
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames011.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("12");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(getDrawable(R.drawable.images_background));
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames012.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("13");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(getDrawable(R.drawable.images_background));
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames013.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("14");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(getDrawable(R.drawable.images_background));
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames014.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("15");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(getDrawable(R.drawable.images_background));
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames015.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("16");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(getDrawable(R.drawable.images_background));
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames016.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("17");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(getDrawable(R.drawable.images_background));
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames017.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("18");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(getDrawable(R.drawable.images_background));
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames018.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("19");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(getDrawable(R.drawable.images_background));
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames019.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("20");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(getDrawable(R.drawable.images_background));
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(null);

            }
        });

        ivFrames020.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("21");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(getDrawable(R.drawable.images_background));
                ivFrames021.setBackground(null);

            }
        });

        ivFrames021.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFrames00.setBackground(null);
//                switchToPoliticalFrame5();
                handleSwitchFrame("22");
                ivFrames11.setBackground(null);
                ivFrames22.setBackground(null);
                ivFrames33.setBackground(null);
                ivFrames44.setBackground(null);
                ivFrames55.setBackground(null);
                ivFrames66.setBackground(null);
                ivFrames77.setBackground(null);
                ivFrames88.setBackground(null);
                ivFrames99.setBackground(null);
                ivFrames010.setBackground(null);
                ivFrames011.setBackground(null);
                ivFrames012.setBackground(null);
                ivFrames013.setBackground(null);
                ivFrames014.setBackground(null);
                ivFrames015.setBackground(null);
                ivFrames016.setBackground(null);
                ivFrames017.setBackground(null);
                ivFrames018.setBackground(null);
                ivFrames019.setBackground(null);
                ivFrames020.setBackground(null);
                ivFrames021.setBackground(getDrawable(R.drawable.images_background));

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

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createText(getAppContext(),etText.getText().toString(),slectedFontColor);
            }
        });
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

                adapter = new FontAdapter(EditPoliticalProfileDetailsActivity.this, getResources().getStringArray(R.array.fonts_array));
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

                adapter = new FontAdapter(EditPoliticalProfileDetailsActivity.this, getResources().getStringArray(R.array.fonts_array));
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

                adapter = new FontAdapter(EditPoliticalProfileDetailsActivity.this, getResources().getStringArray(R.array.fonts_array));
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

                adapter = new FontAdapter(EditPoliticalProfileDetailsActivity.this, getResources().getStringArray(R.array.fonts_array));
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
                ivclose.setVisibility(View.GONE);
                checkSubscriptionPlansExpireDialog();
            }
        });

        llFramesLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    lay_profile_photo_ll.setVisibility(View.GONE);
                    lay_uploaded_photo.setVisibility(View.GONE);
                    lay_name_ll.setVisibility(View.GONE);
                    lay_Designation1_ll.setVisibility(View.GONE);
                    lay_party_photo_ll.setVisibility(View.GONE);
                    lay_Designation2_ll.setVisibility(View.GONE);
                    lay_Mobile_ll.setVisibility(View.GONE);
                    lay_SocialMedia_ll.setVisibility(View.GONE);
                    lay_LeadersPhoto_ll.setVisibility(View.GONE);
                    lay_frames_ll.setVisibility(VISIBLE);
                    lay_sticker_ll.setVisibility(View.GONE);
                    lay_editText.setVisibility(View.GONE);
                    textPhotoAddll.setVisibility(VISIBLE);

            }
        });
        llProfilePhotoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    lay_profile_photo_ll.setVisibility(View.VISIBLE);
                    lay_uploaded_photo.setVisibility(View.GONE);
                    lay_name_ll.setVisibility(View.GONE);
                    lay_Designation1_ll.setVisibility(View.GONE);
                    lay_party_photo_ll.setVisibility(View.GONE);
                    lay_Designation2_ll.setVisibility(View.GONE);
                    lay_Mobile_ll.setVisibility(View.GONE);
                    lay_SocialMedia_ll.setVisibility(View.GONE);
                    lay_LeadersPhoto_ll.setVisibility(View.GONE);
                    lay_frames_ll.setVisibility(View.GONE);
                    lay_sticker_ll.setVisibility(View.GONE);
                    lay_editText.setVisibility(View.GONE);
                    textPhotoAddll.setVisibility(VISIBLE);
            }
        });
        llNameLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_uploaded_photo.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.VISIBLE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
                lay_sticker_ll.setVisibility(View.GONE);
                lay_editText.setVisibility(View.GONE);
                textPhotoAddll.setVisibility(View.GONE);
            }
        });
        llDesignation1Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_uploaded_photo.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.VISIBLE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
                lay_sticker_ll.setVisibility(View.GONE);
                lay_editText.setVisibility(View.GONE);
                textPhotoAddll.setVisibility(View.GONE);
            }
        });
        llDesignation2Ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_uploaded_photo.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.VISIBLE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
                lay_sticker_ll.setVisibility(View.GONE);
                lay_editText.setVisibility(View.GONE);
                textPhotoAddll.setVisibility(View.GONE);
            }
        });
        llMobileLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_uploaded_photo.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.VISIBLE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
                lay_sticker_ll.setVisibility(View.GONE);
                lay_editText.setVisibility(View.GONE);
                textPhotoAddll.setVisibility(View.GONE);
            }
        });

        llLeadersPhotoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    lay_profile_photo_ll.setVisibility(View.GONE);
                    lay_uploaded_photo.setVisibility(View.GONE);
                    lay_name_ll.setVisibility(View.GONE);
                    lay_party_photo_ll.setVisibility(View.GONE);
                    lay_Designation1_ll.setVisibility(View.GONE);
                    lay_Designation2_ll.setVisibility(View.GONE);
                    lay_Mobile_ll.setVisibility(View.GONE);
                    lay_SocialMedia_ll.setVisibility(View.GONE);
                    lay_LeadersPhoto_ll.setVisibility(View.VISIBLE);
                    lay_frames_ll.setVisibility(View.GONE);
                    lay_sticker_ll.setVisibility(View.GONE);
                    lay_editText.setVisibility(View.GONE);
                    textPhotoAddll.setVisibility(VISIBLE);
            }
        });
        llSocialMediaIconsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    lay_profile_photo_ll.setVisibility(View.GONE);
                    lay_uploaded_photo.setVisibility(View.GONE);
                    lay_name_ll.setVisibility(View.GONE);
                    lay_party_photo_ll.setVisibility(View.GONE);
                    lay_Designation1_ll.setVisibility(View.GONE);
                    lay_Designation2_ll.setVisibility(View.GONE);
                    lay_Mobile_ll.setVisibility(View.GONE);
                    lay_SocialMedia_ll.setVisibility(View.VISIBLE);
                    lay_LeadersPhoto_ll.setVisibility(View.GONE);
                    lay_frames_ll.setVisibility(View.GONE);
                    lay_sticker_ll.setVisibility(View.GONE);
                    lay_editText.setVisibility(View.GONE);
                    textPhotoAddll.setVisibility(VISIBLE);


            }
        });

        llPartyIconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    lay_profile_photo_ll.setVisibility(View.GONE);
                    lay_uploaded_photo.setVisibility(View.GONE);
                    lay_name_ll.setVisibility(View.GONE);
                    lay_party_photo_ll.setVisibility(View.VISIBLE);
                    lay_Designation1_ll.setVisibility(View.GONE);
                    lay_Designation2_ll.setVisibility(View.GONE);
                    lay_Mobile_ll.setVisibility(View.GONE);
                    lay_SocialMedia_ll.setVisibility(View.GONE);
                    lay_LeadersPhoto_ll.setVisibility(View.GONE);
                    lay_frames_ll.setVisibility(View.GONE);
                    lay_sticker_ll.setVisibility(View.GONE);
                    lay_editText.setVisibility(View.GONE);
                    textPhotoAddll.setVisibility(VISIBLE);
            }
        });
        add_textLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_uploaded_photo.setVisibility(View.GONE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
                lay_sticker_ll.setVisibility(View.GONE);
                lay_editText.setVisibility(View.VISIBLE);
            }
        });
        uploadedPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_profile_photo_ll.setVisibility(View.GONE);
                lay_uploaded_photo.setVisibility(View.VISIBLE);
                lay_name_ll.setVisibility(View.GONE);
                lay_party_photo_ll.setVisibility(View.GONE);
                lay_Designation1_ll.setVisibility(View.GONE);
                lay_Designation2_ll.setVisibility(View.GONE);
                lay_Mobile_ll.setVisibility(View.GONE);
                lay_SocialMedia_ll.setVisibility(View.GONE);
                lay_LeadersPhoto_ll.setVisibility(View.GONE);
                lay_frames_ll.setVisibility(View.GONE);
                lay_sticker_ll.setVisibility(View.GONE);
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
            int newWidth = 200 + progress * 25; // Adjust as needed
            int newHeight = 200 + progress * 25; // Adjust as needed


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
            int newWidth = 120 + progress * 10; // Adjust as needed
            int newHeight = 120 + progress * 10; // Adjust as needed


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

            float newTextSize = 20 + progress;

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

    private void switchToPoliticalFrame4() {
        // Assuming you have a reference to the parent ViewGroup
        ViewGroup parentLayout  = findViewById(R.id.parent_layout);


        // Remove the current toolbar from the parent layout
        View currentToolbar = findViewById(R.id.toolbar);
        parentLayout.removeView(currentToolbar);

        // Inflate the new layout (political_frame_2)
        View newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_5, parentLayout, false);


        // Add the new toolbar to the parent layout
        // Set the ID for the new toolbar
        newToolbar.setId(R.id.toolbar);
        parentLayout.addView(newToolbar);


        initView(newToolbar);
        getDataShare();
    }

    private void switchToPoliticalFrame5() {
        // Assuming you have a reference to the parent ViewGroup
        ViewGroup parentLayout  = findViewById(R.id.parent_layout);

        // Remove the current toolbar from the parent layout
        View currentToolbar = findViewById(R.id.toolbar);
        parentLayout.removeView(currentToolbar);

        // Inflate the new layout (political_frame_2)
        View newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_6, parentLayout, false);


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
        ivAddImgParty = (ImageView) newToolbar.findViewById(R.id.iv_party);
        ivAddImgLeader1 = (ImageView) newToolbar.findViewById(R.id.iv_logoL1);
        ivAddImgLeader2 = (ImageView) newToolbar.findViewById(R.id.iv_logoL2);
        ivAddImgLeader3 = (ImageView) newToolbar.findViewById(R.id.iv_logoL3);
        ivAddImgLeader4 = (ImageView) newToolbar.findViewById(R.id.iv_logoL4);
        ivAddImgLeader5 = (ImageView) newToolbar.findViewById(R.id.iv_logoL5);
        ivAddImgLeader6 = (ImageView) newToolbar.findViewById(R.id.iv_logoL6);
        ivSocialMediaIv = (ImageView) newToolbar.findViewById(R.id.socialMediaIv);
        uploadedFlipIv = (ImageView)findViewById(R.id.uploadedFlipIv);
        lay_uploaded_photo = (LinearLayout) findViewById(R.id.lay_uploaded_photo);
    }
    RelativeLayout ivBack;
    private void init() {
        ivBack = (RelativeLayout)findViewById(R.id.btn_bckprass);
        category_text = (TextView)findViewById(R.id.category_text);
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




        // Get the width of the screen or the parent layout
        int screenWidth = getResources().getDisplayMetrics().widthPixels-56;
        constraintTwo.getLayoutParams().height = screenWidth;
        // Request layout to make sure changes are applied
        constraintTwo.requestLayout();

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
        ivFrames44 = (ImageView) findViewById(R.id.iv_logoL141);
        ivFrames55 = (ImageView) findViewById(R.id.iv_logoL151);
        ivFrames66 = (ImageView) findViewById(R.id.iv_logoL161);
        ivFrames77 = (ImageView) findViewById(R.id.iv_logoL171);
        ivFrames88 = (ImageView) findViewById(R.id.iv_logoL181);
        ivFrames99 = (ImageView) findViewById(R.id.iv_logoL191);
        ivFrames010 = (ImageView) findViewById(R.id.iv_logoL010);
        ivFrames011 = (ImageView) findViewById(R.id.iv_logoL011);
        ivFrames012 = (ImageView) findViewById(R.id.iv_logoL012);
        ivFrames013 = (ImageView) findViewById(R.id.iv_logoL013);
        ivFrames014 = (ImageView) findViewById(R.id.iv_logoL014);
        ivFrames015 = (ImageView) findViewById(R.id.iv_logoL015);
        ivFrames016 = (ImageView) findViewById(R.id.iv_logoL016);
        ivFrames017 = (ImageView) findViewById(R.id.iv_logoL017);
        ivFrames018 = (ImageView) findViewById(R.id.iv_logoL018);
        ivFrames019 = (ImageView) findViewById(R.id.iv_logoL019);
        ivFrames020 = (ImageView) findViewById(R.id.iv_logoL020);
        ivFrames021 = (ImageView) findViewById(R.id.iv_logoL021);

        etText = (EditText) findViewById(R.id.etText);
        btn_save = (TextView) findViewById(R.id.btn_save);

        categoryName = getIntent().getStringExtra("categoryName");
        category_text.setText(categoryName);





        switchToPoliticalFrame4();


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
            Bitmap watermarkBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water_mark);

            Bitmap watermarkedBitmap = bitmap;
//            if (watermarkBitmap != null) {
//                watermarkedBitmap = addWatermark(bitmap, watermarkBitmap);
//                watermarkBitmap.recycle();
//            } else {
//                Log.e("Bitmap Loading", "Failed to load watermark image");
//                watermarkedBitmap = bitmap;
//            }


            watermarkedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            watermarkedBitmap.recycle();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
//            bitmap.recycle();
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
        Toast.makeText(EditPoliticalProfileDetailsActivity.this, "Download Successfully", Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(context, ShareImageActivity.class);
        intent.putExtra("uri", filePath);
        intent.putExtra("way", "Poster");
        startActivity(intent);*/
//        ivclose.setVisibility(VISIBLE);
        handleCloseButtons(false);
        rightThumbnailImage.setVisibility(View.GONE);
        leftThumbnailImage.setVisibility(View.GONE);
        centerThumbnailImage.setVisibility(View.GONE);

    }
    private Bitmap viewToBitmap(View view) {

        //  tvEdit.setVisibility(GONE);

        try {

            Bitmap createBitmap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888);


            view.draw(new Canvas(createBitmap));
            return createBitmap;
        } finally {
            view.destroyDrawingCache();
        }
    }

//    for water mark

    private Bitmap addWatermark(Bitmap originalBitmap, Bitmap  watermarkImage) {
        // Calculate the desired width and height for the watermark
        int desiredWidth = 15 ;  // Adjust the divisor to change the size ratio
        int desiredHeight = 120 ; // Adjust the divisor to change the size ratio

        // Resize the watermark image
        Bitmap resizedWatermarkImage = Bitmap.createScaledBitmap(watermarkImage, desiredWidth, desiredHeight, true);

        // Create a mutable copy of the original bitmap
        Bitmap watermarkedBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // Create a Canvas to draw the watermark
        Canvas canvas = new Canvas(watermarkedBitmap);

        // Define the Paint for drawing the watermark
        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY); // Set color for the watermark text

        // Calculate position to draw the watermark (adjust as needed)
        int x = 17; // X-coordinate
        int y = watermarkedBitmap.getHeight() - 600; // Y-coordinate

        // Save the current state of the canvas
        canvas.save();

        // Rotate the canvas by 90 degrees around the specified pivot point (x, y)


        // Draw the resized watermark image on the bitmap
        canvas.drawBitmap(resizedWatermarkImage, x, y, paint);


        // Recycle the resized watermark image to free up memory
        resizedWatermarkImage.recycle();

        return watermarkedBitmap;
    }

    private void handleSwitchFrame(String type){
        handleResetProgress();
        ViewGroup parentLayout  = findViewById(R.id.parent_layout);

        // Remove the current toolbar from the parent layout
        View currentToolbar = findViewById(R.id.toolbar);
        parentLayout.removeView(currentToolbar);

        // Inflate the new layout (political_frame_2)
        View newToolbar;
        switch (type) {
            case "4":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_4, parentLayout, false);
                break;
            case "5":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_5, parentLayout, false);
                break;
            case "6":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_6, parentLayout, false);
                break;
            case "7":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_7, parentLayout, false);
                break;
            case "8":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_8, parentLayout, false);
                break;
            case "9":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_9, parentLayout, false);
                break;
            case "10":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_10, parentLayout, false);
                break;
            case "11":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_11, parentLayout, false);
                break;
            case "12":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_12, parentLayout, false);
                break;
            case "13":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_13, parentLayout, false);
                break;
            case "14":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_14, parentLayout, false);
                break;
            case "15":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_15, parentLayout, false);
                break;
            case "16":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_16, parentLayout, false);
                break;
            case "17":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_17, parentLayout, false);
                break;
            case "18":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_18, parentLayout, false);
                break;
            case "19":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_19, parentLayout, false);
                break;
            case "20":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_20, parentLayout, false);
                break;
            case "21":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_21, parentLayout, false);
                break;
            case "22":
                newToolbar = LayoutInflater.from(this).inflate(R.layout.political_frame_22, parentLayout, false);
                break;
            default:
                // Handle default case if needed
                return;
        }

        // Add the new toolbar to the parent layout
        // Set the ID for the new toolbar
        newToolbar.setId(R.id.toolbar);
        parentLayout.addView(newToolbar);


        initView(newToolbar);
        getDataShare();
    }

    private void handleResetProgress(){
        btnseekBarProfilePhoto.setProgress(5);
        btnseekBarPartyPhoto.setProgress(5);
        btnseekBarName.setProgress(5);
        btnseekBarDesignation1.setProgress(2);
        btnseekBarDesignation2.setProgress(2);
    }

    public void createSticker(Context context,String sticker_type,String imageUr) {
        // Create a new RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(context);

        // Set layout parameters for the RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,  // Width
                RelativeLayout.LayoutParams.WRAP_CONTENT   // Height
        );
        relativeLayout.setLayoutParams(layoutParams);

        // Create the sticker ImageView
//        RotateZoomImageView stickerImageView = new RotateZoomImageView(context);
        ImageView stickerImageView = new ImageView(context);
        stickerImageView.setId(View.generateViewId()); // Generate a unique ID for each view
        Glide.with(context).load(imageUr).into(stickerImageView);


        // Set layout parameters for the sticker ImageView
        RelativeLayout.LayoutParams stickerParams = new RelativeLayout.LayoutParams(
                550, // Width
                550  // Height
        );

        stickerImageView.setLayoutParams(stickerParams);
        ImageView closeImageView = new ImageView(context);
        closeImageView.setId(View.generateViewId());
        closeImageView.setImageResource(R.drawable.icon_close);

// Set layout parameters for the close ImageView
        RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
//        closeParams.setMargins(0,-100,0,0);
        closeParams.addRule(RelativeLayout.ALIGN_TOP, stickerImageView.getId());
        closeParams.addRule(RelativeLayout.ALIGN_START, stickerImageView.getId());
        closeImageView.setLayoutParams(closeParams);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the parent RelativeLayout when the close button is clicked
                constraint.removeView(relativeLayout);

            }
        });

        ImageView zoomImageView = new ImageView(context);
        zoomImageView.setId(View.generateViewId()); // Generate a unique ID for each view
        zoomImageView.setImageResource(R.drawable.sticker_scale);

        // Set layout parameters for the close ImageView
        RelativeLayout.LayoutParams zoomParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
        zoomParams.addRule(RelativeLayout.ALIGN_BOTTOM, stickerImageView.getId());
        zoomParams.addRule(RelativeLayout.ALIGN_END, stickerImageView.getId());
        zoomImageView.setLayoutParams(zoomParams);

        ImageView rotateImageView = new ImageView(context);
        rotateImageView.setId(View.generateViewId()); // Generate a unique ID for each view
        rotateImageView.setImageResource(R.drawable.sticker_rotate);

        // Set layout parameters for the close ImageView
        RelativeLayout.LayoutParams rotateParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
        rotateParams.addRule(RelativeLayout.ALIGN_BOTTOM, stickerImageView.getId());
        rotateParams.addRule(RelativeLayout.ALIGN_START, stickerImageView.getId());
        rotateImageView.setLayoutParams(rotateParams);

        closeButtons.add(rotateImageView);
        closeButtons.add(zoomImageView);
        closeButtons.add(closeImageView);

        relativeLayout.addView(rotateImageView);
        relativeLayout.addView(zoomImageView);
        relativeLayout.addView(closeImageView);
        relativeLayout.addView(stickerImageView);
        constraint.addView(relativeLayout,constraint.indexOfChild(constraintThumbnail));
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        // Save initial touch point and view position
                        initialX = (int) v.getX(); // Change 'view' to 'v'
                        initialY = (int) v.getY(); // Change 'view' to 'v'
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Calculate new position based on touch movement
                        int newX = initialX + (int) (event.getRawX() - initialTouchX);
                        int newY = initialY + (int) (event.getRawY() - initialTouchY);

                        // Update view position
                        v.setX(newX); // Change 'view' to 'v'
                        v.setY(newY); // Change 'view' to 'v'
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;
                    case MotionEvent.ACTION_UP:
                        handleCloseButtons(true);
                        closeImageView.setVisibility(VISIBLE);
                        zoomImageView.setVisibility(VISIBLE);
                        rotateImageView.setVisibility(VISIBLE);

                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            // Perform click action here if needed
                        }
                        lastAction = MotionEvent.ACTION_UP;
                        break;
                }
                return true;
            }
        });

        zoomImageView.setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY;
            private float initialWidth, initialHeight;
            private boolean isResizing = false;
            private final int MIN_SIZE = 100; // Minimum size in pixels

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startY = event.getRawY();
                        initialWidth = stickerImageView.getWidth(); // Get the initial width of the sticker image
                        initialHeight = stickerImageView.getHeight(); // Get the initial height of the sticker image
                        isResizing = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isResizing) {
                            float currentX = event.getRawX();
                            float currentY = event.getRawY();

                            // Calculate the distance from the initial touch point
                            float distanceX = currentX - startX;
                            float distanceY = currentY - startY;

                            // Calculate the new width and height based on the distance
                            float newWidth = initialWidth + distanceX;
                            float newHeight = initialHeight + distanceY;

                            // Constrain the new size within the min limit
                            newWidth = Math.max(MIN_SIZE, newWidth);
                            newHeight = Math.max(MIN_SIZE, newHeight);

                            // Set the new size for the sticker image
                            stickerImageView.setLayoutParams(new RelativeLayout.LayoutParams((int) newWidth, (int) newHeight));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        isResizing = false;
                        break;
                }
                return true;
            }
        });


        rotateImageView.setOnTouchListener(new View.OnTouchListener() {
            private float previousX, previousY; // Previous touch position
            private boolean isRotating = false; // Flag to track if rotation is currently happening

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Finger is pressed, start rotation
                        previousX = event.getX();
                        previousY = event.getY();
                        isRotating = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Finger is moving, continue rotation
                        if (isRotating) {
                            float currentX = event.getX();
                            float currentY = event.getY();

                            // Calculate angle between previous and current touch position
                            double startAngle = Math.atan2(previousY - relativeLayout.getHeight() / 2, previousX - relativeLayout.getWidth() / 2);
                            double currentAngle = Math.atan2(currentY - relativeLayout.getHeight() / 2, currentX - relativeLayout.getWidth() / 2);
                            double rotationAngle = Math.toDegrees(currentAngle - startAngle);

                            // Apply the rotation to the relativeLayout
                            relativeLayout.setRotation((float) (relativeLayout.getRotation() + rotationAngle));

                            // Update previous touch position
                            previousX = currentX;
                            previousY = currentY;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Finger is released or touch is canceled, stop rotation
                        isRotating = false;
                        break;
                }
                return true;
            }
        });

    }


    public void createImage(Context context, Uri selectedImage) {
        // Create a new RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(context);

        // Set layout parameters for the RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,  // Width
                RelativeLayout.LayoutParams.WRAP_CONTENT   // Height
        );
        relativeLayout.setLayoutParams(layoutParams);

        // Create the sticker ImageView
        ImageView stickerImageView = new ImageView(context);
        stickerImageView.setId(View.generateViewId()); // Generate a unique ID for each view
        stickerImageView.setImageURI(selectedImage);
        // Set layout parameters for the sticker ImageView
        RelativeLayout.LayoutParams stickerParams = new RelativeLayout.LayoutParams(
                450, // Width
                450  // Height
        );

        stickerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        stickerParams.setMargins(20,20,20,20);

        stickerImageView.setLayoutParams(stickerParams);
        stickerImageView.setZ(-Float.MAX_VALUE);

        ImageView closeImageView = new ImageView(context);
        closeImageView.setId(View.generateViewId());
        closeImageView.setImageResource(R.drawable.icon_close);

// Set layout parameters for the close ImageView
        RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
//        closeParams.setMargins(0,-100,0,0);
        closeParams.addRule(RelativeLayout.ALIGN_TOP, stickerImageView.getId());
        closeParams.addRule(RelativeLayout.ALIGN_START, stickerImageView.getId());
        closeImageView.setLayoutParams(closeParams);

        ImageView flipImageView = new ImageView(context);
        flipImageView.setId(View.generateViewId()); // Generate a unique ID for each view
        flipImageView.setImageResource(R.drawable.sticker_flip);

        // Set layout parameters for the close ImageView
        RelativeLayout.LayoutParams flipParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
        flipParams.addRule(RelativeLayout.ALIGN_TOP, stickerImageView.getId());
        flipParams.addRule(RelativeLayout.ALIGN_END, stickerImageView.getId());
        flipImageView.setLayoutParams(flipParams);

        ImageView zoomImageView = new ImageView(context);
        zoomImageView.setId(View.generateViewId()); // Generate a unique ID for each view
        zoomImageView.setImageResource(R.drawable.sticker_scale);

        // Set layout parameters for the close ImageView
        RelativeLayout.LayoutParams zoomParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
        zoomParams.addRule(RelativeLayout.ALIGN_BOTTOM, stickerImageView.getId());
        zoomParams.addRule(RelativeLayout.ALIGN_END, stickerImageView.getId());
        zoomImageView.setLayoutParams(zoomParams);

        ImageView rotateImageView = new ImageView(context);
        rotateImageView.setId(View.generateViewId()); // Generate a unique ID for each view
        rotateImageView.setImageResource(R.drawable.sticker_rotate);

        // Set layout parameters for the close ImageView
        RelativeLayout.LayoutParams rotateParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
        rotateParams.addRule(RelativeLayout.ALIGN_BOTTOM, stickerImageView.getId());
        rotateParams.addRule(RelativeLayout.ALIGN_START, stickerImageView.getId());
        rotateImageView.setLayoutParams(rotateParams);

        closeButtons.add(flipImageView);
        closeButtons.add(rotateImageView);
        closeButtons.add(zoomImageView);
        closeButtons.add(closeImageView);

        relativeLayout.addView(flipImageView);
        relativeLayout.addView(rotateImageView);
        relativeLayout.addView(zoomImageView);
        relativeLayout.addView(closeImageView);
        relativeLayout.addView(stickerImageView);
        constraint.addView(relativeLayout,constraint.indexOfChild(constraintThumbnail));

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            private float xDelta;
            private float yDelta;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final float x = event.getRawX();
                final float y = event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        // Save the initial touch coordinates
                        xDelta = x - view.getX();
                        yDelta = y - view.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // Update the image view's position based on finger movement
                        view.setX(x - xDelta);
                        view.setY(y - yDelta);
                        break;
                    case MotionEvent.ACTION_UP:
                        handleCloseButtons(true);
                        closeImageView.setVisibility(VISIBLE);
                        flipImageView.setVisibility(VISIBLE);
                        zoomImageView.setVisibility(VISIBLE);
                        rotateImageView.setVisibility(VISIBLE);
                        break;
                }

                return true;
            }
        });
        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the parent RelativeLayout when the close button is clicked
                constraint.removeView(relativeLayout);

            }
        });

        flipImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipImage(stickerImageView);

            }
        });
        zoomImageView.setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY;
            private float initialWidth, initialHeight;
            private boolean isResizing = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startY = event.getRawY();
                        initialWidth = stickerImageView.getWidth(); // Get the initial width of the sticker image
                        initialHeight = stickerImageView.getHeight(); // Get the initial height of the sticker image
                        isResizing = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isResizing) {
                            float currentX = event.getRawX();
                            float currentY = event.getRawY();

                            // Calculate the distance from the initial touch point
                            float distanceX = currentX - startX;
                            float distanceY = currentY - startY;

                            // Calculate the change in size based on the distance
                            float newSize = Math.max(initialWidth + distanceX, 0);

                            // Set the new size for the sticker image
                            stickerImageView.setLayoutParams(new RelativeLayout.LayoutParams((int) newSize, (int) newSize));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        isResizing = false;
                        break;
                }
                return true;
            }
        });

        rotateImageView.setOnTouchListener(new View.OnTouchListener() {
            private float previousX, previousY; // Previous touch position
            private boolean isRotating = false; // Flag to track if rotation is currently happening

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Finger is pressed, start rotation
                        previousX = event.getX();
                        previousY = event.getY();
                        isRotating = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Finger is moving, continue rotation
                        if (isRotating) {
                            float currentX = event.getX();
                            float currentY = event.getY();

                            // Calculate angle between previous and current touch position
                            double startAngle = Math.atan2(previousY - relativeLayout.getHeight() / 2, previousX - relativeLayout.getWidth() / 2);
                            double currentAngle = Math.atan2(currentY - relativeLayout.getHeight() / 2, currentX - relativeLayout.getWidth() / 2);
                            double rotationAngle = Math.toDegrees(currentAngle - startAngle);

                            // Apply the rotation to the relativeLayout
                            relativeLayout.setRotation((float) (relativeLayout.getRotation() + rotationAngle));

                            // Update previous touch position
                            previousX = currentX;
                            previousY = currentY;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Finger is released or touch is canceled, stop rotation
                        isRotating = false;
                        break;
                }
                return true;
            }
        });


    }


    public void createText(Context context, String text, int fontColor) {

        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setId(View.generateViewId());
        // Set layout parameters for the RelativeLayout
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,  // Width
                RelativeLayout.LayoutParams.WRAP_CONTENT   // Height
        );
        relativeLayout.setLayoutParams(layoutParams);



        LinearLayout linearLayoutText = new LinearLayout(context);
        linearLayoutText.setId(View.generateViewId());
        // Set layout parameters for the RelativeLayout
        RelativeLayout.LayoutParams linearLayoutTextParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,  // Width
                RelativeLayout.LayoutParams.WRAP_CONTENT   // Height
        );
        linearLayoutText.setLayoutParams(linearLayoutTextParams);
        linearLayoutText.setPadding(50,50,50,50);
        TextView textView = new TextView(context);
        File file1 = new File(Configure.GetFileDir(context).getPath() + File.separator + "font");
        textView.setIncludeFontPadding(false);
        textView.setLineSpacing(1, 0.8f);
        textView.setTypeface(Typeface.createFromFile(file1.getAbsolutePath() + "/" + selectedFontFamily));
        textView.setId(View.generateViewId()); // Generate a unique ID for each view
        textView.setText(text);
        textView.setTextColor(fontColor);
        textView.setTextSize(selectedFontSize); // Set text size as needed

        // Set layout parameters for the text view
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParams.gravity = Gravity.CENTER; // Center the TextView in the LinearLayout
        textView.setLayoutParams(textParams);

        ImageView closeImageView = new ImageView(context);
        closeImageView.setId(View.generateViewId());
        closeImageView.setImageResource(R.drawable.icon_close);
        RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
        closeParams.addRule(RelativeLayout.ALIGN_TOP, linearLayoutText.getId());
        closeParams.addRule(RelativeLayout.ALIGN_START, linearLayoutText.getId());
        closeImageView.setLayoutParams(closeParams);

        ImageView dragImageView = new ImageView(context);
        dragImageView.setId(View.generateViewId());
        dragImageView.setImageResource(R.drawable.pen);
        RelativeLayout.LayoutParams dragParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
        dragParams.addRule(RelativeLayout.ALIGN_TOP, linearLayoutText.getId());
        dragParams.addRule(RelativeLayout.ALIGN_END, linearLayoutText.getId());
        dragImageView.setLayoutParams(dragParams);

        ImageView zoomImageView = new ImageView(context);
        zoomImageView.setId(View.generateViewId()); // Generate a unique ID for each view
        zoomImageView.setImageResource(R.drawable.sticker_scale);

        // Set layout parameters for the close ImageView
        RelativeLayout.LayoutParams zoomParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
        zoomParams.addRule(RelativeLayout.ALIGN_BOTTOM, linearLayoutText.getId());
        zoomParams.addRule(RelativeLayout.ALIGN_END, linearLayoutText.getId());
        zoomImageView.setLayoutParams(zoomParams);

        ImageView rotateImageView = new ImageView(context);
        rotateImageView.setId(View.generateViewId()); // Generate a unique ID for each view
        rotateImageView.setImageResource(R.drawable.sticker_rotate);

        // Set layout parameters for the close ImageView
        RelativeLayout.LayoutParams rotateParams = new RelativeLayout.LayoutParams(
                70,
                70
        );
        rotateParams.addRule(RelativeLayout.ALIGN_BOTTOM, linearLayoutText.getId());
        rotateParams.addRule(RelativeLayout.ALIGN_START, linearLayoutText.getId());
        rotateImageView.setLayoutParams(rotateParams);
        linearLayoutText.addView(textView);
        relativeLayout.addView(linearLayoutText);
        relativeLayout.addView(dragImageView);
        relativeLayout.addView(rotateImageView);
        relativeLayout.addView(zoomImageView);
        relativeLayout.addView(closeImageView);

        closeButtons.add(rotateImageView);
        closeButtons.add(zoomImageView);
        closeButtons.add(closeImageView);

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the parent LinearLayout when the close button is clicked
                constraint.removeView(relativeLayout);
            }
        });

        dragImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the parent LinearLayout when the close button is clicked

//                showFontFamilyBottomSheet(textView);
                EditTextItemDialogFragment editTextFragment = new EditTextItemDialogFragment();
                editTextFragment.show(getSupportFragmentManager(), editTextFragment.getTag());
                editTextFragment.setInitialText(textView.getText().toString());
                editTextFragment.setOnSubmitListener(new EditTextItemDialogFragment.OnSubmitListener() {
                    @Override
                    public void onSubmit(String userInput) {
                        textView.setText(userInput);
                    }
                });

                editTextFragment.setColorListener(new EditTextItemDialogFragment.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        textView.setTextColor(color);
                    }
                });

                editTextFragment.setFontListener(new EditTextItemDialogFragment.OnFontFamilyListener() {
                    @Override
                    public void onFamilySelected(String fontFamily) {
                        textView.setTypeface(Typeface.createFromFile(file1.getAbsolutePath() + "/" + fontFamily));
                    }
                });

            }
        });

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            private int lastAction;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        // Save initial touch point and view position
                        initialX = (int) v.getX();
                        initialY = (int) v.getY();
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Calculate new position based on touch movement
                        int newX = initialX + (int) (event.getRawX() - initialTouchX);
                        int newY = initialY + (int) (event.getRawY() - initialTouchY);

                        // Update view position
                        v.setX(newX);
                        v.setY(newY);
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;
                    case MotionEvent.ACTION_UP:
                        handleCloseButtons(true);
                        closeImageView.setVisibility(VISIBLE);
                        dragImageView.setVisibility(VISIBLE);
                        zoomImageView.setVisibility(VISIBLE);
                        rotateImageView.setVisibility(VISIBLE);
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            // Perform click action here if needed
                        }
                        lastAction = MotionEvent.ACTION_UP;
                        break;
                }
                return true;
            }
        });

//        dragImageView.setOnTouchListener(new View.OnTouchListener() {
//            private int lastAction;
//            private int initialX;
//            private int initialY;
//            private float initialTouchX;
//            private float initialTouchY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getActionMasked()) {
//                    case MotionEvent.ACTION_DOWN:
//                        // Save initial touch point and view position
//                        initialX = (int) relativeLayout.getX();
//                        initialY = (int) relativeLayout.getY();
//                        initialTouchX = event.getRawX();
//                        initialTouchY = event.getRawY();
//                        lastAction = MotionEvent.ACTION_DOWN;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        // Calculate new position based on touch movement
//                        int newX = initialX + (int) (event.getRawX() - initialTouchX);
//                        int newY = initialY + (int) (event.getRawY() - initialTouchY);
//
//                        // Update view position
//                        relativeLayout.setX(newX);
//                        relativeLayout.setY(newY);
//                        lastAction = MotionEvent.ACTION_MOVE;
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        // Check if it was a click or drag action
//                        if (lastAction == MotionEvent.ACTION_DOWN) {
//                            // Perform click action here if needed
//                        }
//                        lastAction = MotionEvent.ACTION_UP;
//                        break;
//                }
//                return true;
//            }
//        });


//        zoomImageView.setOnTouchListener(new View.OnTouchListener() {
//            private float startX, startY;
//            private float initialTextSize;
//            private boolean isZooming = false;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startX = event.getRawX();
//                        startY = event.getRawY();
//                        initialTextSize = textView.getTextSize();
//                        isZooming = true;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        if (isZooming) {
//                            float currentX = event.getRawX();
//                            float currentY = event.getRawY();
//
//                            // Calculate the distance from the initial touch point
//                            float distanceX = currentX - startX;
//                            float distanceY = currentY - startY;
//
//                            // Calculate the total distance
//                            float totalDistance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
//
//                            // Calculate the change in text size based on the distance
//                            float newSize = initialTextSize + totalDistance * 0.02f; // Adjust this multiplier as needed for sensitivity
//
//                            // Set the new text size
//                            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, newSize);
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        isZooming = false;
//                        break;
//                }
//                return true;
//            }
//        });

        zoomImageView.setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY;
            private float initialTextSize;
            private boolean isResizing = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startY = event.getRawY();
                        initialTextSize = textView.getTextSize();
                        isResizing = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isResizing) {
                            float currentX = event.getRawX();
                            float currentY = event.getRawY();

                            // Calculate the distance from the initial touch point
                            float distanceX = currentX - startX;
                            float distanceY = currentY - startY;

                            // Calculate the change in size based on the distance
                            float newSize = initialTextSize + (distanceX + distanceY) / 4;

                            // Limit the minimum size
                            newSize = Math.max(newSize, 30);

                            // Set the new text size
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, newSize);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        isResizing = false;
                        break;
                }
                return true;
            }
        });

        rotateImageView.setOnTouchListener(new View.OnTouchListener() {
            private float previousX, previousY; // Previous touch position
            private boolean isRotating = false; // Flag to track if rotation is currently happening

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Finger is pressed, start rotation
                        previousX = event.getX();
                        previousY = event.getY();
                        isRotating = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Finger is moving, continue rotation
                        if (isRotating) {
                            float currentX = event.getX();
                            float currentY = event.getY();

                            // Calculate angle between previous and current touch position
                            double startAngle = Math.atan2(previousY - relativeLayout.getHeight() / 2, previousX - relativeLayout.getWidth() / 2);
                            double currentAngle = Math.atan2(currentY - relativeLayout.getHeight() / 2, currentX - relativeLayout.getWidth() / 2);
                            double rotationAngle = Math.toDegrees(currentAngle - startAngle);

                            // Apply the rotation to the relativeLayout
                            relativeLayout.setRotation((float) (relativeLayout.getRotation() + rotationAngle));

                            // Update previous touch position
                            previousX = currentX;
                            previousY = currentY;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        // Finger is released or touch is canceled, stop rotation
                        isRotating = false;
                        break;
                }
                return true;
            }
        });




        closeButtons.add(dragImageView);
        closeButtons.add(rotateImageView);
        closeButtons.add(zoomImageView);
        closeButtons.add(closeImageView);


        constraint.addView(relativeLayout, constraint.indexOfChild(constraintThumbnail));
    }

    private void handleCloseButtons(boolean makeInvisible) {
        for (ImageView closeButton : closeButtons) {
            if (makeInvisible) {
                // Make the close button invisible
                closeButton.setVisibility(View.INVISIBLE);
            } else {
                // Make the close button visible
                closeButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showColorPicker() {
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
                        slectedFontColor = colorPickerView.getColor();
                        etText.setTextColor(slectedFontColor);

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();

        // Show the AlertDialog
        colorPickerDialog.show();
    }

    private void showDownloadDialog() {
        // Inflate the custom layout for the color picker
        View view2 = LayoutInflater.from(this).inflate(R.layout.download_dialog, null);

        // Build the AlertDialog
        android.app.AlertDialog colorPickerDialog = new android.app.AlertDialog.Builder(this)
                .setView(view2)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handleCloseButtons(true);
                        saveImage(viewToBitmap(constraintTwo), true);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rightThumbnailImage.setVisibility(View.GONE);
                        leftThumbnailImage.setVisibility(View.GONE);
                        centerThumbnailImage.setVisibility(View.GONE);
                    }
                })
                .create();
        colorPickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                colorPickerDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                colorPickerDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
            }
        });

        // Show the AlertDialog
        colorPickerDialog.show();
    }

    private void openWhatsAppChat(String phoneNumber, String message) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + URLEncoder.encode(message, "UTF-8"));
            intent.setData(uri);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkSubscriptionPlansExpireDialog() {
        String todayDateTime = new SimpleDateFormat(Constant.TODAY_DATE_PATTERN, Locale.getDefault()).format(System.currentTimeMillis());
        String planEndDate = preferenceManager.getString(Constant.PLAN_END_DATE);

        if (planEndDate != null && !planEndDate.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.TODAY_DATE_PATTERN, Locale.getDefault());
            try {
                Date todayDate = dateFormat.parse(todayDateTime);
                Date endDate = dateFormat.parse(planEndDate);

                if (endDate != null && endDate.before(todayDate)) {
                    // Your plan got expired. Show dialog
                    universalDialog.showWarningDialog(getString(R.string.upgrade), getString(R.string.your_plan_expired),
                            getString(R.string.upgrade), true);
                    universalDialog.show();
                    universalDialog.okBtn.setOnClickListener(view -> {
                        universalDialog.cancel();
                        startActivity(new Intent(this, SubsPlanActivity.class));
                    });

                    universalDialog.cancelBtn.setOnClickListener(view -> universalDialog.cancel());

                }else{
                    if (watermarkDetails.position != null && watermarkDetails.showWatermark != null) {
                        if (watermarkDetails.position == 1) {
                            leftThumbnailImage.setVisibility(View.VISIBLE);
                        } else if (watermarkDetails.position == 2) {
                            centerThumbnailImage.setVisibility(View.VISIBLE);
                        } else if (watermarkDetails.position == 3) {
                            rightThumbnailImage.setVisibility(View.VISIBLE);
                        }

                    }
                    showDownloadDialog();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}