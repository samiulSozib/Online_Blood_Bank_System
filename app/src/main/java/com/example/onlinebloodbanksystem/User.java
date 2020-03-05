package com.example.onlinebloodbanksystem;

public class User {

   public String uid,email,password,phone,nAME,lOCATION,donationDATE,blood_group,dISTRICT;

    public User() {

    }

    public User(String uid, String email, String password, String phone, String nAME, String lOCATION, String donationDATE, String blood_group, String dISTRICT) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nAME = nAME;
        this.lOCATION = lOCATION;
        this.donationDATE = donationDATE;
        this.blood_group = blood_group;
        this.dISTRICT = dISTRICT;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getnAME() {
        return nAME;
    }

    public void setnAME(String nAME) {
        this.nAME = nAME;
    }

    public String getlOCATION() {
        return lOCATION;
    }

    public void setlOCATION(String lOCATION) {
        this.lOCATION = lOCATION;
    }

    public String getDonationDATE() {
        return donationDATE;
    }

    public void setDonationDATE(String donationDATE) {
        this.donationDATE = donationDATE;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getdISTRICT() {
        return dISTRICT;
    }

    public void setdISTRICT(String dISTRICT) {
        this.dISTRICT = dISTRICT;
    }
}
