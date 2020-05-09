package com.sumit.spid.search;

import android.widget.TextView;

public class PriceEstimator {

    TextView textView;

    public PriceEstimator(TextView textView) {
        this.textView = textView;
    }

    int price;
    String currentDateTime;
    String from;
    String to;
    String distance;
    String type;
    String weight;
    String dimention;
    String duration;
    String value;

//    String insurance;


    public int getCurrentDateTimePrice(String data) {
        int price = 1;
        return price;
    }

    public int getFromPrice(String data) {
        int price = 2;
        return price;
    }

    public int getToPrice(String data) {
        int price = 3;
        return price;
    }

    public int getDistancePrice(String data) {
        int price = 4;
        return price;
    }

    public int getTypePrice(String data) {
        int price = 5;
        return price;
    }

    public int getWeightPrice(String data) {
        int price = 6;
        return price;
    }

    public int getDimentionPrice(String data) {
        int price = 7;
        return price;
    }

    public int getDurationPrice(String data) {
        int price = 8;
        return price;
    }

    public int getValuePrice(String data) {
        int price = 9;
        return price;
    }

    void estimatePrice(String currentDateTime, String from, String to, String distance,
            String type, String weight, String dimention, String duration, String value){

        price = 0;
        if(!currentDateTime.equals("")){price += getCurrentDateTimePrice("");}
        if(!from.equals("")){price += getFromPrice("");}
        if(!to.equals("")){price += getToPrice("");}
        if(!distance.equals("")){price += getDistancePrice("");}
        if(!type.equals("")){price += getTypePrice("");}
        if(!weight.equals("")){price += getWeightPrice("");}
        if(!dimention.equals("")){price += getDimentionPrice("");}
        if(!duration.equals("")){price += getDurationPrice("");}
        if(value.equals("")){price += getValuePrice("");}

        textView.setText(String.valueOf(price));
    }

 }
