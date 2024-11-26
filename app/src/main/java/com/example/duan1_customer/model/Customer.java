package com.example.duan1_customer.model;
import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@Keep
public class Customer implements Serializable {
    private  String _id;
    private String parent_id;
    private String branch;
    private String date;
    private String zalo;
    private ArrayList<String> history;
    private ArrayList<String> history_edit;
    private String date_of_birth;
    private String source;
    private String name;
    private String phone;
    private ArrayList<String> record;
    private String address;
    private int rate;
    private String social_media;
    private ArrayList<String> bill_media;
    private String note;
    private String sex;
    private ArrayList<String> service;
    private ArrayList<String> media;
    private String service_detail;
    private String pancake;
    private String schedule;
    private String register_schedule;
    private Profile_User telesales;
    private String status;
    private String status_data;
    private String sales;
    private String sales_assistant;
    private int total_amount;
    private int amount_paid;
    private int outstanding_amount;
    private String reason_failure;
    private String final_status_branch;
    private String final_status;
    private String final_status_date;
    private String created_at;
    private String updated_at;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getZalo() {
        return zalo;
    }

    public void setZalo(String zalo) {
        this.zalo = zalo;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public ArrayList<String> getHistory_edit() {
        return history_edit;
    }

    public void setHistory_edit(ArrayList<String> history_edit) {
        this.history_edit = history_edit;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public ArrayList<String> getRecord() {
        return record;
    }

    public void setRecord(ArrayList<String> record) {
        this.record = record;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getSocial_media() {
        return social_media;
    }

    public void setSocial_media(String social_media) {
        this.social_media = social_media;
    }

    public ArrayList<String> getBill_media() {
        return bill_media;
    }

    public void setBill_media(ArrayList<String> bill_media) {
        this.bill_media = bill_media;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ArrayList<String> getService() {
        return service;
    }

    public void setService(ArrayList<String> service) {
        this.service = service;
    }

    public ArrayList<String> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<String> media) {
        this.media = media;
    }

    public String getService_detail() {
        return service_detail;
    }

    public void setService_detail(String service_detail) {
        this.service_detail = service_detail;
    }

    public String getPancake() {
        return pancake;
    }

    public void setPancake(String pancake) {
        this.pancake = pancake;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getRegister_schedule() {
        return register_schedule;
    }

    public void setRegister_schedule(String register_schedule) {
        this.register_schedule = register_schedule;
    }

    public Profile_User getTelesales() {
        return telesales;
    }

    public void setTelesales(Profile_User telesales) {
        this.telesales = telesales;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_data() {
        return status_data;
    }

    public void setStatus_data(String status_data) {
        this.status_data = status_data;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getSales_assistant() {
        return sales_assistant;
    }

    public void setSales_assistant(String sales_assistant) {
        this.sales_assistant = sales_assistant;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public int getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(int amount_paid) {
        this.amount_paid = amount_paid;
    }

    public int getOutstanding_amount() {
        return outstanding_amount;
    }

    public void setOutstanding_amount(int outstanding_amount) {
        this.outstanding_amount = outstanding_amount;
    }

    public String getReason_failure() {
        return reason_failure;
    }

    public void setReason_failure(String reason_failure) {
        this.reason_failure = reason_failure;
    }

    public String getFinal_status_branch() {
        return final_status_branch;
    }

    public void setFinal_status_branch(String final_status_branch) {
        this.final_status_branch = final_status_branch;
    }

    public String getFinal_status() {
        return final_status;
    }

    public void setFinal_status(String final_status) {
        this.final_status = final_status;
    }

    public String getFinal_status_date() {
        return final_status_date;
    }

    public void setFinal_status_date(String final_status_date) {
        this.final_status_date = final_status_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Customer(String _id, String branch, String date, String zalo, ArrayList<String> history, ArrayList<String> history_edit, String date_of_birth, String source, String name, String phone, ArrayList<String> record, String address, int rate, String social_media, ArrayList<String> bill_media, String note, String sex, ArrayList<String> service, ArrayList<String> media, String service_detail, String pancake, String schedule, String register_schedule, Profile_User telesales, String status, String status_data, String sales, String sales_assistant, int total_amount, int amount_paid, int outstanding_amount, String reason_failure, String final_status_branch, String final_status, String final_status_date, String created_at, String updated_at) {
        this._id = _id;
        this.branch = branch;
        this.date = date;
        this.zalo = zalo;
        this.history = history;
        this.history_edit = history_edit;
        this.date_of_birth = date_of_birth;
        this.source = source;
        this.name = name;
        this.phone = phone;
        this.record = record;
        this.address = address;
        this.rate = rate;
        this.social_media = social_media;
        this.bill_media = bill_media;
        this.note = note;
        this.sex = sex;
        this.service = service;
        this.media = media;
        this.service_detail = service_detail;
        this.pancake = pancake;
        this.schedule = schedule;
        this.register_schedule = register_schedule;
        this.telesales = telesales;
        this.status = status;
        this.status_data = status_data;
        this.sales = sales;
        this.sales_assistant = sales_assistant;
        this.total_amount = total_amount;
        this.amount_paid = amount_paid;
        this.outstanding_amount = outstanding_amount;
        this.reason_failure = reason_failure;
        this.final_status_branch = final_status_branch;
        this.final_status = final_status;
        this.final_status_date = final_status_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Customer() {

    }

    public Customer(String _id, String parent_id, String branch, String date, String zalo, ArrayList<String> history, ArrayList<String> history_edit, String date_of_birth, String source, String name, String phone, ArrayList<String> record, String address, int rate, String social_media, ArrayList<String> bill_media, String note, String sex, ArrayList<String> service, ArrayList<String> media, String service_detail, String pancake, String schedule, String register_schedule, Profile_User telesales, String status, String status_data, String sales, String sales_assistant, int total_amount, int amount_paid, int outstanding_amount, String reason_failure, String final_status_branch, String final_status, String final_status_date, String created_at, String updated_at) {
        this._id = _id;
        this.parent_id = parent_id;
        this.branch = branch;
        this.date = date;
        this.zalo = zalo;
        this.history = history;
        this.history_edit = history_edit;
        this.date_of_birth = date_of_birth;
        this.source = source;
        this.name = name;
        this.phone = phone;
        this.record = record;
        this.address = address;
        this.rate = rate;
        this.social_media = social_media;
        this.bill_media = bill_media;
        this.note = note;
        this.sex = sex;
        this.service = service;
        this.media = media;
        this.service_detail = service_detail;
        this.pancake = pancake;
        this.schedule = schedule;
        this.register_schedule = register_schedule;
        this.telesales = telesales;
        this.status = status;
        this.status_data = status_data;
        this.sales = sales;
        this.sales_assistant = sales_assistant;
        this.total_amount = total_amount;
        this.amount_paid = amount_paid;
        this.outstanding_amount = outstanding_amount;
        this.reason_failure = reason_failure;
        this.final_status_branch = final_status_branch;
        this.final_status = final_status;
        this.final_status_date = final_status_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
