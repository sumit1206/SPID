package com.sumit.spid;

import static java.lang.Math.*;

//0 is towards the North, 90 - East, 180 - South, 270 - West. And all between, i.e. 45 is North East.
public class CoordinateManager {

    static void findLatLonFromDistance(double latitude, double longitude, double distanceInMetres, double bearing) {
        double brngRad = toRadians(bearing);
        double latRad = toRadians(latitude);
        double lonRad = toRadians(longitude);
        int earthRadiusInMetres = 6371000;
        double distFrac = distanceInMetres / earthRadiusInMetres;

        double latitudeResult = asin(sin(latRad) * cos(distFrac) + cos(latRad) * sin(distFrac) * cos(brngRad));
        double a = atan2(sin(brngRad) * sin(distFrac) * cos(latRad), cos(distFrac) - sin(latRad) * sin(latitudeResult));
        double longitudeResult = (lonRad + a + 3 * PI) % (2 * PI) - PI;
        System.out.println("latitude: " + toDegrees(latitudeResult) + ", longitude: " + toDegrees(longitudeResult));
    }

    public static double findDistance(String lat1String, String lon1String, String lat2String,
                                  String lon2String, double el1, double el2) {

        Double lat1 = Double.valueOf(lat1String);
        Double lon1 = Double.valueOf(lon1String);
        Double lat2 = Double.valueOf(lat2String);
        Double lon2 = Double.valueOf(lon2String);

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}
