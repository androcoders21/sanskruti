package com.sanskruti.volotek.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sanskruti.volotek.R;
import com.sanskruti.volotek.binding.GlideDataBinding;
import com.sanskruti.volotek.model.ItemPolitical;
import com.sanskruti.volotek.ui.activities.EditPoliticalProfileDetailsActivity;
import com.sanskruti.volotek.ui.activities.PoliticalProfileDetailsEditActivity;
import com.sanskruti.volotek.utils.Constant;
import com.sanskruti.volotek.utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.MyViewHolder> {
    private List<ItemPolitical> itemList = new ArrayList<>();

    Activity context;
    String image_url;
    String type;

    PreferenceManager preferenceManager;
    // Other adapter code

    public interface OnItemClickListener {
        void onItemClick(int position);


        void onDeleteClick(String pProfileId);


    }

    private OnItemClickListener onItemClickListener;


    JSONArray jsonArrayModel = new JSONArray();
    boolean greeting = false,isVideo = false;

    String imagePosition = "";

    public BottomAdapter(Activity context, String image_url, List<ItemPolitical> jsonArray, String type, JSONArray jsonArrayNew,OnItemClickListener onItemClickListener,boolean greeting,String imagePosition,boolean isVideo) {
        this.context = context;
        this.itemList = jsonArray;
        this.image_url = image_url;
        this.type = type;
        this.onItemClickListener = onItemClickListener;
        this.greeting = greeting;
        this.isVideo = isVideo;
        if (jsonArrayNew != null) {
            addAllData(jsonArrayNew);
        }
        this.imagePosition = imagePosition != null ? imagePosition : "";

        preferenceManager = new PreferenceManager(this.context);
        Log.i("getJSONData", "size 11 = " + String.valueOf(jsonArray.size()));
        Log.i("getJSONData", "size 12 = " + String.valueOf(itemList.size()));

    }

    private void addAllData(JSONArray sourceArray) {
        try {
            // Iterate through the elements of sourceArray and add them to destinationArray
            for (int i = 0; i < sourceArray.length(); i++) {
                Object item = sourceArray.get(i);
                jsonArrayModel.put(item);
                Log.i("getDataDelete", "addAllData size = " + String.valueOf(jsonArrayModel.length()));
            }
        } catch (JSONException e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace(); // Handle JSONException appropriately in a real application
        }
    }

    @NonNull
    @Override
    public BottomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomAdapter.MyViewHolder holder, int position) {
        ItemPolitical item = itemList.get(position);
        holder.bind(item, position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName, textViewDetails;
        private LinearLayout ll, iv_editll;
        private CircularImageView profileImg;
        private ImageView ivIvDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.txt_name);
            textViewDetails = itemView.findViewById(R.id.txt_details);
            ll = itemView.findViewById(R.id.profilePoliciy);
            profileImg = itemView.findViewById(R.id.circularImageView);
            iv_editll = itemView.findViewById(R.id.iv_edit);
            ivIvDelete = itemView.findViewById(R.id.ivdelete);
        }

        public void bind(ItemPolitical item, int position) {
            GlideDataBinding.bindImage(profileImg, item.pProfileImg);
            textViewName.setText(item.pName);
            textViewDetails.setText(item.pDesignation1);
            iv_editll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PoliticalProfileDetailsEditActivity.class);
                    intent.putExtra("index", position);
                    intent.putExtra("profileId", item.profileId);
                    intent.putExtra("Action", "Update");
                    context.startActivity(intent);

                }
            });
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (type.equalsIgnoreCase("profile")) {
                        Intent intent = new Intent(context, PoliticalProfileDetailsEditActivity.class);
                        intent.putExtra("index", position);
                        intent.putExtra("profileId", item.profileId);
                        intent.putExtra("Action", "Update");
                        context.startActivity(intent);

                    } else {
                        Intent intent = new Intent(context, EditPoliticalProfileDetailsActivity.class);
                        intent.putExtra("index", String.valueOf(position));
                        intent.putExtra("img", image_url);
                        intent.putExtra("profileId", item.profileId);
                        intent.putExtra("greeting",greeting);
                        intent.putExtra("imagePosition",imagePosition);
                        intent.putExtra("isVideo",isVideo);
                        context.startActivity(intent);
                    }


                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getAdapterPosition());
                    }

                }
            });
            // You can add additional bindings and click listeners here if needed


            ivIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("RESPONSEGetAllData", "RESPONSE profileId -->" + String.valueOf(item.profileId)+", type = "+type);
                    if (onItemClickListener != null) {
                        Log.i("RESPONSEGetAllData", "ivIvDelete size = ");
                        onItemClickListener.onDeleteClick(item.profileId);
                    }

                }
            });
        }
    }

    private void onDeleteData(String profileId,int poss) {
        Log.i("getDataDelete", "adapter = " + String.valueOf(profileId));
        try {
            for (int i = 0; i < jsonArrayModel.length(); i++) {
                JSONObject userObjectTwo = jsonArrayModel.getJSONObject(i);
                String profileIdOther = userObjectTwo.getString("profileId");
                if (profileId.equalsIgnoreCase(String.valueOf(profileIdOther))) {
                    int position=i;
                    Log.i("getDataDelete", "posisiot = " + String.valueOf(position));
                    if (position >= 0 && position < jsonArrayModel.length()) {
                        Log.i("getDataDelete", "length = " + String.valueOf(jsonArrayModel.length()));
                        jsonArrayModel.remove(position);
                        Log.i("getDataDelete", "length new = " + String.valueOf(jsonArrayModel.length()));
                        preferenceManager.setStringTwo(Constant.USER_POLITICAL_PROFILE, jsonArrayModel.toString());


                        itemList.remove(poss);
                        notifyDataSetChanged();
                    } else {
                        Log.i("getDataDelete", "else size = " + String.valueOf(jsonArrayModel.length()));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        Log.i("getJSONData", "size 13 = " + String.valueOf(itemList.size()));
        return itemList.size();
    }
}
