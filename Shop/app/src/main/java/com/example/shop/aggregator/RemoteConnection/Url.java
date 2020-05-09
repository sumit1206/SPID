package com.example.shop.aggregator.RemoteConnection;

public class Url {

    String ROOT_URL = "http://197.189.202.8/spid_dev/";

    String URL_AGGREGATORLOGINFUNCTIONALITY = ROOT_URL+"aggregator_login_functionality.php";
    String URL_AFTERCONFIRMEDBYCARRIER = ROOT_URL+"after_confirmed_by_carrier.php";
    String URL_FETCHDELIVERYDETAILS = ROOT_URL+"featch_delivery_details.php";
    String URL_AGGRIGATORFUNCTIONALITY = ROOT_URL+"aggregator_funtionality.php";
    String URL_JOURNEYUPLOAD  = ROOT_URL+"journey_details_add.php";
    String URL_WHOLEWALLETFUNCTIONALITY = ROOT_URL+"whole_wallet_funtionality.php";
    String URL_EXTRASADDED = ROOT_URL+"extras_added.php";


    public String getURL_EXTRASADDED() {
        return URL_EXTRASADDED;
    }

    public String getURL_WHOLEWALLETFUNCTIONALITY() {
        return URL_WHOLEWALLETFUNCTIONALITY;
    }

    public String getURL_JOURNEYUPLOAD() {
        return URL_JOURNEYUPLOAD;
    }

    public String getURL_AGGREGATORLOGINFUNCTIONALITY() {
        return URL_AGGREGATORLOGINFUNCTIONALITY;
    }

    public String getURL_AFTERCONFIRMEDBYCARRIER() {
        return URL_AFTERCONFIRMEDBYCARRIER;
    }

    public String getURL_FETCHDELIVERYDETAILS() {
        return URL_FETCHDELIVERYDETAILS;
    }

    public String getURL_AGGRIGATORFUNCTIONALITY() {
        return URL_AGGRIGATORFUNCTIONALITY;
    }

}
