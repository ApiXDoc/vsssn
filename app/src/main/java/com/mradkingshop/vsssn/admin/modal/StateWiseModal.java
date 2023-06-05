package com.mradkingshop.vsssn.admin.modal;

public class StateWiseModal {

    String state_name,number_donator,total_collection;

    public StateWiseModal(){

    }

    public StateWiseModal(String state_name, String number_donator, String total_collection) {

        this.state_name = state_name;
        this.number_donator = number_donator;
        this.total_collection = total_collection;
    }


    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getNumber_donator() {
        return number_donator;
    }

    public void setNumber_donator(String number_donator) {
        this.number_donator = number_donator;
    }

    public String getTotal_collection() {
        return total_collection;
    }

    public void setTotal_collection(String total_collection) {
        this.total_collection = total_collection;
    }
}
