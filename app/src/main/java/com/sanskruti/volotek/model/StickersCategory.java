package com.sanskruti.volotek.model;

import java.util.List;

public class StickersCategory {
    public String id;
    public String category_title;
    public String status;

    public List<Sticker> getStickers() {
        return stickers;
    }

    private List<Sticker> stickers;

    public String getCategory_title() {
        return category_title;
    }




}
