package com.sanskruti.volotek.ui.fragments;

import static android.view.View.GONE;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.adapters.BottomAdapter;
import com.sanskruti.volotek.adapters.BusinessListAdapter;
import com.sanskruti.volotek.adapters.SpecialFramesAdater;
import com.sanskruti.volotek.api.ApiClient;
import com.sanskruti.volotek.binding.GlideDataBinding;
import com.sanskruti.volotek.custom.poster.activity.ThumbnailActivity;
import com.sanskruti.volotek.listener.DismisListner;
import com.sanskruti.volotek.model.BusinessItem;
import com.sanskruti.volotek.model.FrameModel;
import com.sanskruti.volotek.model.ItemPolitical;
import com.sanskruti.volotek.model.UserItem;
import com.sanskruti.volotek.ui.activities.AddBusinessActivity;
import com.sanskruti.volotek.ui.activities.EditBusinessProfileDetailsActivity;
import com.sanskruti.volotek.ui.activities.EditPersonalProfileDetailsActivity;
import com.sanskruti.volotek.ui.activities.EditPoliticalProfileDetailsActivity;
import com.sanskruti.volotek.ui.activities.PoliticalProfileDetailsEditActivity;
import com.sanskruti.volotek.ui.activities.ProfileEditActivity;
import com.sanskruti.volotek.ui.dialog.UniversalDialog;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.MyUtils;
import com.sanskruti.volotek.utils.PreferenceManager;
import com.sanskruti.volotek.viewmodel.UserViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBottomSheetFragment extends BottomSheetDialogFragment implements BottomAdapter.OnItemClickListener {
    UserItem userItem;
    Activity context;
    String image_url;

    RecyclerView political_RV;
    PreferenceManager preferenceManager;
    JSONArray jsonArrayModel;
    BottomAdapter featureAdapter;
    // Constructor to receive data
    List<ItemPolitical> items = new ArrayList<>();
    LinearLayout iv_edit_politicalll, iv_edit_per, iv_edit_politicalllbus, lineDataiv, contact_whatsapp;

    RelativeLayout toolbarpm, toolbarbus, toolbarppm, toolbaspecSp;
    DismisListner dismisListner;
    TextView tvtitletvtwol;
    private StaggeredGridLayoutManager layoutManager;

    public MyBottomSheetFragment(DismisListner dismisListner) {
        this.dismisListner = dismisListner;
    }

    @Override
    public void onItemClick(int position) {
        // Handle item click
        // Close or hide the bottom sheet here
        dismiss();
    }

    @Override
    public void onDeleteClick(String pProfileId) {
        Log.i("RESPONSEGetAllData", "onDeleteClick  = ");
        Toast.makeText(context, pProfileId, Toast.LENGTH_SHORT).show();

        deletePoliticalProfile(pProfileId);
    }







    private String type;

    private boolean greeting;
    public MyBottomSheetFragment(String image_url, Activity context, String typeOpen, boolean greetingNew) {
//        this.data = data;
        this.userItem = Constant.getUserItem(context);
        this.context = context;
        this.image_url = image_url;
        preferenceManager = new PreferenceManager(this.context);
        this.type = typeOpen;
        this.greeting = greetingNew;


    }

    private BusinessListAdapter getBusinessAdapter;
    private StaggeredGridLayoutManager recyclerViewLayoutManager;
    private ArrayList<BusinessItem> businessItemArrayList;

    private void loadBusinessData() {
        ApiClient.getApiDataService().getBusinessList(preferenceManager.getString(Constant.USER_ID), "business_list").enqueue
                (new Callback<List<BusinessItem>>() {
                    @Override
                    public void onResponse(Call<List<BusinessItem>> call, Response<List<BusinessItem>> response) {

                        if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {

                            businessItemArrayList.addAll(response.body());
                            recyclerViewLayoutManager = new StaggeredGridLayoutManager(1, 1);
                            business_rvbus.setLayoutManager(recyclerViewLayoutManager);



                            getBusinessAdapter = new BusinessListAdapter(context, businessItemArrayList, (view, i) -> {

                                if (type.equalsIgnoreCase("bus")) {

                                    Constant.businessItem = businessItemArrayList.get(i);

                                    Intent intent = new Intent(context, AddBusinessActivity.class);
                                    intent.putExtra("Action", "Update");
                                    context.startActivity(intent);
                                    dismiss();
                                } else {
                                    Intent intent = new Intent(context, EditBusinessProfileDetailsActivity/*ThumbnailActivity*/.class);
                                    intent.putExtra("backgroundImage", image_url);
                                    intent.putExtra("type", "images");
                                    intent.putExtra("sizeposition", "1:1");

                                    intent.putExtra("greeting",greeting);
                                    intent.putExtra("index", String.valueOf(0));
                                    intent.putExtra("img", image_url);
                                //    intent.putExtra("profileId", /*item.profileId*/222);

                                    intent.putExtra("party", Constant.businessItem.logo);
                                    intent.putExtra("mobile", Constant.businessItem.phone);
                                    intent.putExtra("name", Constant.businessItem.name);
                                    intent.putExtra("address",  Constant.businessItem.address);
                                    context.startActivity(intent);
                                    dismiss();
                                }


                            });


                            business_rvbus.setAdapter(getBusinessAdapter);
                            getBusinessAdapter.notifyDataSetChanged();
                        } else {


                        }

                    }

                    @Override
                    public void onFailure(Call<List<BusinessItem>> call, Throwable t) {

                      /*  if (businessItemArrayList.size() == 0) {
                            iv_edit_politicalllbus.setVisibility(View.VISIBLE);
                        } else {
                            iv_edit_politicalllbus.setVisibility(GONE);
                        }*/
                        Toast.makeText(context, "Failed = " + t, Toast.LENGTH_SHORT).show();


                    }
                });
    }

    private RecyclerView business_rvbus, rvSpec;
    List<FrameModel> postItemList;
    private TextView bustv, potvtv;
    private LinearLayout ll, linearLayout10ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        businessItemArrayList = new ArrayList<>();
        // Set up your bottom sheet views and buttons here
        CircularImageView profileImg = view.findViewById(R.id.circularImageView);

        linearLayout10ll = view.findViewById(R.id.linearLayout10);
        TextView txtName = view.findViewById(R.id.txt_name);
        postItemList = new ArrayList<>();
        toolbarpm = view.findViewById(R.id.toolbarp);
        toolbarbus = view.findViewById(R.id.toolbar);
        toolbarppm = view.findViewById(R.id.toolbarpp);
        toolbaspecSp = view.findViewById(R.id.toolbaspec);

        tvtitletvtwol = view.findViewById(R.id.titletvtwo);
        iv_edit_per = view.findViewById(R.id.iv_edit);
        rvSpec = view.findViewById(R.id.rv_post);

        lineDataiv = view.findViewById(R.id.lineData);
        iv_edit_politicalll = view.findViewById(R.id.iv_edit_political);

        iv_edit_politicalllbus = view.findViewById(R.id.iv_edit_politicalbus);

        contact_whatsapp = view.findViewById(R.id.contact_whatsapp);

        bustv = view.findViewById(R.id.title_tv);
        potvtv = view.findViewById(R.id.potv);
        ll = view.findViewById(R.id.profilePoliciy);



        // Business Profiles
        business_rvbus = view.findViewById(R.id.recyclerViewTwo);


        // Political Profiles
        political_RV = view.findViewById(R.id.recyclerView);
        loadBusinessData();

        getSpecialFrame(Constant.FRAME_TYPE_IMAGE);
        getDataShare();
        setVisibility();

        //  Log.i("getJSONData", "userDataString length = " + String.valueOf(items.size()));

        iv_edit_per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ProfileEditActivity.class));
            }
        });

        linearLayout10ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditPersonalProfileDetailsActivity.class);
                intent.putExtra("backgroundImage", image_url);
                intent.putExtra("type", "images");
                intent.putExtra("sizeposition", "1:1");

                intent.putExtra("index", String.valueOf(0));
                intent.putExtra("img", image_url);
                //    intent.putExtra("profileId", /*item.profileId*/222);

                intent.putExtra("userImg", userItem.getUserImage());
                intent.putExtra("name", userItem.getUserName());
                intent.putExtra("email",  userItem.getEmail());
                intent.putExtra("greeting",greeting);
                context.startActivity(intent);
                dismiss();
            }
        });





        political_RV.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                //
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                //
            }
        });

        GlideDataBinding.bindImage(profileImg, userItem.getUserImage());
        txtName.setText(userItem.getUserName());
        iv_edit_politicalll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.size() >= 2) {
                    Toast.makeText(context, "Already created 2 profiles!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(context, PoliticalProfileDetailsEditActivity.class));
                }
            }
        });
        iv_edit_politicalllbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (businessItemArrayList.size() >= 2) {
                    Log.v("saqlain","In Gone"+businessItemArrayList.size());
                    Toast.makeText(context, "Already created 2 profiles!", Toast.LENGTH_SHORT).show();
//                    iv_edit_politicalllbus.setVisibility(GONE);
                } else {
                    Log.v("saqlain","In Visible"+businessItemArrayList.size());
//                    iv_edit_politicalllbus.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(context, AddBusinessActivity.class);
                intent.putExtra("Action", "Insert");
                startActivity(intent);
                }




            }
        });

        contact_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace "1234567890" with the recipient's phone number, including the country code
                String phoneNumber = "+918553537373";
                String message = "नमस्कार, स्पेशल फ्रेम हवी आहे।";
                openWhatsAppChat(phoneNumber,message);
            }
        });

        /*ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ThumbnailActivity.class);
                intent.putExtra("backgroundImage", image_url);
                intent.putExtra("type", "images");
                intent.putExtra("sizeposition", "1:1");
                context.startActivity(intent);
                dismiss();
            }
        });*/
        return view;
    }

    private void setVisibility(){
        if (type.equalsIgnoreCase("profile")) {

            business_rvbus.setVisibility(View.GONE);
            political_RV.setVisibility(View.VISIBLE);
            bustv.setVisibility(View.GONE);
            toolbarbus.setVisibility(View.GONE);
            toolbarpm.setVisibility(View.VISIBLE);
            toolbarppm.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
            toolbaspecSp.setVisibility(View.GONE);
            rvSpec.setVisibility(View.GONE);

            iv_edit_politicalll.setVisibility(View.VISIBLE);
            lineDataiv.setVisibility(GONE);
            iv_edit_politicalllbus.setVisibility(GONE);
        } else if (type.equalsIgnoreCase("NA")) {
            bustv.setVisibility(View.VISIBLE);
            toolbarbus.setVisibility(View.VISIBLE);
            business_rvbus.setVisibility(View.VISIBLE);
            political_RV.setVisibility(View.VISIBLE);
            toolbarpm.setVisibility(View.VISIBLE);
            toolbarppm.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
            toolbaspecSp.setVisibility(View.VISIBLE);
            rvSpec.setVisibility(View.VISIBLE);

            iv_edit_politicalll.setVisibility(View.VISIBLE);
            lineDataiv.setVisibility(View.VISIBLE);
            iv_edit_politicalllbus.setVisibility(View.VISIBLE);
        } else {
            toolbarpm.setVisibility(View.GONE);
            toolbarbus.setVisibility(View.VISIBLE);
            bustv.setVisibility(View.VISIBLE);
            political_RV.setVisibility(View.GONE);
            business_rvbus.setVisibility(View.VISIBLE);
            toolbarppm.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
            toolbaspecSp.setVisibility(View.GONE);
            rvSpec.setVisibility(View.GONE);

            iv_edit_politicalll.setVisibility(GONE);
            lineDataiv.setVisibility(GONE);
            iv_edit_politicalllbus.setVisibility(View.VISIBLE);
        }
        if (items != null) {
            if (items.size() > 0) {
                if (type.equalsIgnoreCase("profile")) {
                    iv_edit_politicalll.setVisibility(View.VISIBLE);
                    lineDataiv.setVisibility(View.GONE);
                    iv_edit_politicalllbus.setVisibility(View.GONE);
                } else if (type.equalsIgnoreCase("bus")) {
                    iv_edit_politicalll.setVisibility(View.GONE);
                    lineDataiv.setVisibility(View.GONE);
                    iv_edit_politicalllbus.setVisibility(View.VISIBLE);
                } else {
                    iv_edit_politicalll.setVisibility(View.VISIBLE);
                    lineDataiv.setVisibility(View.VISIBLE);
                    iv_edit_politicalllbus.setVisibility(View.VISIBLE);
                }

            } else {
                if (type.equalsIgnoreCase("NA")) {
                    toolbarpm.setVisibility(View.GONE);
                }
                if (type.equalsIgnoreCase("profile")) {
                    iv_edit_politicalll.setVisibility(View.VISIBLE);
                    lineDataiv.setVisibility(View.GONE);
                    iv_edit_politicalllbus.setVisibility(View.GONE);
                } else if (type.equalsIgnoreCase("bus")) {
                    iv_edit_politicalll.setVisibility(View.GONE);
                    lineDataiv.setVisibility(View.GONE);
                    iv_edit_politicalllbus.setVisibility(View.VISIBLE);
                } else {
                    iv_edit_politicalll.setVisibility(View.VISIBLE);
                    lineDataiv.setVisibility(View.VISIBLE);
                    iv_edit_politicalllbus.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    String actualRatio = "1:1";
    SpecialFramesAdater adapter;

    private void setupPreviewAdapter() {

    //    Toast.makeText(context, "greeting 2 = "+String.valueOf(greeting), Toast.LENGTH_SHORT).show();
        adapter = new SpecialFramesAdater(getActivity(), (data) -> {
//            Toast.makeText(context, "greeting 3 = "+String.valueOf(greeting), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, EditPoliticalProfileDetailsActivity.class);
            intent.putExtra("index", String.valueOf(0));
            intent.putExtra("img", image_url);
            intent.putExtra("imgThum", postItemList.get(data).getThumbnail());
            intent.putExtra("greeting",greeting);
            context.startActivity(intent);
        }, 3, getResources().getDimension(com.intuit.ssp.R.dimen._2ssp), postItemList);
        rvSpec.setAdapter(adapter);


        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        rvSpec.setLayoutManager(layoutManager);


    }

    private void getSpecialFrame(String frameType) {
        Constant.getHomeViewModel(this).getFrames(preferenceManager.getString(Constant.USER_ID), frameType, actualRatio).observe(this, frameResponse -> {


            if (frameResponse != null) {
                MyUtils.showResponse(frameResponse.framecategories);

                Log.i("checkframdata", "frameResponse = " + new Gson().toJson(frameResponse));


                if (frameResponse.framecategories.size() > 0 && frameResponse.framecategories.get(0).getFrameModels().size() > 0) {

                    FrameModel frameModel = new FrameModel();
                  frameModel.setThumbnail("https://androcoders.com/temp/sanskruti/common_desired_frame.png");
                    postItemList.add(frameModel);
                    for (int i = 0; i < frameResponse.framecategories.size(); i++) {
                        if (frameResponse.framecategories.get(i).getName().equalsIgnoreCase("Custom")) {
                            Log.i("checkframdata", "frameResponse inside = " + new Gson().toJson(frameResponse.framecategories.get(i)));

                            postItemList.addAll(frameResponse.framecategories.get(i).getFrameModels());
                            Log.i("checkframdata", "frameResponse size = " + String.valueOf(postItemList.size()));

                            if (postItemList.size() > 0) {
                                setupPreviewAdapter();
                            } else {
                                toolbaspecSp.setVisibility(GONE);
                            }
                        }
                    }


                }

            }

        });
    }
    private void getDataShare() {
        Log.i("RESPONSEGetAllData", "USER_ID-->" + preferenceManager.getString(Constant.USER_ID));
        Constant.getHomeViewModel(this).getAllPoliticalProfile(preferenceManager.getString(Constant.USER_ID)).observe(this, businessItem -> {


            if (businessItem != null) {
                if (items.size()>0){
                    items.clear();
                }
                Log.i("RESPONSEGetAllData", "RESPONSE-->" + new Gson().toJson(businessItem));
                Log.i("RESPONSEGetAllData", "RESPONSE-->" + new Gson().toJson(businessItem));

                for(int i=0;i<businessItem.profiles.size();i++){
                    String profileId = businessItem.profiles.get(i)._id;
                    String id = businessItem.profiles.get(i).pUserId;

                    String pName = businessItem.profiles.get(i).pName;
                    String pPhone = businessItem.profiles.get(i).pPhone;
                    String pEmail = businessItem.profiles.get(i).pEmail;
                    String pFacebookUsername = businessItem.profiles.get(i).pFacebookUsername;
                    String pInstagramUsername = businessItem.profiles.get(i).pInstagramUsername;
                    String pTwitterUsername = businessItem.profiles.get(i).pTwitterUsername;

                    String pDesignation1 = businessItem.profiles.get(i).pDesignation1;
                    String pDesignation2 = businessItem.profiles.get(i).pDesignation2;

                    String pProfileImg = businessItem.profiles.get(i).pProfileImg;
                    String pPartyImg = businessItem.profiles.get(i).pPartyImg;
                    String pLeaderImg1 = businessItem.profiles.get(i).pLeaderImg1;
                    String pLeaderImg2 = businessItem.profiles.get(i).pLeaderImg2;
                    String pLeaderImg3 = businessItem.profiles.get(i).pLeaderImg3;
                    String pLeaderImg4 = businessItem.profiles.get(i).pLeaderImg4;
                    String pLeaderImg5 = businessItem.profiles.get(i).pLeaderImg5;
                    String pLeaderImg6 = businessItem.profiles.get(i).pLeaderImg6;


                   items.add(new ItemPolitical(profileId,id, pName, pPhone, pEmail, pFacebookUsername, pInstagramUsername, pTwitterUsername,
                                pDesignation1, pDesignation2, pProfileImg, pPartyImg, pLeaderImg1, pLeaderImg2,
                                pLeaderImg3, pLeaderImg4, pLeaderImg5, pLeaderImg6));

                }
                Log.i("RESPONSEGetAllData", "RESPONSE Size-->" + String.valueOf(items.size())+", type = "+type);
                featureAdapter = new BottomAdapter(context, image_url, items, type,jsonArrayModel,this,greeting);
                featureAdapter.notifyDataSetChanged();
                political_RV.setLayoutManager(new LinearLayoutManager(context));
                political_RV.setAdapter(featureAdapter);
                political_RV.setNestedScrollingEnabled(false);

                setVisibility();
            }

        });

       /* preferenceManager.getString(Constant.USER_ID);
        String userDataString = preferenceManager.getStringTwo(Constant.USER_POLITICAL_PROFILE);
        // Retrieve JSONArray string from SharedPreferences
        items = new ArrayList<>();
        // Convert JSONArray string back to List<User>
        Log.i("getJSONData", "Bottom Sheet Get JSON Data = " + userDataString.toString());
        Log.i("getJSONData", "Bottom Sheet Get User ID = " + String.valueOf(preferenceManager.getString(Constant.USER_ID)));
        if (!userDataString.isEmpty()) {

            Log.i("getJSONData", "size 2  = " + String.valueOf(items.size()));
            try {
                jsonArrayModel = new JSONArray(userDataString);
                for (int i = 0; i < jsonArrayModel.length(); i++) {
                    JSONObject userObject = jsonArrayModel.getJSONObject(i);
                    String profileId = userObject.getString("profileId");
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

                    if(preferenceManager.getString(Constant.USER_ID).equalsIgnoreCase(id)){
                        items.add(new ItemPolitical(profileId,id, pName, pPhone, pEmail, pFacebookUsername, pInstagramUsername, pTwitterUsername,
                                pDesignation1, pDesignation2, pProfileImg, pPartyImg, pLeaderImg1, pLeaderImg2,
                                pLeaderImg3, pLeaderImg4, pLeaderImg5, pLeaderImg6));
                    }


                }
                Log.i("getJSONData", "JSON Size = " + String.valueOf(items.size()));
            } catch (JSONException e) {
                e.printStackTrace();
                FirebaseCrashlytics.getInstance().recordException(e);
                Log.i("getJSONData", "error two = " + e.getMessage().toString());
            }

        } else {
            Log.i("getJSONData", "userDataString two = " + userDataString.toString());
        }*/

    }





    private void deletePoliticalProfile(String profileId) {
        Log.i("RESPONSEGetAllData", "USER_ID-->" + preferenceManager.getString(Constant.USER_ID));
        Constant.getHomeViewModel(this).deletePoliticalProfile(profileId).observe(this, businessItem -> {


            if (businessItem != null) {
                Log.i("RESPONSEGetAllData", "RESPONSE-->" + new Gson().toJson(businessItem));
                Log.i("RESPONSEGetAllData", "RESPONSE-->" + new Gson().toJson(businessItem));

               if(businessItem.success){
                   getDataShare();
               }
            }

        });

       /* preferenceManager.getString(Constant.USER_ID);
        String userDataString = preferenceManager.getStringTwo(Constant.USER_POLITICAL_PROFILE);
        // Retrieve JSONArray string from SharedPreferences
        items = new ArrayList<>();
        // Convert JSONArray string back to List<User>
        Log.i("getJSONData", "Bottom Sheet Get JSON Data = " + userDataString.toString());
        Log.i("getJSONData", "Bottom Sheet Get User ID = " + String.valueOf(preferenceManager.getString(Constant.USER_ID)));
        if (!userDataString.isEmpty()) {

            Log.i("getJSONData", "size 2  = " + String.valueOf(items.size()));
            try {
                jsonArrayModel = new JSONArray(userDataString);
                for (int i = 0; i < jsonArrayModel.length(); i++) {
                    JSONObject userObject = jsonArrayModel.getJSONObject(i);
                    String profileId = userObject.getString("profileId");
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

                    if(preferenceManager.getString(Constant.USER_ID).equalsIgnoreCase(id)){
                        items.add(new ItemPolitical(profileId,id, pName, pPhone, pEmail, pFacebookUsername, pInstagramUsername, pTwitterUsername,
                                pDesignation1, pDesignation2, pProfileImg, pPartyImg, pLeaderImg1, pLeaderImg2,
                                pLeaderImg3, pLeaderImg4, pLeaderImg5, pLeaderImg6));
                    }


                }
                Log.i("getJSONData", "JSON Size = " + String.valueOf(items.size()));
            } catch (JSONException e) {
                e.printStackTrace();
                FirebaseCrashlytics.getInstance().recordException(e);
                Log.i("getJSONData", "error two = " + e.getMessage().toString());
            }

        } else {
            Log.i("getJSONData", "userDataString two = " + userDataString.toString());
        }*/

    }

    private void openWhatsAppChat(String phoneNumber, String message) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + URLEncoder.encode(message, "UTF-8"));
            intent.setData(uri);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "WhatsApp not installed", Toast.LENGTH_SHORT).show();
        }
    }

}
