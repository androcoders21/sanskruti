package com.sanskruti.volotek.adapters;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanskruti.volotek.R;
import com.sanskruti.volotek.binding.GlideDataBinding;
import com.sanskruti.volotek.model.Sticker;
import com.sanskruti.volotek.model.StickersCategory;

import java.util.ArrayList;
import java.util.List;

public class StickerCatHolder extends RecyclerView.ViewHolder {
    private TextView textViewName;
    private LinearLayout stickerCatLl;
    private List<TextView> textViewList;


    public StickerCatHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.sticker_txt_category);
        stickerCatLl = itemView.findViewById(R.id.sticker_cat_card);
        textViewList = new ArrayList<>();

    }
    public void bind(StickersCategory item, int position) {

        textViewName.setText(item.category_title);
//        textViewName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Reset color of all text views
//                resetTextColor();
//                // Set color of clicked text view
//                textViewName.setBackgroundColor(Color.BLUE);
//                 textViewName.setTextColor(Color.WHITE);
//                // Notify listener
//                if (listener != null) {
//                    listener.onItemClick(item.category_title);
//                }
//            }
//        });

        // Add text view to list
//        textViewList.add(textViewName);
    }

//    private void resetTextColor() {
//        for (TextView textView : textViewList) {
//            textViewName.setBackgroundColor(Color.WHITE);
//            textViewName.setTextColor(Color.BLUE);
//        }
//    }

}
