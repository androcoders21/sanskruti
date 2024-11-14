package com.sanskruti.volotek.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sanskruti.volotek.R;
import com.sanskruti.volotek.databinding.ActivityReferralBinding;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.NetworkConnectivity;
import com.sanskruti.volotek.utils.PreferenceManager;
import com.sanskruti.volotek.utils.Util;

public class ReferralActivity extends AppCompatActivity {
    ActivityReferralBinding binding;
    NetworkConnectivity networkConnectivity;
    PreferenceManager preferenceManager;
    ProgressDialog prgDialog;
    String referralCode = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReferralBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(binding.getRoot());

        binding.toolbar.toolName.setText("Referral");
        networkConnectivity = new NetworkConnectivity(this);
        preferenceManager = new PreferenceManager(this);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage(getString(R.string.login_loading));
        prgDialog.setCancelable(false);
        setUpUi();

    }

    private void setUpUi(){
        if(preferenceManager.getString(Constant.USER_TYPE).equals("leader")){
            binding.etReferral.setText(preferenceManager.getString(Constant.USER_REFERRAL));
            binding.btnSave.setVisibility(View.GONE);
            binding.etReferral.setFocusable(false);
            binding.etReferral.setFocusableInTouchMode(false);
            binding.etReferral.setInputType(InputType.TYPE_NULL);
            binding.etReferral.setTextIsSelectable(true);
        }
        binding.btnSave.setOnClickListener(view -> {
            hideKeyboard();
            if(validate()){
                if (!networkConnectivity.isConnected()) {
                    Util.showToast(this, getString(R.string.error_message__no_internet));
                    return;
                }
                prgDialog.show();
                String referralCode = binding.etReferral.getText().toString().trim();
                Log.i("saqlain",preferenceManager.getString(Constant.USER_PHONE) + " " + referralCode);
                Constant.getHomeViewModel(this).updateReferralCode(preferenceManager.getString(Constant.USER_PHONE),referralCode)
                        .observe(this,data->{
                            if(data!=null){
                                if(data.getStatus() == null){
                                    Util.showToast(this, "Referral Code Invalid!");
                                }else {
                                    Util.showToast(this, data.getMessage());
                                    preferenceManager.setString(Constant.USER_TYPE,"party_worker");
                                }
                            }
                            prgDialog.dismiss();
                        });
            }
        });
    }

    private Boolean validate(){
        if(binding.etReferral.getText().toString().trim().isEmpty()){
            binding.etReferral.setError("Enter Referral Code");
            binding.etReferral.requestFocus();
            return false;
        }
        return true;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
