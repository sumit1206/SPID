package com.sumit.spid.mydelivery.data;

import java.io.Serializable;

public class MyDeliveryData implements Serializable
{
    String from_address;
    String to_address;
    String delivery_time;
    String progress;

    String packetId;
    String eventId;
    String image;
    String senderId;
    String carrierId;
    String receiverId;
    String receiverName;
    String insurance;

    String paidStatus;

    String cost;
    String type;
    String weight;
    String about;
    String dimension;

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getType() {
        return type;
    }

    public String getWeight() {
        return weight;
    }

    public String getAbout() {
        return about;
    }

    public String getDimension() {
        return dimension;
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

    public String getPacketId() {
        return packetId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getImage() {
        return image;
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

    public String getCost() {
        return cost;
    }


//    public MyDeliveryData(String from_address, String to_address, String delivery_time, int thumbnail) {
//        this.from_address = from_address;
//        this.to_address = to_address;
//        this.delivery_time = delivery_time;
//        this.thumbnail = thumbnail;
//    }
//
//    public String getFrom_address() {
//        return from_address;
//    }
//
//    public void setFrom_address(String from_address) {
//        this.from_address = from_address;
//    }
//
//    public String getTo_address() {
//        return to_address;
//    }
//
//    public void setTo_address(String to_address) {
//        this.to_address = to_address;
//    }
//
//    public String getDelivery_time() {
//        return delivery_time;
//    }
//
//    public void setDelivery_time(String delivery_time) {
//        this.delivery_time = delivery_time;
//    }
//
//    public int getThumbnail() {
//        return thumbnail;
//    }

    public MyDeliveryData(String from_address, String to_address, String delivery_time, String progress, String packetId, String eventId, String image,
                          String senderId, String carrierId, String receiverId, String receiverName, String insurance, String paidStatus, String cost,
                          String type, String weight, String about, String dimension) {
        this.from_address = from_address;
        this.to_address = to_address;
        this.delivery_time = delivery_time;
        this.progress = progress;
        this.packetId = packetId;
        this.eventId = eventId;
        this.image = image;
        this.senderId = senderId;
        this.carrierId = carrierId;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.insurance = insurance;
        this.paidStatus = paidStatus;
        this.cost = cost;
        this.type = type;
        this.weight = weight;
        this.about = about;
        this.dimension = dimension;
    }
//
//    public void setThumbnail(int thumbnail) {
//        this.thumbnail = thumbnail;
//    }
}

