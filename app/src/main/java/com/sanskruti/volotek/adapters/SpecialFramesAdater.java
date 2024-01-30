package com.sanskruti.volotek.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.sanskruti.volotek.MyApplication;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.databinding.ItemPostPreviewBinding;
import com.sanskruti.volotek.listener.ClickListener;
import com.sanskruti.volotek.model.FrameModel;

import java.util.List;

public class SpecialFramesAdater extends RecyclerView.Adapter<SpecialFramesAdater.MyViewHolder> {

    Context context;
    ClickListener<Integer> listener;

    List<FrameModel> postItemList;
    int column;
    float width;
    private int itemWidth = 0;
    int selectedPosision = 0;

    public SpecialFramesAdater(Context context, ClickListener<Integer> listener, int column, float width,List<FrameModel> postItemList) {
        this.context = context;
        this.listener = listener;
        this.column = column;
        this.width = width;
        this.postItemList = postItemList;
        itemWidth = MyApplication.getColumnWidth(column, width);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostPreviewBinding binding = ItemPostPreviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        TransitionManager.beginDelayedTransition(binding.rootView);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.itemView.setOnClickListener(v -> {

            listener.onClick(position);

        });


        Glide.with(context.getApplicationContext())
                .load(postItemList.get(position).getThumbnail())
                .thumbnail(Glide.with(context.getApplicationContext()).load(postItemList.get(position).getThumbnail()))
                .placeholder(R.drawable.spaceholder)
                .into(holder.binding.ivPost);


    }

    @Override
    public int getItemCount() {
        if (postItemList != null && postItemList.size() > 0) {
            return postItemList.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPostPreviewBinding binding;

        public MyViewHolder(@NonNull ItemPostPreviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

