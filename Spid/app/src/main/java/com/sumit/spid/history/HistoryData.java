package com.sumit.spid.history;

import java.io.Serializable;

public class HistoryData implements Serializable
{
    public String getFrom_address() {
        return from_address;
    }

    public String getTo_address() {
        return to_address;
    }

    public String getDelivery_time() {
        return delivery_time;
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

    public String getReceiverId() {
        return receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }


    String from_address;
    String to_address;
    String delivery_time;
    String packetId;
    String eventId;
    String image;
    String receiverId;
    String receiverName;
    String insurance;
    String price;
    String type;
    String weight;
    String description;
    String status;

    public HistoryData(String from_address, String to_address, String delivery_time, String packetId, String eventId, String image, String receiverId,
                       String receiverName, String insurance, String price, String type, String weight, String description, String status) {
        this.from_address = from_address;
        this.to_address = to_address;
        this.delivery_time = delivery_time;
        this.packetId = packetId;
        this.eventId = eventId;
        this.image = image;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.insurance = insurance;
        this.price = price;
        this.type = type;
        this.weight = weight;
        this.description = description;
        this.status = status;
    }



}
