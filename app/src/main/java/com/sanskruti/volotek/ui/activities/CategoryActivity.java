package com.sanskruti.volotek.ui.activities;

import static com.sanskruti.volotek.utils.Constant.CATEGORY;
import static com.sanskruti.volotek.utils.Constant.GREETING;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.sanskruti.volotek.AdsUtils.AdsUtils;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.adapters.CategoryAdapter;
import com.sanskruti.volotek.adapters.ChildCategoryAdapter;
import com.sanskruti.volotek.databinding.ActivityCategoryBinding;
import com.sanskruti.volotek.listener.ClickListener;
import com.sanskruti.volotek.model.CategoryItem;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.PreferenceManager;

public class CategoryActivity extends AppCompatActivity implements ClickListener<CategoryItem> {

    ActivityCategoryBinding binding;
    ChildCategoryAdapter categoryAdapter;
    PreferenceManager preferenceManager;

    String imageType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);
        new AdsUtils(this).showBannerAds(this);
        setUiViews();
        if (getIntent().getExtras() != null) {
            imageType = getIntent().getStringExtra(Constant.INTENT_TYPE);
            getData();
        }

    }

    private void setUiViews() {
        binding.toolbar.toolName.setText(getResources().getString(R.string.menu_category));

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());

        categoryAdapter = new ChildCategoryAdapter(this, this, false);
        binding.rvCategory.setAdapter(categoryAdapter);
        binding.swipeRefresh.setOnRefreshListener(() -> {
            if(categoryAdapter != null) categoryAdapter.clearData();
            binding.animationView.setVisibility(View.GONE);
            new Handler(Looper.getMainLooper()).postDelayed(this::getData,100);
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    private void getData() {
if(imageType.equals(CATEGORY)) {
    binding.toolbar.toolName.setText(getResources().getString(R.string.menu_category));
    Constant.getHomeViewModel(this).getCategories("categories").observe(this, categoryItems -> {
        if (categoryItems != null && !categoryItems.isEmpty()) {
            categoryAdapter.setCategories(categoryItems);
            binding.animationView.setVisibility(View.GONE);
        } else {
            binding.llNotFound.setVisibility(View.VISIBLE);
        }
        binding.animationView.setVisibility(View.GONE);
    });
    }else if(imageType.equals(GREETING)){
    binding.toolbar.toolName.setText("Greeting");
    Constant.getHomeViewModel(this).getFeaturedGreeting().observe(this,greetingData -> {
        if(greetingData != null && !greetingData.isEmpty()){
//                greetingFeatureAdapter.setFeatureItemList(greetingData);
            categoryAdapter.setCategories(greetingData);
        } else {
            binding.llNotFound.setVisibility(View.VISIBLE);
        }
        binding.animationView.setVisibility(View.GONE);
    });
}else if(imageType.equals("trending")){
    binding.toolbar.toolName.setText("Trending");
    Constant.getHomeViewModel(this).getTrending().observe(this,trendingData -> {
        if(trendingData != null && !trendingData.isEmpty()){
//                greetingFeatureAdapter.setFeatureItemList(greetingData);
            categoryAdapter.setCategories(trendingData);
        } else {
            binding.llNotFound.setVisibility(View.VISIBLE);
        }
        binding.animationView.setVisibility(View.GONE);
    });
}else if(imageType.equals("categoryChild")){
    binding.toolbar.toolName.setText(getIntent().getStringExtra(Constant.INTENT_FEST_NAME));
    Constant.getHomeViewModel(this).getSubCategories(getIntent().getStringExtra(Constant.INTENT_FEST_ID)).observe(this,trendingData -> {
        if(trendingData != null && !trendingData.isEmpty()){
//                greetingFeatureAdapter.setFeatureItemList(greetingData);
            categoryAdapter.setCategories(trendingData);
        } else {
            binding.llNotFound.setVisibility(View.VISIBLE);
        }
        binding.animationView.setVisibility(View.GONE);
    });
}else if(imageType.equals("greetingChild")){
    binding.toolbar.toolName.setText(getIntent().getStringExtra(Constant.INTENT_FEST_NAME));
    Constant.getHomeViewModel(this).getGreetingSubCategories(getIntent().getStringExtra(Constant.INTENT_FEST_ID)).observe(this,trendingData -> {
        if(trendingData != null && !trendingData.isEmpty()){
            categoryAdapter.setCategories(trendingData);
        } else {
            binding.llNotFound.setVisibility(View.VISIBLE);
        }
        binding.animationView.setVisibility(View.GONE);
    });
}else if(imageType.equals("videoChild")){
    binding.toolbar.toolName.setText(getIntent().getStringExtra(Constant.INTENT_FEST_NAME));
    Constant.getHomeViewModel(this).getVideoSubCategories(getIntent().getStringExtra(Constant.INTENT_FEST_ID)).observe(this,trendingData -> {
        if(trendingData != null && !trendingData.isEmpty()){
            categoryAdapter.setCategories(trendingData);
        } else {
            binding.llNotFound.setVisibility(View.VISIBLE);
        }
        binding.animationView.setVisibility(View.GONE);
    });
}

    }

    @Override
    public void onClick(CategoryItem data) {
        Intent intent;
        if (imageType.equals("categoryChild")) {
            intent = new Intent(this, CategoryPostActivity.class);
            intent.putExtra(Constant.INTENT_PARENT_CAT_ID,getIntent().getStringExtra(Constant.INTENT_FEST_ID));
            intent.putExtra(Constant.INTENT_TYPE, imageType);
        }else if(imageType.equals("greetingChild")){
            intent = new Intent(this, CategoryPostActivity.class);
            intent.putExtra(Constant.INTENT_PARENT_CAT_ID,getIntent().getStringExtra(Constant.INTENT_FEST_ID));
            intent.putExtra(Constant.INTENT_TYPE, imageType);
        }else if(imageType.equals("videoChild")){
            intent = new Intent(this, CategoryPostActivity.class);
            intent.putExtra(Constant.INTENT_PARENT_CAT_ID,getIntent().getStringExtra(Constant.INTENT_FEST_ID));
            intent.putExtra(Constant.INTENT_TYPE, imageType);
        }else if(imageType.equals(CATEGORY)){
            intent = new Intent(this, CategoryActivity.class);
            intent.putExtra(Constant.INTENT_TYPE, "categoryChild");
        }else if(imageType.equals(GREETING)){
            intent = new Intent(this, CategoryActivity.class);
            intent.putExtra(Constant.INTENT_TYPE, "greetingChild");
        } else {
            intent = new Intent(this, PreviewActivity.class);
            intent.putExtra(Constant.INTENT_TYPE, imageType);
        }
        intent.putExtra(Constant.INTENT_FEST_ID, data.id);
        intent.putExtra(Constant.INTENT_FEST_NAME, data.name);
        intent.putExtra(Constant.INTENT_POST_IMAGE, "");
        intent.putExtra(Constant.INTENT_VIDEO, data.video);
        startActivity(intent);
    }

}