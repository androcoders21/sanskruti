package com.sanskruti.volotek.binding;

import static android.view.View.DRAWING_CACHE_QUALITY_HIGH;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.sanskruti.volotek.AppConfig;
import com.sanskruti.volotek.MyApplication;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.utils.Util;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class GlideDataBinding {

    @BindingAdapter("imageURL")
    public static void bindImage(ImageView imageView, String url) {
        Context mContext = imageView.getContext();
        if (isValid(imageView, url)) {
            if (AppConfig.PRE_LOAD_IMAGE) {
                Glide.with(mContext.getApplicationContext()).load(url).thumbnail(Glide.with(mContext.getApplicationContext()).load(url))
                        .placeholder(R.drawable.spaceholder).into(imageView);
            } else {
                Glide.with(mContext.getApplicationContext()).load(url).placeholder(R.drawable.spaceholder).into(imageView);
            }
        }
    }

    @BindingAdapter("circle_imageURL")
    public static void bindImage(CircularImageView imageView, String url) {
        if (isValid(imageView, url)) {
            if (AppConfig.PRE_LOAD_IMAGE) {
                Glide.with(MyApplication.getAppContext()).load(url).thumbnail(Glide.with(MyApplication.getAppContext()).load(url)).placeholder(R.drawable.spaceholder).into(imageView);
            } else {
                Glide.with(MyApplication.getAppContext()).load(url).placeholder(R.drawable.spaceholder).into(imageView);
            }
        }
    }

    public static Bitmap viewToBitmap(View view) {
        Bitmap createBitmap = null;
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(DRAWING_CACHE_QUALITY_HIGH);
        try {
            createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            view.draw(new Canvas(createBitmap));
            return createBitmap;
        } catch (Exception e) {
            return createBitmap;
        } finally {
            view.destroyDrawingCache();
        }
    }


    public static Boolean isValid(ImageView imageView, String url) {
        Context mContext = imageView.getContext();
        return !(url == null
                || imageView == null
                || mContext == null
                || url.equals(""));
    }


    @BindingAdapter("fonts")
    public static void setFont(TextView textView, String type) {
        switch (type) {
            case "normal":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO));
                break;
            case "bold":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO), Typeface.BOLD);
                break;
            case "extra_bold":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO_MEDIUM), Typeface.BOLD);
                break;
            case "super_extra_bold":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO_BOLD), Typeface.BOLD);
                break;
            case "bold_italic":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO), Typeface.BOLD_ITALIC);
                break;
            case "italic":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO), Typeface.ITALIC);
                break;
            case "medium":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO_MEDIUM));
                break;
            case "light":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO_LIGHT));
                break;
            default:
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO));
                break;
        }
    }

    @BindingAdapter("fonts")
    public static void setFont(Button button, String type) {
        switch (type) {
            case "normal":
                button.setTypeface(Util.getTypeFace(button.getContext(), Util.Fonts.ROBOTO));
                break;
            case "medium":
                button.setTypeface(Util.getTypeFace(button.getContext(), Util.Fonts.ROBOTO_MEDIUM));
                break;
            case "light":
                button.setTypeface(Util.getTypeFace(button.getContext(), Util.Fonts.ROBOTO_LIGHT));
                break;
            default:
                button.setTypeface(Util.getTypeFace(button.getContext(), Util.Fonts.ROBOTO));
                break;
        }

    }

    @BindingAdapter("textSize")
    public static void setTextSize(TextView textView, String dimenType) {

        float dimenPix = 0;
//        Util.showLog("dimenType " + dimenType);
        switch (dimenType) {

            case "font_h1_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._96ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h2_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._60ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h3_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._48ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h4_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._34ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h5_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._24ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h6_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._20ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h7_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._18ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_title_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._16ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_body_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._14ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_body_s_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._12ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_body_xs_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._10ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;
            case "font_body_xxs_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._8ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;
        }
    }

    @BindingAdapter("textSize")
    public static void setTextSize(CheckBox textView, String dimenType) {

        float dimenPix = 0;
        switch (dimenType) {

            case "font_h1_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._96ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h2_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._60ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h3_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._48ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h4_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._34ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h5_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._24ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h6_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._20ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_h7_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._18ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_title_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._16ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_body_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._14ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_body_s_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._12ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;

            case "font_body_xs_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._10ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;
            case "font_body_xxs_size":
                dimenPix = textView.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._8ssp);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);
                break;
        }
    }

    @BindingAdapter("fonts")
    public static void setFont(CheckBox textView, String type) {
        switch (type) {
            case "normal":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO));
                break;
            case "bold":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO), Typeface.BOLD);
                break;
            case "bold_italic":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO), Typeface.BOLD_ITALIC);
                break;
            case "italic":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO), Typeface.ITALIC);
                break;
            case "medium":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO_MEDIUM));
                break;
            case "light":
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO_LIGHT));
                break;
            default:
                textView.setTypeface(Util.getTypeFace(textView.getContext(), Util.Fonts.ROBOTO));
                break;
        }

    }


    @BindingAdapter("textSize")
    public static void setTextSize(EditText editText, String dimenType) {

        float dimenPix = 0;
        switch (dimenType) {
            case "edit_text":

                dimenPix = editText.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._14ssp);
                editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);

                break;
        }
    }

    @BindingAdapter("textSize")
    public static void setTextSize(Button button, String dimenType) {

        float dimenPix = 0;
        switch (dimenType) {
            case "button_text_12":

                dimenPix = button.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._12ssp);
                button.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);

                break;

            case "button_text_14":

                dimenPix = button.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._14ssp);
                button.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);

                break;

            case "button_text_16":

                dimenPix = button.getResources().getDimensionPixelOffset(com.intuit.ssp.R.dimen._16ssp);
                button.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenPix);

                break;
        }
    }
}
