package com.example.loganpatino.hackmit_2016;

/**
 * Created by loganpatino on 9/17/16.
 */
public class Event {
    private String latitude;
    private String longitude;
    private String name;

    public Event() {

    }

    public Event(String latitude, String longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }
}
