package com.example.shop.store.stock;

import java.io.Serializable;

public class StoreStockData implements Serializable {

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
    String dimension;

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


    public StoreStockData(String from_address, String to_address, String delivery_time, String packetId, String eventId, String image,
                          String receiverId, String receiverName, String insurance, String price, String type, String weight,
                          String description,String dimension) {
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
        this.dimension = dimension;
    }
}