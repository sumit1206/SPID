package com.example.shop;

import com.example.shop.store.remote.DbHelper;

public class Extras {
//    static void fetch(){
//        String kdt[],kv[],key,value;
//        String pnr = "", trainNo = "", date = "", time = "", to = "", from = "", dateTime= "";
//        String sample = "PNR:2201302023,TRN:12372,DOJ:03-10-19,SL,DLI-HWH,DP:15:05,\n" +
//                "DIPTESH CHAKRABO+3,WL 18,WL 19,WL 20,WL 21,\n" +
//                "Fare:3140,C Fee:17.7+PG";
//        sample = sample.replace("\n", "");
//        String allData[] = sample.split(",");
//        for (String data:allData) {
//            if(occurance(data,':') == 1) {
//                kv = data.split(":");
//                key = kv[0];
//                value = kv[1];
//                if(key.toUpperCase().contains("PNR"))
//                    pnr = value;
//                else if(key.toUpperCase().contains("TRN") || key.toUpperCase().contains("TRAIN"))
//                    trainNo = value;
//                else if(key.toUpperCase().contains("DOJ")) {
//                    date = value;
//                    String dmy[] = date.split("-");
//                    String d = dmy[0];
//                    String m = dmy[1];
//                    String y = dmy[2];
//                    date = "20"+y+"-"+m+"-"+d;
//                }
//            }else if(occurance(data,':') == 2){
//                kdt = data.split(":");
//                key = kdt[0];
//                if(key.toUpperCase().contains("DP") || key.contains("Dep")){
//                    time = kdt[1]+":"+kdt[2];
//                }
//            }else if(occurance(data,'-') == 1){
//                kv = data.split("-");
//                from = kv[0];
//                to = kv[1];
//            }
//        }
////        dateTime = date+" "+time+":00";
//
//        System.out.println(pnr);
//        System.out.println(trainNo);
//        System.out.println(date);
//        System.out.println(time);
//        System.out.println(to);
//        System.out.println(from);
//
//
//    }

    static int occurance(String s, char c) {
        int l = s.length(), count = 0;
        for (int i = 0; i < l; i++)
            if (s.charAt(i) == c)
                count++;
        return count;
    }


    public static void main(String args[]){
        System.out.println("\"");
//        Extras.fetch();
    }

}
