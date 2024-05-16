package com.sanskruti.volotek.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanskruti.volotek.R;
import com.sanskruti.volotek.model.Sticker;
import com.sanskruti.volotek.model.StickersCategory;

import java.util.ArrayList;
import java.util.List;

public class StickerCatAdapter extends RecyclerView.Adapter<StickerCatHolder> {

    Context context;
    List<StickersCategory> stickersItems =  new ArrayList<>();


    public interface OnItemClickListener {
        void onItemClick(String category_name);
    }

    private StickerCatAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(StickerCatAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public StickerCatAdapter(Context context, List<StickersCategory> stickersItems) {
        this.context = context;
        this.stickersItems = stickersItems;
    }

    @NonNull
    @Override
    public StickerCatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerCatHolder(LayoutInflater.from(context).inflate(R.layout.sticker_category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerCatHolder holder, int position) {
        StickersCategory item = stickersItems.get(position);
        holder.bind(item, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(item.category_title);
                }
            }
        });
    }

    @Override
    public int getItemCount()  {
        return stickersItems.size();
    }
}
