package com.sanskruti.volotek.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sanskruti.volotek.AdsUtils.InterstitialsAdsManager;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.binding.GlideDataBinding;
import com.sanskruti.volotek.custom.poster.activity.ThumbnailActivity;
import com.sanskruti.volotek.databinding.GreetingFeatureBinding;
import com.sanskruti.volotek.databinding.ItemFeatureBinding;
import com.sanskruti.volotek.model.FeatureItem;
import com.sanskruti.volotek.model.GreetingPage;
import com.sanskruti.volotek.model.PostItem;
import com.sanskruti.volotek.model.UserItem;
import com.sanskruti.volotek.ui.activities.CategoryActivity;
import com.sanskruti.volotek.ui.activities.CategoryPostActivity;
import com.sanskruti.volotek.ui.activities.PreviewActivity;
import com.sanskruti.volotek.ui.fragments.HomeFragment;
import com.sanskruti.volotek.ui.fragments.MyBottomSheetFragment;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.PreferenceManager;

import java.util.List;

public class FeatureAdapterTwo extends RecyclerView.Adapter<FeatureAdapterTwo.MyViewHolder> {

    public List<GreetingPage> greetingItemList;
    Activity context;
    PreferenceManager preferenceManager;
    private String From;
    private FragmentManager fragmentManager;
    private InterstitialsAdsManager interstitialsAdsManager;

    private boolean greeting = false;

    private String imagePosition = "";

    public FeatureAdapterTwo(Activity context, String From, FragmentManager fragmentManager,boolean greetingNew) {
        this.context = context;
        this.From = From;
        preferenceManager = new PreferenceManager(context);

        interstitialsAdsManager = new InterstitialsAdsManager(context);
        this.fragmentManager = fragmentManager;
        this.greeting = greetingNew;

    }


    public void setFeatureItemList(List<GreetingPage> greetingItemList) {
        this.greetingItemList = greetingItemList;
        notifyDataSetChanged();


    }  public void addFeatureItems(List<GreetingPage> greetingItemList) {
        this.greetingItemList.addAll(greetingItemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GreetingFeatureBinding binding = GreetingFeatureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (position % 2 == 0) {
            holder.binding.mainConstraint.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.binding.txtViewTrending.setOnClickListener(v -> interstitialsAdsManager.showInterstitialAd(() -> goToPreviewActivityViewMore(position)));

        holder.adapters = new CategoryAdapter(context,item -> {
            Intent intent = new Intent(context, CategoryPostActivity.class);
            intent.putExtra(Constant.INTENT_TYPE, greeting ? "greetingChild" : "videoChild");
            intent.putExtra(Constant.INTENT_PARENT_CAT_ID,item.category_id);
            intent.putExtra(Constant.INTENT_FEST_ID, item.id);
            intent.putExtra(Constant.INTENT_FEST_NAME, item.name);

            intent.putExtra(Constant.INTENT_POST_IMAGE, "");
            intent.putExtra(Constant.INTENT_VIDEO, item.video);
            context.startActivity(intent);
        },false);
        //Use Category Adapter

        holder.binding.rvFeature.setAdapter(holder.adapters);
        holder.binding.tvFeature.setText(greetingItemList.get(position).name);

        holder.adapters.setCategories(greetingItemList.get(position).subCategories);


    }

    private void gotoPreviewActivity(MyViewHolder holder, PostItem data, int position) {
      /*  Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(Constant.INTENT_TYPE, featureItemList.get(position).type);
        intent.putExtra(Constant.INTENT_FEST_ID, data.fest_id);
        intent.putExtra(Constant.INTENT_FEST_NAME, featureItemList.get(position).title);
        intent.putExtra(Constant.INTENT_POST_IMAGE, data.image_url);
        intent.putExtra(Constant.INTENT_POS, holder.adapters.getItemPosition());
        intent.putExtra(Constant.INTENT_VIDEO, featureItemList.get(position).video);
        intent.putExtra("From", From);
        context.startActivity(intent);*/
        Log.i("saqlain","check details in this type = "+greetingItemList.get(position).name);

//        Intent intent = new Intent(context, ThumbnailActivity.class);
//        intent.putExtra("backgroundImage", data.image_url);
//        intent.putExtra("type", "images");
//        intent.putExtra("sizeposition", "1:1");
//        context.startActivity(intent);
//        if (greetingItemList.get(position).type != null && greetingItemList.get(position).type.equals("greeting")) {
//            greeting = true;
//        }


        // Or using static method
        // MyBottomSheetFragment bottomSheetFragment = MyBottomSheetFragment.newInstance(itemData);

        if(fragmentManager !=null){
            MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment(data.image_url,context,"NA",greeting,data.type,data.position,false);
            bottomSheetFragment.show(fragmentManager, bottomSheetFragment.getTag());
        }else {

        }



    }

    private void goToPreviewActivityViewMore(int position) {

        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(Constant.INTENT_TYPE, greeting ? "greetingChild" : "videoChild");
        intent.putExtra(Constant.INTENT_FEST_ID, greetingItemList.get(position).id);
        intent.putExtra(Constant.INTENT_FEST_NAME, greetingItemList.get(position).name);
        intent.putExtra(Constant.INTENT_POST_IMAGE, "");
        intent.putExtra(Constant.INTENT_VIDEO, false);
        intent.putExtra("From", From);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (greetingItemList != null && greetingItemList.size() > 0) {
            return greetingItemList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        GreetingFeatureBinding binding;
        CategoryAdapter adapters;

        public MyViewHolder(@NonNull GreetingFeatureBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}


