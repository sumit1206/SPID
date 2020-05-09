package com.sumit.spid.search;

import android.app.Activity;
import android.os.Parcelable;

import java.io.Serializable;

public class ParcelData implements Serializable {

    public ParcelData(String serverDropTime, String serverDeliveryTime, String serverToStationList, String serverFromStationList,
                       String valuePrice, String valueType, String valueDimention, String valueWeight, String valueFromAddress, String valueToAddress,
                       String toLatitude, String toLongitude) {
//        this.searchActivity = searchActivity;
        this.serverDropTime = serverDropTime;
        this.serverDeliveryTime = serverDeliveryTime;
        this.serverToStationList = serverToStationList;
        this.serverFromStationList = serverFromStationList;
        this.valuePrice = valuePrice;
        this.valueType = valueType;
        this.valueDimention = valueDimention;
        this.valueWeight = valueWeight;
        this.valueFromAddress = valueFromAddress;
        this.valueToAddress = valueToAddress;
        this.toLatitude = toLatitude;
        this.toLongitude = toLongitude;
    }

//    Activity searchActivity;
    String serverDropTime;
    String serverDeliveryTime;
    String serverToStationList;
    String serverFromStationList;
    String valuePrice;
    String valueType;
    String valueDimention;
    String valueWeight;
    String valueFromAddress;
    String valueToAddress;
    String toLatitude;
    String toLongitude;

//    public String getServerDropTime() {
//        return serverDropTime;
//    }
//
//    public String getServerDeliveryTime() {
//        return serverDeliveryTime;
//    }
//
//    public String getServerToStationList() {
//        return serverToStationList;
//    }
//
//    public String getServerFromStationList() {
//        return serverFromStationList;
//    }
//
//    public String getValuePrice() {
//        return valuePrice;
//    }
//
//    public String getValueType() {
//        return valueType;
//    }
//
//    public String getValueDimention() {
//        return valueDimention;
//    }
//
//    public String getValueWeight() {
//        return valueWeight;
//    }
//
//    public String getValueFromAddress() {
//        return valueFromAddress;
//    }
//
//    public String getValueToAddress() {
//        return valueToAddress;
//    }
//
//    public String getToLatitude() {
//        return toLatitude;
//    }
//
//    public String getToLongitude() {
//        return toLongitude;
//    }



}
