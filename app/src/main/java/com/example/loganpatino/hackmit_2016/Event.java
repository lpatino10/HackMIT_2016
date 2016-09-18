package com.example.loganpatino.hackmit_2016;

/**
 * Created by loganpatino on 9/17/16.
 */
public class Event {
    private String latitude;
    private String longitude;
    private String name;
    private String eventType;
    private String eventDescription;

    public Event() {

    }

    public Event(String latitude, String longitude, String name, String eventType, String eventDescription) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
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

    public String getEventType() {
        return eventType;
    }

    public String getEventDescription() {
        return eventDescription;
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

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
