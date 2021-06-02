package com.work.myapp;

import java.util.HashMap;
import java.util.Map;

public class locationdata {
    public String id;
    public String image;
    public String street;
    public String time;
    public String info;

    public locationdata(){}

    public String getInfo() {return info;}

    public void setInfo(String info) {
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


