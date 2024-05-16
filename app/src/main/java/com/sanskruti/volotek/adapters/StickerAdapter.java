package com.sanskruti.volotek.adapters;

public class StickerAdapter {
    String image;
    String isPaid;
    String price;
    String type;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StickerAdapter(String image, String isPaid, String price, String type) {
        this.image = image;
        this.isPaid = isPaid;
        this.price = price;
        this.type = type;
    }


}
