package com.sanskruti.volotek.ui.fragments;

import static android.os.Looper.getMainLooper;
import static com.sanskruti.volotek.MyApplication.context;
import static com.sanskruti.volotek.utils.Constant.CATEGORY;
import static com.sanskruti.volotek.utils.Constant.FESTIVAL;
import static com.sanskruti.volotek.utils.Constant.GREETING;
import static com.sanskruti.volotek.utils.Constant.INTENT_TYPE;
import static com.sanskruti.volotek.utils.Constant.SUBS_PLAN;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sanskruti.volotek.AdsUtils.InterstitialsAdsManager;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.adapters.CategoryAdapter;
import com.sanskruti.volotek.adapters.FeatureAdapterTwo;
import com.sanskruti.volotek.adapters.FestivalAdapter;
import com.sanskruti.volotek.adapters.SliderAdapter;
import com.sanskruti.volotek.adapters.StoryAdapter;
import com.sanskruti.volotek.custom.animated_video.activities.AnimatedVideoActivity;
import com.sanskruti.volotek.custom.animated_video.activities.SearchActivity;
import com.sanskruti.volotek.custom.animated_video.adapters.AnimatedCategoryAdapter;
import com.sanskruti.volotek.databinding.FragmentHomeBinding;
import com.sanskruti.volotek.model.HomeItem;
import com.sanskruti.volotek.ui.activities.CategoryActivity;
import com.sanskruti.volotek.ui.activities.FestivalActivity;
import com.sanskruti.volotek.ui.activities.PreviewActivity;
import com.sanskruti.volotek.ui.activities.SubsPlanActivity;
import com.sanskruti.volotek.ui.dialog.UniversalDialog;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.MyUtils;
import com.sanskruti.volotek.utils.PreferenceManager;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    Activity activity;
    StoryAdapter storyAdapter;
    FestivalAdapter festivalAdapter;
    CategoryAdapter categoryAdapter, greetingAdapter,trendingAdapter;
    FeatureAdapterTwo featureAdapter,greetingFeatureAdapter;
    AnimatedCategoryAdapter animatedCategoryAdapter;
    PreferenceManager preferenceManager;

    final int paddingPx = 70;
    final float MIN_SCALE = 0.9f;
    final float MAX_SCALE = 1f;

    private InterstitialsAdsManager interstitialsAdsManager;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        activity = getActivity();


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(activity);

        // Load interstistial Ads
        interstitialsAdsManager = new InterstitialsAdsManager(activity);

        shimmer(View.VISIBLE, View.GONE);
        setupUi();
        getData();

        // Load slider Data
        getSliderData();
        preferenceManager.setString(Constant.USER_LANGUAGE, "");

        binding.swipeRefresh.setOnRefreshListener(() -> {

            binding.swipeRefresh.setRefreshing(false);

            new Handler(getMainLooper()).postDelayed(this::getData, 100);

            shimmer(View.VISIBLE, View.GONE);


        });
    }

    private void shimmer(int gone, int visible) {
        binding.shimmerViewContainer.setVisibility(gone);
        binding.mainContainer.setVisibility(visible);
        binding.shimmerViewContainer.startShimmer();
    }


    private void setupUi() {
        // Story Region
        storyAdapter = new StoryAdapter(getContext(), (item) -> {
            if (item.type.equals("externalLink")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.externalLink));
                startActivity(intent);
            } else if (item.type.equals(SUBS_PLAN)) {
                Intent intent = new Intent(getActivity(), SubsPlanActivity.class);
                startActivity(intent);
            } else if(item.type.equals(GREETING)){
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra(INTENT_TYPE,"greetingChild");
                intent.putExtra(Constant.INTENT_FEST_ID,item.festivalId);

                intent.putExtra(Constant.INTENT_FEST_NAME, item.title);
                intent.putExtra(Constant.INTENT_POST_IMAGE, "");
                intent.putExtra(Constant.INTENT_VIDEO, item.video);
                startActivity(intent);
            } else if(item.type.equals(CATEGORY)){
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra(INTENT_TYPE,"categoryChild");
                intent.putExtra(Constant.INTENT_FEST_ID,item.festivalId);

                intent.putExtra(Constant.INTENT_FEST_NAME, item.title);
                intent.putExtra(Constant.INTENT_POST_IMAGE, "");
                intent.putExtra(Constant.INTENT_VIDEO, item.video);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), PreviewActivity.class);
                intent.putExtra(Constant.INTENT_TYPE, item.type);
                intent.putExtra(Constant.INTENT_FEST_ID, item.festivalId);
                intent.putExtra(Constant.INTENT_FEST_NAME, item.title);
                intent.putExtra(Constant.INTENT_POST_IMAGE, "");
                intent.putExtra(Constant.INTENT_VIDEO, item.video);
                startActivity(intent);
            }
        });
        binding.rvStory.setAdapter(storyAdapter);

        //Festival Region
        festivalAdapter = new FestivalAdapter(activity, item -> {
//            if (!item.isActive) {
//                UniversalDialog universalDialog = new UniversalDialog(getActivity(), true);
//                universalDialog.showWarningDialog(getContext().getString(R.string.no_festival_image), getContext().getString(R.string.festival_image_create),
//                        getContext().getString(R.string.ok), false);
//                universalDialog.show();
//                return;
//            }
            Intent intent = new Intent(getActivity(), PreviewActivity.class);
            intent.putExtra(Constant.INTENT_TYPE, FESTIVAL);
            intent.putExtra(Constant.INTENT_FEST_ID, item.id);
            intent.putExtra(Constant.INTENT_FEST_NAME, item.name);
            intent.putExtra(Constant.INTENT_POST_IMAGE, "");
            intent.putExtra(Constant.INTENT_VIDEO, item.video);
            startActivity(intent);
        }, true);


        binding.rvFestival.setAdapter(festivalAdapter);

        //Category Region
        categoryAdapter = new CategoryAdapter(getContext(), item -> {
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            intent.putExtra(Constant.INTENT_TYPE, "categoryChild");
            intent.putExtra(Constant.INTENT_FEST_ID, item.id);
            intent.putExtra(Constant.INTENT_FEST_NAME, item.name);
            intent.putExtra(Constant.INTENT_POST_IMAGE, "");
            intent.putExtra(Constant.INTENT_VIDEO, item.video);
            startActivity(intent);
        }, true);
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.rvCategory.setLayoutManager(verticalLayoutManager);
        binding.rvCategory.setAdapter(categoryAdapter);
        binding.rvCategory.setNestedScrollingEnabled(false);
        binding.rvCategory.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                // ontouch
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {


                //onRequest...
            }
        });

        // Animated Video Category
        animatedCategoryAdapter = new AnimatedCategoryAdapter(getContext(), item -> {
            Intent intent = new Intent(context, SearchActivity.class)
                    .putExtra(Constant.TAG_SEARCH_TERM, item.getName());
            startActivity(intent);
        }, true);

        binding.rvAnimatedCategory.setAdapter(animatedCategoryAdapter);
        binding.rvAnimatedCategory.setNestedScrollingEnabled(false);
        binding.rvAnimatedCategory.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                // ontouch
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {


                //onRequest...
            }
        });

// For Category feature posts
//        featureAdapter = new FeatureAdapterTwo(activity, "HOME",getChildFragmentManager(),false);
//        binding.rvHomeFeature.setAdapter(featureAdapter);
//        binding.rvHomeFeature.setNestedScrollingEnabled(false);
//        binding.rvHomeFeature.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//                //
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//                //
//            }
//        });

        greetingAdapter = new CategoryAdapter(getContext(), item -> {
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            intent.putExtra(Constant.INTENT_TYPE, "greetingChild");
            intent.putExtra(Constant.INTENT_FEST_ID, item.id);
            intent.putExtra(Constant.INTENT_FEST_NAME, item.name);

            intent.putExtra(Constant.INTENT_POST_IMAGE, "");
            intent.putExtra(Constant.INTENT_VIDEO, item.video);
            startActivity(intent);
        }, true);
        binding.rvGreeting.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        LinearLayoutManager verticalLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.rvGreeting.setLayoutManager(verticalLayoutManager2);
        binding.rvGreeting.setAdapter(greetingAdapter);
        binding.rvGreeting.setNestedScrollingEnabled(false);
        binding.rvGreeting.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                // ontouch
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {


                //onRequest...
            }
        });

//        Trending
        trendingAdapter = new CategoryAdapter(getContext(), item -> {
            Intent intent = new Intent(getActivity(), PreviewActivity.class);
            intent.putExtra(Constant.INTENT_TYPE, "trending");
            intent.putExtra(Constant.INTENT_FEST_ID, item.id);
            intent.putExtra(Constant.INTENT_FEST_NAME, item.name);

            intent.putExtra(Constant.INTENT_POST_IMAGE, "");
            intent.putExtra(Constant.INTENT_VIDEO, item.video);
            startActivity(intent);
        }, true);
        binding.rvTrending.setLayoutManager(new LinearLayoutManager(activity.getApplicationContext()));
        LinearLayoutManager verticalLayoutManager3 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.rvTrending.setLayoutManager(verticalLayoutManager3);
        binding.rvTrending.setAdapter(trendingAdapter);
        binding.rvTrending.setNestedScrollingEnabled(false);
        binding.rvTrending.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                // ontouch
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {


                //onRequest...
            }
        });


        binding.fabWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Replace "1234567890" with the recipient's phone number, including the country code
                String phoneNumber = "+918553537373";
                openWhatsAppChat(phoneNumber);
            }
        });

        binding.txtViewFestival.setOnClickListener(v -> interstitialsAdsManager.showInterstitialAd(() -> getContext().startActivity(new Intent(getActivity(), FestivalActivity.class))));

        binding.txtViewCategory.setOnClickListener(v -> interstitialsAdsManager.showInterstitialAd(() -> {
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            intent.putExtra(INTENT_TYPE,"category");
            getContext().startActivity(intent);
        }));

        binding.txtViewAnimated.setOnClickListener(v -> interstitialsAdsManager.showInterstitialAd(() -> getContext().startActivity(new Intent(getActivity(), AnimatedVideoActivity.class))));
        binding.txtViewGreeting.setOnClickListener(v -> interstitialsAdsManager.showInterstitialAd(() -> {
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            intent.putExtra(INTENT_TYPE,"greeting");
            getContext().startActivity(intent);
        }));
        binding.txtViewTrending.setOnClickListener(v -> interstitialsAdsManager.showInterstitialAd(() -> {
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            intent.putExtra(INTENT_TYPE,"trending");
            getContext().startActivity(intent);
        }));

    }

    private void openWhatsAppChat(String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber;
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "WhatsApp not installed", Toast.LENGTH_SHORT).show();
        }
    }
    private void getData() {


        Constant.getHomeViewModel(this).getHomeData().observe(getViewLifecycleOwner(), homeItem -> {

            if (homeItem != null) {

                shimmer(View.GONE, View.VISIBLE);

                binding.shimmerViewContainer.stopShimmer();

                setHomeData(homeItem);
            }
        });

        Constant.getHomeViewModel(this).getGreetingMainCategories().observe(getViewLifecycleOwner(),greetingData -> {
            if(greetingData != null){
//                greetingFeatureAdapter.setFeatureItemList(greetingData);
                greetingAdapter.setCategories(greetingData);
            }
        });

        Constant.getHomeViewModel(this).getTrending().observe(getViewLifecycleOwner(),trendingData -> {
            if(trendingData != null){
//                greetingFeatureAdapter.setFeatureItemList(greetingData);
                trendingAdapter.setCategories(trendingData);
            }
        });

        Constant.getHomeViewModel(this).getMainCategories().observe(getViewLifecycleOwner(),mainCategories -> {
            if(mainCategories != null){
                categoryAdapter.setCategories(mainCategories);
            }
        });

    }

    private void setHomeData(HomeItem data) {

        if (data.storyItemList != null) {
            storyAdapter.setItemList(data.storyItemList);
        }

        if (data.festivalItemList != null) {
            festivalAdapter.setFestData(data.festivalItemList);

        }

        if (data.categoryList != null) {
//            categoryAdapter.setCategories(data.categoryList);
        }

        if (data.featureItemList != null) {
//            featureAdapter.setFeatureItemList(data.featureItemList);
        }

        if (data.animatedCategoryList != null) {
            animatedCategoryAdapter.setCategories(data.animatedCategoryList);
        }

        if (data.festivalItemList!=null&&data.festivalItemList.isEmpty()){

            binding.linearLayout6.setVisibility(View.GONE);
        }

        if (data.festivalItemList!=null&&data.animatedCategoryList.isEmpty()){

            binding.linearLayout9.setVisibility(View.GONE);
        }
        if (data.storyItemList!=null&&data.storyItemList.isEmpty()){

            binding.linearLayout1.setVisibility(View.GONE);
        }

        MyUtils.showResponse(data);

    }
    private int currentPage = 0;
    private final int delayMillis = 3000;
    private Handler handler = new Handler();
    // Runnable for auto-scrolling
    private Runnable autoScroll = new Runnable() {
        @Override
        public void run() {
            if (currentPage == sliderAdapter.getCount() - 1) {
                currentPage = 0;
       //         handler.removeCallbacks(autoScroll);
            } else {
                currentPage++;
            }
            binding.imageSlider.setCurrentItem(currentPage, true);
            handler.postDelayed(autoScroll, delayMillis);
        }
    };
    SliderAdapter sliderAdapter;
    public void getSliderData() {

        Constant.getHomeViewModel(this).getHomeBanners().observe(getViewLifecycleOwner(), sliderItems -> {

            if (sliderItems != null) {


                sliderAdapter = new SliderAdapter(getActivity(), sliderItems);

                binding.imageSlider.setAdapter(sliderAdapter);
                binding.imageSlider.setClipToPadding(false);
                binding.imageSlider.setPadding(paddingPx, 0, paddingPx, 30);
                binding.imageSlider.setPageTransformer(false, transformer);

                binding.linearLayout61.setVisibility(View.VISIBLE);
                // Auto-scroll the ViewPager
                handler.postDelayed(autoScroll, delayMillis);
            }
            else {
                binding.linearLayout61.setVisibility(View.GONE);
            }

        });


    }

    ViewPager.PageTransformer transformer = (page, position) -> {

        float pagerWidthPx = ((ViewPager) page.getParent()).getWidth();
        float pageWidthPx = pagerWidthPx - 2 * paddingPx;

        float maxVisiblePages = pagerWidthPx / pageWidthPx;
        float center = maxVisiblePages / 2f;

        float scale;
        if (position + 0.5f < center - 0.5f || position > center) {
            scale = MIN_SCALE;
        } else {
            float coef;
            if (position + 0.5f < center) {
                coef = (position + 1 - center) / 0.5f;
            } else {
                coef = (center - position) / 0.5f;
            }
            scale = coef * (MAX_SCALE - MIN_SCALE) + MIN_SCALE;
        }
        page.setScaleX(scale);
        page.setScaleY(scale);
    };


    public class AlphaAndScalePageTransformer implements ViewPager.PageTransformer {

        final float SCALE_MAX = 0.8f;
        final float ALPHA_MAX = 0.5f;

        @Override
        public void transformPage(View page, float position) {
            float scale = (position < 0)
                    ? ((1 - SCALE_MAX) * position + 1)
                    : ((SCALE_MAX - 1) * position + 1);
            float alpha = (position < 0)
                    ? ((1 - ALPHA_MAX) * position + 1)
                    : ((ALPHA_MAX - 1) * position + 1);
            if (position < 0) {
                page.setPivotX(page.getWidth());
                page.setPivotY(page.getHeight()/2);
            } else {
                page.setPivotX(0);
                page.setPivotY(page.getHeight()/2);
            }
            page.setScaleX(scale);
            page.setScaleY(scale);
            page.setAlpha(Math.abs(alpha));
        }
    }

}