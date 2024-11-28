package com.example.duan1_customer.model;

public class Request_Update_Minute_Tele {
    public String telesale_id;
    public double minutes;
    public String service;
    public String date;
    public boolean missed;

    public Request_Update_Minute_Tele(String telesale_id, double minutes, String service, String date, boolean missed) {
        this.telesale_id = telesale_id;
        this.minutes = minutes;
        this.service = service;
        this.date = date;
        this.missed = missed;
    }

    public Request_Update_Minute_Tele(String telesale_id, double minutes, String service, String date) {
        this.telesale_id = telesale_id;
        this.minutes = minutes;
        this.service = service;
        this.date = date;
    }
    public Request_Update_Minute_Tele(){}
}
