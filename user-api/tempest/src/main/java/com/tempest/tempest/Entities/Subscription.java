package com.tempest.tempest.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String user;
    private String countryCode;
    private String province;
    private String city;
    private float lat;
    private float lon;

    public Subscription(
        String _id,
        String _user, 
        String _countryCode, 
        String _province, 
        String _city, 
        float _lat, 
        float _lon) {
            this.id = _id;
            this.user = _user;
            this.countryCode = _countryCode;
            this.province = _province;
            this.city = _city;
            this.lat = _lat;
            this.lon = _lon;
    }
    public Subscription(
        String _user, 
        String _countryCode, 
        String _province, 
        String _city, 
        float _lat, 
        float _lon) {
            this.user = _user;
            this.countryCode = _countryCode;
            this.province = _province;
            this.city = _city;
            this.lat = _lat;
            this.lon = _lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String user() {
        return user;
    }

    public String countryCode() {
        return countryCode;
    }

    public String province() {
        return province;
    }

    public String city() {
        return city;
    }

    public float lat() {
        return lat;
    }

    public float lon() {
        return lon;
    }

}