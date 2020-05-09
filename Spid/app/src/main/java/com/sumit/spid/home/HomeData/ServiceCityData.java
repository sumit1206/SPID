package com.sumit.spid.home.HomeData;

public class ServiceCityData {
    int thumbnail;
    String city_name;

    public ServiceCityData(int thumbnail, String city_name) {
        this.thumbnail = thumbnail;
        this.city_name = city_name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
