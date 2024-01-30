package com.sanskruti.volotek.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanskruti.volotek.R;
import com.sanskruti.volotek.model.FontFamilyItem;

import java.util.List;

public class FontFamilyAdapter extends RecyclerView.Adapter<FontFamilyAdapter.ViewHolder> {

    private List<FontFamilyItem> fontFamilyList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public FontFamilyAdapter(List<FontFamilyItem> fontFamilyList) {
        this.fontFamilyList = fontFamilyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_font_family, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FontFamilyItem fontFamilyItem = fontFamilyList.get(position);
        holder.radioButton.setText(fontFamilyItem.getFontFamilyName());
        holder.radioButton.setChecked(position == selectedPosition);

        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("checkfontFamily","fontFamily 4 = "+String.valueOf(position));
                selectedPosition = position;
                Log.i("checkfontFamily","fontFamily 5 = "+String.valueOf(selectedPosition));
                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return fontFamilyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}