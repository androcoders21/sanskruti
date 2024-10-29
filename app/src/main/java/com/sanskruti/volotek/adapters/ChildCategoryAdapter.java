package com.sanskruti.volotek.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanskruti.volotek.AppConfig;
import com.sanskruti.volotek.databinding.CategoryItemBinding;
import com.sanskruti.volotek.databinding.SubCategoryItemBinding;
import com.sanskruti.volotek.listener.ClickListener;
import com.sanskruti.volotek.model.CategoryItem;

import java.util.List;

public class ChildCategoryAdapter extends RecyclerView.Adapter<ChildCategoryAdapter.MyViewHolder> {

    public Context context;
    public ClickListener<CategoryItem> listener;
    public List<CategoryItem> categoryItemList;
    boolean isHome;

    public ChildCategoryAdapter(Context context, ClickListener<CategoryItem> listener, boolean isHome) {
        this.context = context;
        this.listener = listener;
        this.isHome = isHome;
    }

    public void setCategories(List<CategoryItem> categories) {
        this.categoryItemList = categories;
        notifyDataSetChanged();
    }

    public void clearData(){
        categoryItemList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubCategoryItemBinding binding = SubCategoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setCategoryData(categoryItemList.get(position));


        holder.itemView.setOnClickListener(v -> listener.onClick(categoryItemList.get(position)));
    }


    @Override
    public int getItemCount() {
        if (categoryItemList != null && !categoryItemList.isEmpty()) {
            if (categoryItemList.size() > AppConfig.HOME_CATEGORY_SHOW && isHome) {
                return AppConfig.HOME_CATEGORY_SHOW;
            } else {
                return categoryItemList.size();
            }
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SubCategoryItemBinding binding;

        public MyViewHolder(@NonNull SubCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
