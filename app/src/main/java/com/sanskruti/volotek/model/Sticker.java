package com.sanskruti.volotek.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sticker  implements Serializable {
    @NonNull
    @SerializedName("id")
    public String id;
    public String category_id,user_id,language_id,frame_image,thumbnail,dimension,paid,status,created_at,updated_at,deleted_at,is_video
            ,greeting_position,price,category;
    public String getImage(){
        return frame_image;
    }
}
