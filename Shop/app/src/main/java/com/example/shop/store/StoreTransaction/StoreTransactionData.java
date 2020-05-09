package com.example.shop.store.StoreTransaction;

public class StoreTransactionData {

    private String parcel_id;
    private int parcelImage;
    private String description;
    private String parcelType;
    private String timeStampStock;

    public StoreTransactionData(String parcel_id, int parcelImage, String description, String parcelType, String timeStampStock) {
        this.parcel_id = parcel_id;
        this.parcelImage = parcelImage;
        this.description = description;
        this.parcelType = parcelType;
        this.timeStampStock = timeStampStock;
    }

    public String getParcel_id() {
        return parcel_id;
    }

    public void setParcel_id(String parcel_id) {
        this.parcel_id = parcel_id;
    }

    public int getParcelImage() {
        return parcelImage;
    }

    public void setParcelImage(int parcelImage) {
        this.parcelImage = parcelImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParcelType() {
        return parcelType;
    }

    public void setParcelType(String parcelType) {
        this.parcelType = parcelType;
    }

    public String getTimeStampStock() {
        return timeStampStock;
    }

    public void setTimeStampStock(String timeStampStock) {
        this.timeStampStock = timeStampStock;
    }

}
