package com.sumit.spid.mydelivery.data;

public class NearestShopDeliveryData {

    String id;
    String shopName;
    String ownerName;
    String phoneNumber;
    String address;
    String imageString;
    String landmark;
    String latitude;
    String longitude;
    String type;
    String rating;
    String distance;
    String openStatus;

    public NearestShopDeliveryData(String id, String shopName, String ownerName, String phoneNumber, String address, String imageString,
                                   String landmark, String latitude, String longitude, String type, String rating, String distance, String openStatus) {
        this.id = id;
        this.shopName = shopName;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.imageString = imageString;
        this.landmark = landmark;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.rating = rating;
        this.distance = distance;
        this.openStatus = openStatus;
    }

    public String getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getImageString() {
        return imageString;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getType() {
        return type;
    }

    public String getRating() {
        return rating;
    }

    public String getDistance() {
        return distance;
    }

    public String getOpenStatus() {
        return openStatus;
    }


}
