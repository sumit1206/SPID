package com.example.shop.store;

public class HomeFragmentdata
{
    private String from_station;
    private String to_station;
    private String departure_date;
    private String departure_time;
    private String arrival_date;
    private String arrival_time;
    private String train_no;
    private String parcel_id;
    private String status;

    public HomeFragmentdata(String from_station, String to_station, String departure_date, String departure_time,
                            String arrival_date, String arrival_time, String train_no, String parcel_id, String status)
    {
        this.from_station = from_station;
        this.to_station = to_station;
        this.departure_date = departure_date;
        this.departure_time = departure_time;
        this.arrival_date = arrival_date;
        this.arrival_time = arrival_time;
        this.train_no = train_no;
        this.parcel_id =parcel_id;
        this.status = status;
    }

    public String getFrom_station() {
        return from_station;
    }

    public void setFrom_station(String from_station) {
        this.from_station = from_station;
    }

    public String getTo_station() {
        return to_station;
    }

    public void setTo_station(String to_station) {
        this.to_station = to_station;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(String departure_date) {
        this.departure_date = departure_date;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getTrain_no() {
        return train_no;
    }

    public void setTrain_no(String train_no) {
        this.train_no = train_no;
    }

    public String getParcel_id() {
        return parcel_id;
    }

    public void setParcel_id(String parcel_id) {
        this.parcel_id = parcel_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

