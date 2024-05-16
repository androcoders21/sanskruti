package com.sanskruti.volotek.model;

import java.util.List;

public class StickerResponse {
    private String status;
    private String message;
    private List<StickersCategory> data;

    public List<StickersCategory> getData() {
        return data;
    }

}
