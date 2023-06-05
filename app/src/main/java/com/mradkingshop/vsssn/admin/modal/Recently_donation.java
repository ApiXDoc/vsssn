package com.mradkingshop.vsssn.admin.modal;

public class Recently_donation extends DocId{

    String plane_name,name,state,donation_amount,phone_number;
    public Recently_donation() {


    }

    public Recently_donation(String plane_name, String name, String state, String donation_amount, String phone_number) {
        this.plane_name = plane_name;
        this.name = name;
        this.state = state;
        this.donation_amount = donation_amount;
        this.phone_number = phone_number;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
