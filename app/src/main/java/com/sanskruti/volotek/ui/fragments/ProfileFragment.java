package com.sanskruti.volotek.ui.fragments;

import static com.arthenica.mobileffmpeg.Config.getPackageName;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sanskruti.volotek.R;
import com.sanskruti.volotek.binding.GlideDataBinding;
import com.sanskruti.volotek.databinding.FragmentProfileBinding;
import com.sanskruti.volotek.model.UserItem;
import com.sanskruti.volotek.ui.activities.AddBusinessActivity;
import com.sanskruti.volotek.ui.activities.CustomSplashActivity;
import com.sanskruti.volotek.ui.activities.PoliticalProfileDetailsEditActivity;
import com.sanskruti.volotek.ui.activities.PrivacyActivity;
import com.sanskruti.volotek.ui.activities.ProfileEditActivity;
import com.sanskruti.volotek.ui.activities.ReferralActivity;
import com.sanskruti.volotek.ui.activities.ServicesActivity;
import com.sanskruti.volotek.ui.activities.SettingActivity;
import com.sanskruti.volotek.ui.activities.SubsPlanActivity;
import com.sanskruti.volotek.ui.dialog.UniversalDialog;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.PreferenceManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class ProfileFragment extends Fragment {
    Activity context;
    UserItem userItem;
    FragmentProfileBinding binding;
    PreferenceManager preferenceManager;
    UniversalDialog universalDialog;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater());


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

        preferenceManager = new PreferenceManager(context);
        universalDialog = new UniversalDialog(context, false);

        setUpUi();
        // set user details
        setProfile();
    }

    @Override
    public void onResume() {
        super.onResume();

        setProfile();
    }

    private void setProfile() {

        binding.ivBussi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(context, AddBusinessActivity.class);
                intent.putExtra("Action", "Insert");
                startActivity(intent);*/
                MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment("",context,"bus",false,"","");

                // Or using static method
                // MyBottomSheetFragment bottomSheetFragment = MyBottomSheetFragment.newInstance(itemData);

                bottomSheetFragment.show(getParentFragmentManager(), bottomSheetFragment.getTag());
            }
        });
        userItem = Constant.getUserItem(context);
        GlideDataBinding.bindImage(binding.circularImageView, userItem.getUserImage());
        binding.txtName.setText(userItem.getUserName());
        binding.txtNumber.setText(userItem.getPhone());

        if (preferenceManager.getBoolean(Constant.IS_SUBSCRIBE)){

            binding.tvPremium.setVisibility(View.VISIBLE);

        }else {
           binding.tvPremium.setVisibility(View.GONE);
        }

    }

    private void setUpUi() {

        if(preferenceManager.getString(Constant.USER_TYPE).equals("party_worker")){
            binding.ivEditPolitical.setVisibility(View.GONE);
            binding.politicalLine.setVisibility(View.GONE);
        }
        if (!preferenceManager.getBoolean(Constant.IS_SUBSCRIBE)) {
            binding.btnSubsUpgrade.setVisibility(View.VISIBLE);
        } else {
            binding.btnSubsUpgrade.setVisibility(View.GONE);
        }

        binding.ivEdit.setOnClickListener(v -> startActivity(new Intent(context, ProfileEditActivity.class)));


        binding.ivGetInTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace "1234567890" with the recipient's phone number, including the country code
                String phoneNumber = "+918553537373";
                openWhatsAppChat(phoneNumber);
            }
        });
        binding.ivEditPolitical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment("",context,"profile",false,"","");

                // Or using static method
                // MyBottomSheetFragment bottomSheetFragment = MyBottomSheetFragment.newInstance(itemData);

                bottomSheetFragment.show(getParentFragmentManager(), bottomSheetFragment.getTag());
            }
        });
        binding.ivEditPolitical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBottomSheetFragment bottomSheetFragment = new MyBottomSheetFragment("",context,"profile",false,"","");

                // Or using static method
                // MyBottomSheetFragment bottomSheetFragment = MyBottomSheetFragment.newInstance(itemData);

                bottomSheetFragment.show(getParentFragmentManager(), bottomSheetFragment.getTag());
            }
        });
     //   binding.ivEditPolitical.setOnClickListener(v -> startActivity(new Intent(context, PoliticalProfileDetailsEditActivity.class)));

        binding.rlSetting.setOnClickListener(v -> startActivity(new Intent(context, SettingActivity.class)));

        binding.rlRefundPolicy.setOnClickListener(v -> {

            Intent intent = new Intent(context, PrivacyActivity.class);
            intent.putExtra("type", Constant.REFUND_POLICY);
            startActivity(intent);

        });

        binding.btnSubsUpgrade.setOnClickListener(v -> startActivity(new Intent(context, SubsPlanActivity.class)));
        binding.services.setOnClickListener(v -> startActivity(new Intent(context, ServicesActivity.class)));
        binding.logout.setOnClickListener(v -> {

            UniversalDialog universalDialog = new UniversalDialog(context, false);
            universalDialog.showConfirmDialog(getString(R.string.menu_logout), getString(R.string.message__want_to_logout),
                    getString(R.string.message__logout),
                    getString(R.string.message__cancel_close));

            universalDialog.okBtn.setOnClickListener(view -> {

                Constant.getHomeViewModel(this).updateLoginStatus(preferenceManager.getString(Constant.USER_PHONE),
                        "1").observe(this,data->{
                            if(data!=null){
                                Log.i("saqlain",data.getIsLogIn());
                            }
                });

                universalDialog.cancel();

                preferenceManager.clearAllDataOnLogout();

                preferenceManager.setBoolean(Constant.IS_LOGIN, false);
                userItem = null;

                GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                mGoogleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions);

                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(context, task -> Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show());
                startActivity(new Intent(context, CustomSplashActivity.class));
                context.finish();

            });

            universalDialog.cancelBtn.setOnClickListener(view -> universalDialog.cancel());

            universalDialog.show();

        });

        binding.rlRateAap.setOnClickListener(v -> {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (android.content.ActivityNotFoundException anfe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });

        binding.rlShare.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_txt) + context.getPackageName());
            startActivity(Intent.createChooser(intent, "Share App..."));

        });

        binding.ivReferral.setOnClickListener(view -> {
            getContext().startActivity(new Intent(getActivity(), ReferralActivity.class));
        });

    }
    private void openWhatsAppChat(String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber;
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "WhatsApp not installed", Toast.LENGTH_SHORT).show();
        }
    }
}