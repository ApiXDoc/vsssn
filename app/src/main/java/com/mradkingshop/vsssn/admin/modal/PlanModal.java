package com.mradkingshop.vsssn.admin.modal;

import com.google.firebase.Timestamp;

public class PlanModal extends DocId{

String plane_name,donation_amount,plane_image,number_pepople_donate,ItemId
    ,donation_date,name,phone,user_uid,state,email_id,admin_message_token,pin_code
        ,proof_image_url,proof_spending,proof_heading,proof_discreption,date;


Timestamp timestamp;
    public PlanModal() {


    }


    public PlanModal(String plane_name, String donation_amount, String plane_image, String number_pepople_donate, String itemId, String donation_date, String name, String phone, String user_uid, String state, String email_id, String admin_message_token, String pin_code, String proof_image_url, String proof_spending, String proof_heading, String proof_discreption, String date, Timestamp timestamp) {
        this.plane_name = plane_name;
        this.donation_amount = donation_amount;
        this.plane_image = plane_image;
        this.number_pepople_donate = number_pepople_donate;
        ItemId = itemId;
        this.donation_date = donation_date;
        this.name = name;
        this.phone = phone;
        this.user_uid = user_uid;
        this.state = state;
        this.email_id = email_id;
        this.admin_message_token = admin_message_token;
        this.pin_code = pin_code;
        this.proof_image_url = proof_image_url;
        this.proof_spending = proof_spending;
        this.proof_heading = proof_heading;
        this.proof_discreption = proof_discreption;
        this.date = date;
        this.timestamp = timestamp;
    }

    public String getPlane_name() {
        return plane_name;
    }

    public void setPlane_name(String plane_name) {
        this.plane_name = plane_name;
    }

    public String getDonation_amount() {
        return donation_amount;
    }

    public void setDonation_amount(String donation_amount) {
        this.donation_amount = donation_amount;
    }

    public String getPlane_image() {
        return plane_image;
    }

    public void setPlane_image(String plane_image) {
        this.plane_image = plane_image;
    }

    public String getNumber_pepople_donate() {
        return number_pepople_donate;
    }

    public void setNumber_pepople_donate(String number_pepople_donate) {
        this.number_pepople_donate = number_pepople_donate;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getDonation_date() {
        return donation_date;
    }

    public void setDonation_date(String donation_date) {
        this.donation_date = donation_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getAdmin_message_token() {
        return admin_message_token;
    }

    public void setAdmin_message_token(String admin_message_token) {
        this.admin_message_token = admin_message_token;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public String getProof_image_url() {
        return proof_image_url;
    }

    public void setProof_image_url(String proof_image_url) {
        this.proof_image_url = proof_image_url;
    }

    public String getProof_spending() {
        return proof_spending;
    }

    public void setProof_spending(String proof_spending) {
        this.proof_spending = proof_spending;
    }

    public String getProof_heading() {
        return proof_heading;
    }

    public void setProof_heading(String proof_heading) {
        this.proof_heading = proof_heading;
    }

    public String getProof_discreption() {
        return proof_discreption;
    }

    public void setProof_discreption(String proof_discreption) {
        this.proof_discreption = proof_discreption;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

