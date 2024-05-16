package com.sanskruti.volotek.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.model.ItemPolitical;
import com.sanskruti.volotek.model.Sticker;

import java.util.ArrayList;
import java.util.List;

public class StickerAdapterTwo extends RecyclerView.Adapter<StickerHolder> {

    Context context;
    List<Sticker> stickersItems =  new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(String imageUrl);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public StickerAdapterTwo(Context context, List<Sticker> stickersItems) {
        this.context = context;
        this.stickersItems = stickersItems;
    }
    @NonNull
    @Override
    public StickerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerHolder(LayoutInflater.from(context).inflate(R.layout.sticker_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerHolder holder, int position) {
//          String str = stickersItems.get(position).getImage();
//          Glide.with(context).load(str).skipMemoryCache(true).into(holder.stickerImageView);
        Sticker item = stickersItems.get(position);
        holder.bind(item, position);
        holder.stickerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(item.getImage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return stickersItems.size();
    }
}
