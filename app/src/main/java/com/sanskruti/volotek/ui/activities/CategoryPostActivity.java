package com.sanskruti.volotek.ui.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.sanskruti.volotek.utils.Constant.BUSINESS;
import static com.sanskruti.volotek.utils.Constant.INTENT_POST_IMAGE;
import static com.sanskruti.volotek.utils.Constant.INTENT_TYPE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.sanskruti.volotek.MyApplication;
import com.sanskruti.volotek.adapters.PreviewAdapter;
import com.sanskruti.volotek.adapters.VideoAdapter;
import com.sanskruti.volotek.databinding.ActivityCategoryPostBinding;

import com.sanskruti.volotek.model.PostItem;
import com.sanskruti.volotek.model.VideoItem;
import com.sanskruti.volotek.ui.dialog.UniversalDialog;
import com.sanskruti.volotek.ui.fragments.MyBottomSheetFragment;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.MyUtils;
import com.sanskruti.volotek.utils.NetworkConnectivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoryPostActivity extends AppCompatActivity {

    private ActivityCategoryPostBinding binding;
    private NetworkConnectivity networkConnectivity;
    private UniversalDialog universalDialog;
    List<PostItem> postItemList;
    List<VideoItem> videoItemList;
    private String categoryID;
    private String type = "all";
    private PreviewAdapter adapter;
    private VideoAdapter videoAdapter;
    private Boolean isGreeting = false;
    private int position = 0;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCategoryPostBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(binding.getRoot());

        networkConnectivity = new NetworkConnectivity(this);
        universalDialog = new UniversalDialog(this, false);
        postItemList = new ArrayList<>();
        videoItemList = new ArrayList<>();
        binding.toolbar.back.setOnClickListener(v->onBackPressed());
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            binding.toolbar.toolName.setText(intent.getStringExtra(Constant.INTENT_FEST_NAME));
            categoryID = intent.getStringExtra(Constant.INTENT_FEST_ID);
            type = intent.getStringExtra(INTENT_TYPE);
            if(intent.getStringExtra(Constant.INTENT_PARENT_CAT_ID) != null){
                fetchPosts(intent.getStringExtra(Constant.INTENT_PARENT_CAT_ID),categoryID,type);
            }
//            getPostdata();
        }
    }
    private void fetchPosts(String mainCategoryId,String subCategoryId,String type){
        if(type.equals("videoChild")){

            setupVideoAdapter();
            Constant.getHomeViewModel(this).getVideoSubCategoriesFrames(mainCategoryId,subCategoryId).observe(this,videoItems -> {
                if (videoItems != null && !videoItems.isEmpty()) {
                    videoItemList.addAll(videoItems);
                    setVideoData(videoItemList);
                    binding.llNotFound.setVisibility(GONE);
                } else {
                    binding.llNotFound.setVisibility(VISIBLE);
                }

                universalDialog.dissmissLoadingDialog();
                binding.progreee.setVisibility(GONE);
                binding.shimmerViewContainer.setVisibility(GONE);

                if (videoItemList.isEmpty()) {
                    binding.llNotFound.setVisibility(VISIBLE);
                }
            });
        }else{
            setupPreviewAdapter();
        LiveData<List<PostItem>> postLiveData;
        if(type.equals("categoryChild")){
            postLiveData = Constant.getHomeViewModel(this).getSubCategoryPosts(mainCategoryId,subCategoryId);
        }else if(type.equals("greetingChild")){
            postLiveData = Constant.getHomeViewModel(this).getGreetingSubCategoryPosts(mainCategoryId,subCategoryId);
        }else {
            postLiveData = new MutableLiveData<>();
            return;
        }
        postLiveData.observe(this,postItems -> {
            if (postItems != null && !postItems.isEmpty()) {
                MyUtils.showResponse(postItems);
                postItemList.addAll(postItems);
                setData(postItemList);
                binding.llNotFound.setVisibility(GONE);
            } else {
                binding.llNotFound.setVisibility(VISIBLE);
            }

            universalDialog.dissmissLoadingDialog();
            binding.progreee.setVisibility(GONE);
            binding.shimmerViewContainer.setVisibility(GONE);

            if (postItemList.isEmpty()) {
                binding.llNotFound.setVisibility(VISIBLE);
            }
        });
        }
    }

    private void setupPreviewAdapter() {
        if (type != null && type.equals("greetingChild")) {
            isGreeting = true;
        }


        adapter = new PreviewAdapter(this, (data) -> {
            if (postItemList != null) {
                position = data;
                MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment(postItemList.get(data).image_url,this,"NA",isGreeting,"",
                        postItemList.get(data).position,false);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        }, 3, getResources().getDimension(com.intuit.ssp.R.dimen._2ssp));

        binding.rvPost.setAdapter(adapter);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.rvPost.setLayoutManager(layoutManager);

    }
    private void setupVideoAdapter() {
        if (type != null && type.equals("greeting")) {
            isGreeting = true;
        }


        videoAdapter = new VideoAdapter(this, (data) -> {
            if (videoItemList != null) {
                position = data;
                MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment(videoItemList.get(data).video,this,"NA",false,"",
                        "center",true);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        }, 3, getResources().getDimension(com.intuit.ssp.R.dimen._2ssp));

        binding.rvPost.setAdapter(videoAdapter);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.rvPost.setLayoutManager(layoutManager);

    }

    private void setImageShow(PostItem postItem) {

        androidx.transition.Transition transition1 = new AutoTransition();
        transition1.setDuration(200);
        TransitionManager.beginDelayedTransition(binding.rootView, transition1);

        Glide.with(MyApplication.context).asBitmap().load((postItem.image_url)).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });







/*
        Intent intent = new Intent(PreviewActivity.this, ThumbnailActivity.class);
        intent.putExtra("backgroundImage", postItem.image_url);
        intent.putExtra("type", typePost);
        intent.putExtra("sizeposition", ratio);
        startActivity(intent);*/
    }

    private void setData(List<PostItem> data) {

        MyUtils.showResponse(data);

        binding.rvPost.setVisibility(VISIBLE);
        binding.shimmerViewContainer.setVisibility(GONE);

        adapter.setData(data);
        adapter.notifyDataSetChanged();

    }

    private void setVideoData(List<VideoItem> data) {

        MyUtils.showResponse(data);

        binding.rvPost.setVisibility(VISIBLE);
        binding.shimmerViewContainer.setVisibility(GONE);

        videoAdapter.setData(data);
        videoAdapter.notifyDataSetChanged();

    }


}