package com.example.shop.aggregator.dashbord;

import java.io.Serializable;

public class CarrierMainData implements Serializable
{
    String parcelId;
    String parcelImage;
    String from_address;
    String to_address;
    String delivery_time;
    String progress;
    String eventId;
    String senderId;
    String carrierId;
    String receiverId;
    String receiverName;
    String insurance;
    String paidStatus;
    String price;
    String notes;

    String type;
    String weight;
    String dimension;
    String description;

    public String getDimension() {
        return dimension;
    }


    public String getNotes() {
        return notes;
    }

    public String getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }


    public String getParcelId() {
        return parcelId;
    }

    public String getParcelImage() {
        return parcelImage;
    }

    public String getFrom_address() {
        return from_address;
    }

    public String getTo_address() {
        return to_address;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public String getProgress() {
        return progress;
    }

    public String getEventId() {
        return eventId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public String getPrice() {
        return price;
    }


    public CarrierMainData(String parcelId, String parcelImage, String from_address, String to_address, String delivery_time, String progress,
                           String eventId, String senderId, String carrierId, String receiverId, String receiverName, String insurance,
                           String paidStatus, String price, String type, String weight, String description, String dimension, String notes) {
        this.parcelId = parcelId;
        this.parcelImage = parcelImage;
        this.from_address = from_address;
        this.to_address = to_address;
        this.delivery_time = delivery_time;
        this.progress = progress;
        this.eventId = eventId;
        this.senderId = senderId;
        this.carrierId = carrierId;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.insurance = insurance;
        this.paidStatus = paidStatus;
        this.price = price;
        this.type = type;
        this.weight = weight;
        this.description = description;
        this.dimension = dimension;
        this.notes = notes;
    }


//    public CarrierMainData(String parcelId, int parcelImage) {
//        this.parcelId = parcelId;
//        this.parcelImage = parcelImage;
//    }
//
//    public String getParcelId() {
//        return parcelId;
//    }

}

