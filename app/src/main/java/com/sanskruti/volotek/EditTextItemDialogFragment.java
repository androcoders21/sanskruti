package com.sanskruti.volotek.ui.fragments;

import static com.sanskruti.volotek.MyApplication.context;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaredrummler.android.colorpicker.ColorPickerView;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.custom.poster.adapter.FontAdapter;
import com.sanskruti.volotek.custom.poster.listener.OnClickCallback;
import com.sanskruti.volotek.ui.activities.EditPoliticalProfileDetailsActivity;
import com.sanskruti.volotek.utils.Configure;

import java.io.File;
import java.util.ArrayList;


/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     EditTextBottomSheet.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class EditTextItemDialogFragment extends BottomSheetDialogFragment {

    private OnSubmitListener onSubmitListener;

    private  OnColorSelectedListener onColorSelectedListener;

    private  OnFontFamilyListener onFontFamilyListener;

    private String initialText = "",selectedFontFamily =  "Baloo-Bold.ttf";

    private int slectedFontColor = -16056320;

    LinearLayout addTextColorll,addTextFontll;

    FontAdapter adapter;

    public EditTextItemDialogFragment() {
        // Required empty public constructor
    }

    public void setInitialFont(String initialFont) {
        this.selectedFontFamily = initialText;
    }

    public void setInitialFontColor(int initialFontColor) {
        this.slectedFontColor = initialFontColor;
    }
    public void setInitialText(String initialText) {
        this.initialText = initialText;
    }

    public interface OnSubmitListener {
        void onSubmit(String userInput);
    }
    public interface OnFontFamilyListener {
        void onFamilySelected(String fontFamily);
    }

    public interface OnColorSelectedListener {
        void onColorSelected(int color);
    }
    public void setFontListener(OnFontFamilyListener listener){
        this.onFontFamilyListener = listener;
    }

    public void setColorListener(OnColorSelectedListener listener) {
        this.onColorSelectedListener = listener;
    }
    public void setOnSubmitListener(OnSubmitListener listener) {
        this.onSubmitListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_text_item_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText editText = view.findViewById(R.id.editText);
        Button submitButton = view.findViewById(R.id.submitButton);
        addTextColorll = view.findViewById(R.id.addTextColor);
        addTextFontll = view.findViewById(R.id.addTextFontll);
        editText.setText(initialText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = editText.getText().toString();
                if (onSubmitListener != null) {
                    onSubmitListener.onSubmit(userInput);
                }
                dismiss();
            }
        });

        addTextColorll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorPicker(view);
            }
        });

        addTextFontll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //  showFontFamilyBottomSheet(tvMobileNoTv);

                adapter = new FontAdapter(getActivity(), getResources().getStringArray(R.array.fonts_array));
                adapter.setSelected(0);
                ((GridView) view.findViewById(R.id.add_text_gridview_name)).setAdapter(adapter);

                adapter.setItemClickCallback((OnClickCallback<ArrayList<String>, Integer, String, Activity>) (arrayList, num, str, activity) -> {

                    // Apply the selected font family to the TextView
                    selectedFontFamily = str;
                    File file1 = new File(Configure.GetFileDir(context).getPath() + File.separator + "font");
//                    etText.setTypeface(Typeface.createFromFile(file1.getAbsolutePath() + "/" + str));
                    if (onFontFamilyListener != null) {
                        onFontFamilyListener.onFamilySelected(str);
                    }
                    adapter.setSelected(num.intValue());
                });
            }
        });
    }

    private void showColorPicker(View view) {
        // Inflate the custom layout for the color picker
        View view2 = LayoutInflater.from(view.getContext()).inflate(R.layout.color_picker_layout, null);

        // Find views in the custom layout
        final ColorPickerView colorPickerView = view2.findViewById(R.id.colorPickerView);

        // Build the AlertDialog
        AlertDialog colorPickerDialog = new AlertDialog.Builder(view.getContext())
                .setView(view2)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the selected color when the OK button is clicked
                        if (onColorSelectedListener != null) {
                            onColorSelectedListener.onColorSelected(colorPickerView.getColor());
                        }
                        slectedFontColor = colorPickerView.getColor();

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();

//        colorPickerDialog.setOnShowListener( new OnShowListener() {
//            @Override
//            public void onShow(DialogInterface arg0) {
//                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(COLOR_I_WANT);
//            }
//        });

        colorPickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                colorPickerDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                colorPickerDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
            }
        });

        // Show the AlertDialog
        colorPickerDialog.show();
    }
}