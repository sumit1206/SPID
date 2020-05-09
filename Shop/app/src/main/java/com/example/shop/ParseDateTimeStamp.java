package com.example.shop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseDateTimeStamp {

    String dateTimeStamp;

    public ParseDateTimeStamp(String dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    public String getDateTimeInFormat(String format){
        String dateString;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat dt1 = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dt.parse(dateTimeStamp);
            dateString = dt1.format(date).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            dateString = "recently received";
        }
        return dateString;
    }
//    public String getTime(String format){
//        String timeString;
//        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        SimpleDateFormat dt1 = new SimpleDateFormat(format);
//        Date date = null;
//        try {
//            date = dt.parse(dateTimeStamp);
//            timeString = dt1.format(date).toString();
//        } catch (ParseException e) {
//            e.printStackTrace();
//            timeString = "";
//        }
//        return timeString;
//    }
//    public static void main(String args[]){
//        ParseDateTimeStamp pdts = new ParseDateTimeStamp();
//        String s = pdts.getDate("2019-07-10 10:48:00.000000");
////        String s1 = pdts.getTime("2019-07-10 10:48:00.000000");
//        System.out.println(s);
////        System.out.println(s1);
//    }
}
