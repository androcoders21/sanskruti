package com.sanskruti.volotek.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.databinding.ItemPostPreviewBinding;
import com.sanskruti.volotek.databinding.SearchItemBinding;
import com.sanskruti.volotek.listener.ClickListener;
import com.sanskruti.volotek.model.CategoryItem;
import com.sanskruti.volotek.model.PostItem;
import com.sanskruti.volotek.model.SearchDataObj;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    Context context;
    ClickListener<Integer> listener;

    List<SearchDataObj> postItemList;
    List<SearchDataObj> originalList;
    int column;
    float width;
    private int itemWidth = 0;

    public SearchAdapter(Context context, ClickListener<Integer> listener, int column, float width) {
        this.context = context;
        this.listener = listener;
        this.column = column;
        this.width = width;
    }

    public void setPosts(List<SearchDataObj> postItemList) {
        this.postItemList = postItemList;
        this.originalList = postItemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchItemBinding binding = SearchItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        TransitionManager.beginDelayedTransition(binding.rootView);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            listener.onClick(position);
        });
        Glide.with(context.getApplicationContext()).load(postItemList.get(position).path).thumbnail(Glide.with(context.getApplicationContext()).load(postItemList.get(position).path))
                .placeholder(R.drawable.spaceholder).into(holder.binding.ivPost);
    }

    @Override
    public int getItemCount() {
        return postItemList == null ? 0 : postItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SearchItemBinding binding;
        public MyViewHolder(@NonNull SearchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public List<SearchDataObj> getOriginalList() {
        return postItemList;
    }

}
