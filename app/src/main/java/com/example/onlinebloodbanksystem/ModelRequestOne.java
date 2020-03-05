package com.example.onlinebloodbanksystem;

public class ModelRequestOne {
    String request_blood_group,request_date,request_id,request_location,request_need_details,request_phone;

    public ModelRequestOne() {

    }

    public ModelRequestOne(String request_blood_group, String request_date, String request_id, String request_location, String request_need_details, String request_phone) {
        this.request_blood_group = request_blood_group;
        this.request_date = request_date;
        this.request_id = request_id;
        this.request_location = request_location;
        this.request_need_details = request_need_details;
        this.request_phone = request_phone;
    }

    public String getRequest_blood_group() {
        return request_blood_group;
    }

    public void setRequest_blood_group(String request_blood_group) {
        this.request_blood_group = request_blood_group;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getRequest_location() {
        return request_location;
    }

    public void setRequest_location(String request_location) {
        this.request_location = request_location;
    }

    public String getRequest_need_details() {
        return request_need_details;
    }

    public void setRequest_need_details(String request_need_details) {
        this.request_need_details = request_need_details;
    }

    public String getRequest_phone() {
        return request_phone;
    }

    public void setRequest_phone(String request_phone) {
        this.request_phone = request_phone;
    }
}
