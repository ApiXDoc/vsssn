package com.mradkingshop.vsssn.admin.modal;


import com.google.firebase.Timestamp;

public class Real_donation_Modal extends DocId{

    String plane_name,name,state,donation_amount,phone,ItemId;
    Timestamp timestamp;
    public Real_donation_Modal() {


    }


    public Real_donation_Modal(String plane_name, String name, String state, String donation_amount, String phone, String itemId, Timestamp timestamp) {
        this.plane_name = plane_name;
        this.name = name;
        this.state = state;
        this.donation_amount = donation_amount;
        this.phone = phone;
        ItemId = itemId;
        this.timestamp = timestamp;
    }

    public String getPlane_name() {
        return plane_name;
    }

    public void setPlane_name(String plane_name) {
        this.plane_name = plane_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDonation_amount() {
        return donation_amount;
    }

    public void setDonation_amount(String donation_amount) {
        this.donation_amount = donation_amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
