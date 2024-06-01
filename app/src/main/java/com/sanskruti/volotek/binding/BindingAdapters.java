package com.sanskruti.volotek.binding;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingAdapters {
    @BindingAdapter("formattedDate")
    public static void setFormattedDate(TextView textView, String date) {
        if (date != null) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
            SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            try {
                Date inputDate = inputFormat.parse(date);
                String formattedDate;

                // Get today's date in the same format as input date
                String todayDate = todayFormat.format(new Date());

                // Compare the input date with today's date
                if (inputFormat.format(inputDate).equals(todayDate)) {
                    formattedDate = "Today";
                } else {
                    formattedDate = outputFormat.format(inputDate);
                }

                textView.setText(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
                textView.setText(date); // Fallback to the original date if parsing fails
            }
        }
    }

}
