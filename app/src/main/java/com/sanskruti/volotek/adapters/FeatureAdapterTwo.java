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
import com.sanskruti.volotek.databinding.ItemFeatureBinding;
import com.sanskruti.volotek.model.FeatureItem;
import com.sanskruti.volotek.model.PostItem;
import com.sanskruti.volotek.model.UserItem;
import com.sanskruti.volotek.ui.activities.PreviewActivity;
import com.sanskruti.volotek.ui.fragments.HomeFragment;
import com.sanskruti.volotek.ui.fragments.MyBottomSheetFragment;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.PreferenceManager;

import java.util.List;

public class FeatureAdapterTwo extends RecyclerView.Adapter<FeatureAdapterTwo.MyViewHolder> {

    public List<FeatureItem> featureItemList;
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


    public void setFeatureItemList(List<FeatureItem> featureItemList) {
        this.featureItemList = featureItemList;
        notifyDataSetChanged();


    }  public void addFeatureItems(List<FeatureItem> featureItemList) {
        this.featureItemList.addAll(featureItemList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFeatureBinding binding = ItemFeatureBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (position % 2 == 0) {
            holder.binding.mainConstraint.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.binding.txtViewTrending.setOnClickListener(v -> interstitialsAdsManager.showInterstitialAd(() -> goToPreviewActivityViewMore(position)));

        holder.adapters = new TrendingAdapter(context, data -> interstitialsAdsManager.showInterstitialAd(() -> gotoPreviewActivity(holder, data, position)));


        holder.binding.rvFeature.setAdapter(holder.adapters);
        holder.binding.tvFeature.setText(featureItemList.get(position).title);


        holder.adapters.setTrending(featureItemList.get(position).postItemList);


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
        Log.i("saqlain","check details in this type = "+featureItemList.get(position).type);

//        Intent intent = new Intent(context, ThumbnailActivity.class);
//        intent.putExtra("backgroundImage", data.image_url);
//        intent.putExtra("type", "images");
//        intent.putExtra("sizeposition", "1:1");
//        context.startActivity(intent);
        if (featureItemList.get(position).type != null && featureItemList.get(position).type.equals("greeting")) {
            greeting = true;
        }


        // Or using static method
        // MyBottomSheetFragment bottomSheetFragment = MyBottomSheetFragment.newInstance(itemData);

        if(fragmentManager !=null){
            MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment(data.image_url,context,"NA",greeting,data.type,data.position);
            bottomSheetFragment.show(fragmentManager, bottomSheetFragment.getTag());
        }else {

        }



    }

    private void goToPreviewActivityViewMore(int position) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(Constant.INTENT_TYPE, featureItemList.get(position).type);
        intent.putExtra(Constant.INTENT_FEST_ID, featureItemList.get(position).festId);
        intent.putExtra(Constant.INTENT_FEST_NAME, featureItemList.get(position).title);
        intent.putExtra(Constant.INTENT_POST_IMAGE, "");
        intent.putExtra(Constant.INTENT_VIDEO, featureItemList.get(position).video);
        intent.putExtra("From", From);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (featureItemList != null && featureItemList.size() > 0) {
            return featureItemList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemFeatureBinding binding;
        TrendingAdapter adapters;

        public MyViewHolder(@NonNull ItemFeatureBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}


