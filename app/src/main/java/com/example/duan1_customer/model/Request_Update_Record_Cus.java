package com.example.duan1_customer.model;

public class Request_Update_Record_Cus {
    public String customer_id;
    public String record;

    public Request_Update_Record_Cus(String customer_id, String record) {
        this.customer_id = customer_id;
        this.record = record;
    }
}
