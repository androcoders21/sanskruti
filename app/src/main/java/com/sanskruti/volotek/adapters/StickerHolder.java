package com.sanskruti.volotek.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanskruti.volotek.R;
import com.sanskruti.volotek.binding.GlideDataBinding;
import com.sanskruti.volotek.model.ItemPolitical;
import com.sanskruti.volotek.model.Sticker;
import com.sanskruti.volotek.ui.activities.EditPoliticalProfileDetailsActivity;
import com.sanskruti.volotek.ui.activities.PoliticalProfileDetailsEditActivity;

public class StickerHolder extends RecyclerView.ViewHolder {
    ImageView stickerImageView;
    public StickerHolder(@NonNull View itemView) {
        super(itemView);
        stickerImageView = itemView.findViewById(R.id.stickerImageView);
    }
    public void bind(Sticker item, int position) {
        GlideDataBinding.bindImage(stickerImageView, item.frame_image);
//        textViewName.setText(item.pName);
//        textViewDetails.setText(item.pDesignation1);
//        iv_editll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, PoliticalProfileDetailsEditActivity.class);
//                intent.putExtra("index", position);
//                intent.putExtra("profileId", item.profileId);
//                intent.putExtra("Action", "Update");
//                context.startActivity(intent);
//
//            }
//        });

    }
}
