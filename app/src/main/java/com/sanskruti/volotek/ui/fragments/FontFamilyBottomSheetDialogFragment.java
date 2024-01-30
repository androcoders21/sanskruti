package com.sanskruti.volotek.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.adapters.FontFamilyAdapter;
import com.sanskruti.volotek.model.FontFamilyItem;

import java.util.ArrayList;
import java.util.List;

public class FontFamilyBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private List<FontFamilyItem> fontFamilyList;
    private FontFamilyAdapter adapter;
    private OnFontFamilySelectedListener listener;

    public FontFamilyBottomSheetDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_font_family_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fontFamilyList = createFontFamilyList();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new FontFamilyAdapter(fontFamilyList);
        recyclerView.setAdapter(adapter);

        Button btnApplyFont = view.findViewById(R.id.btnApplyFont);
        btnApplyFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = adapter.getSelectedPosition();
                Log.i("checkfontFamily","fontFamily 3 = "+String.valueOf(selectedPosition));

                if (selectedPosition != RecyclerView.NO_POSITION) {
                    if (listener != null) {
                        listener.onFontFamilySelected(fontFamilyList.get(selectedPosition).getFontFamilyName());
                    }
                }
                dismiss();
            }
        });
    }

    private List<FontFamilyItem> createFontFamilyList() {
        List<FontFamilyItem> list = new ArrayList<>();
        // Add font family items to the list

        list.add(new FontFamilyItem("Arial"));
        list.add(new FontFamilyItem("Caveat"));
        list.add(new FontFamilyItem("Georgia"));
        list.add(new FontFamilyItem("Roboto"));
        list.add(new FontFamilyItem("Merriweather"));
        list.add(new FontFamilyItem("Nunito"));
        //
        // Add more font family items as needed
        return list;
    }

    public void setOnFontFamilySelectedListener(OnFontFamilySelectedListener listener) {
        this.listener = listener;
    }

    public interface OnFontFamilySelectedListener {
        void onFontFamilySelected(String fontFamily);
    }
}