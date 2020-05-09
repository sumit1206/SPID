package com.sumit.spid.search;

public class SearchData
{
//    String from_address_search,to_address_search,drop_time_search,expected_delivery_time_search,price_search;
//
//    public SearchData(String from_address_search, String to_address_search, String drop_time_search, String expected_delivery_time_search, String price_search) {
//        this.from_address_search = from_address_search;
//        this.to_address_search = to_address_search;
//        this.drop_time_search = drop_time_search;
//        this.expected_delivery_time_search = expected_delivery_time_search;
//        this.price_search = price_search;
//    }
//
//    public String getFrom_address_search() {
//        return from_address_search;
//    }
//
//    public void setFrom_address_search(String from_address_search) {
//        this.from_address_search = from_address_search;
//    }
//
//    public String getTo_address_search() {
//        return to_address_search;
//    }
//
//    public void setTo_address_search(String to_address_search) {
//        this.to_address_search = to_address_search;
//    }
//
//    public String getDrop_time_search() {
//        return drop_time_search;
//    }
//
//    public void setDrop_time_search(String drop_time_search) {
//        this.drop_time_search = drop_time_search;
//    }
//
//    public String getExpected_delivery_time_search() {
//        return expected_delivery_time_search;
//    }
//
//    public void setExpected_delivery_time_search(String expected_delivery_time_search) {
//        this.expected_delivery_time_search = expected_delivery_time_search;
//    }
//
//    public String getPrice_search() {
//        return price_search;
//    }
//
//    public void setPrice_search(String price_search) {
//        this.price_search = price_search;
//    }
    String deliveryTime;
    String dropTime;

    public SearchData(String deliveryTime, String dropTime) {
        this.deliveryTime = deliveryTime;
        this.dropTime = dropTime;
    }

    public String getdropTime() {
        return dropTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

}
