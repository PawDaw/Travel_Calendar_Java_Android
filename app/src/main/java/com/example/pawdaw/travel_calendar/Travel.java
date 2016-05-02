package com.example.pawdaw.travel_calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pawdaw on 17/04/16.
 */
public class Travel {

    private String title;
    private String description;
    private String address;
    private String date;
    private String imageURL;
    private String visitAgain;

    public Travel(String title, String description, String address, String date, String visitAgain, String imageURL) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.date = date;
        this.imageURL = imageURL;
        this.visitAgain = visitAgain;
    }

    public String getVisitAgain() {
        return visitAgain;
    }

    public void setVisitAgain(String visitAgain) {
        this.visitAgain = visitAgain;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getImageURL() {

        return imageURL;
    }

    public void setImageURL(String imageURL) {

        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "title: " + title + ", description: " + description + ", address: " + address + ", date: " + date;
    }
}

