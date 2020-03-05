package com.example.onlinebloodbanksystem;

public class Donor1 {

    String donorName,donorGroup,donorPhone,donorLocation;

    public Donor1() {

    }

    public Donor1(String donorName, String donorGroup, String donorPhone, String donorLocation) {
        this.donorName = donorName;
        this.donorGroup = donorGroup;
        this.donorPhone = donorPhone;
        this.donorLocation = donorLocation;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorGroup() {
        return donorGroup;
    }

    public void setDonorGroup(String donorGroup) {
        this.donorGroup = donorGroup;
    }

    public String getDonorPhone() {
        return donorPhone;
    }

    public void setDonorPhone(String donorPhone) {
        this.donorPhone = donorPhone;
    }

    public String getDonorLocation() {
        return donorLocation;
    }

    public void setDonorLocation(String donorLocation) {
        this.donorLocation = donorLocation;
    }
}
