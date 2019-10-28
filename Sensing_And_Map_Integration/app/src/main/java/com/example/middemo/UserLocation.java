package com.example.middemo;

public class UserLocation {
    private double Longitude;
    private double Latitude;

    public UserLocation(double lon, double lat){
        this.Longitude = lon;
        this.Latitude = lat;
    }

    public void setLongitude(double d){
        this.Longitude = d;
    }
    public void setLatitude(double d){
        this.Latitude = d;
    }

    public double getLongitude(){
        return Longitude;
    }
    public double getLatitude(){
        return Latitude;
    }
}
