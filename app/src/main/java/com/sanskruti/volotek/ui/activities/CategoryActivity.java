package com.sanskruti.volotek.ui.activities;

import static com.sanskruti.volotek.utils.Constant.CATEGORY;
import static com.sanskruti.volotek.utils.Constant.GREETING;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.sanskruti.volotek.AdsUtils.AdsUtils;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.adapters.CategoryAdapter;
import com.sanskruti.volotek.databinding.ActivityCategoryBinding;
import com.sanskruti.volotek.listener.ClickListener;
import com.sanskruti.volotek.model.CategoryItem;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.PreferenceManager;

public class CategoryActivity extends AppCompatActivity implements ClickListener<CategoryItem> {

    ActivityCategoryBinding binding;
    CategoryAdapter categoryAdapter;
    PreferenceManager preferenceManager;

    String imageType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceManager = new PreferenceManager(this);
        new AdsUtils(this).showBannerAds(this);
        setUiViews();
        if (getIntent().getExtras() != null) {
            imageType = getIntent().getStringExtra(Constant.INTENT_TYPE);
            Log.i("saqlain",imageType);
            getData();
        }

    }

    private void setUiViews() {
        binding.toolbar.toolName.setText(getResources().getString(R.string.menu_category));

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());

        categoryAdapter = new CategoryAdapter(this, this, false);
        binding.rvCategory.setAdapter(categoryAdapter);

    }

    private void getData() {
if(imageType.equals(CATEGORY)) {
    binding.toolbar.toolName.setText(getResources().getString(R.string.menu_category));
    Constant.getHomeViewModel(this).getCategories("categories").observe(this, categoryItems -> {
        if (categoryItems != null) {
            categoryAdapter.setCategories(categoryItems);
            binding.animationView.setVisibility(View.GONE);
        } else {
            binding.animationView.setVisibility(View.VISIBLE);
        }

    });
    }else if(imageType.equals(GREETING)){
    binding.toolbar.toolName.setText("Greeting");
    Constant.getHomeViewModel(this).getFeaturedGreeting().observe(this,greetingData -> {
        if(greetingData != null){
//                greetingFeatureAdapter.setFeatureItemList(greetingData);
            categoryAdapter.setCategories(greetingData);
        }
    });
}else if(imageType.equals("trending")){
    binding.toolbar.toolName.setText("Trending");
    Constant.getHomeViewModel(this).getTrending().observe(this,trendingData -> {
        if(trendingData != null){
//                greetingFeatureAdapter.setFeatureItemList(greetingData);
            categoryAdapter.setCategories(trendingData);
        }
    });
}

    }

    @Override
    public void onClick(CategoryItem data) {

        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra(Constant.INTENT_TYPE, imageType);
        intent.putExtra(Constant.INTENT_FEST_ID, data.id);
        intent.putExtra(Constant.INTENT_FEST_NAME, data.name);
        intent.putExtra(Constant.INTENT_POST_IMAGE, "");
        intent.putExtra(Constant.INTENT_VIDEO, data.video);
        startActivity(intent);

    }

}