package com.example.duan1_customer.model;
import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class Profile_User implements Serializable {
    private String _id;
    private String name;
    private String email;
    private String role;
    private String status;
    private String password;

    private String created_at;
    private String updated_at;
    private String[] data_customer;

    public Profile_User(String _id, String name, String email, String role, String status) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public Profile_User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String[] getData_customer() {
        return data_customer;
    }

    public void setData_customer(String[] data_customer) {
        this.data_customer = data_customer;
    }

    public Profile_User(String _id, String name, String email, String role, String status, String created_at, String updated_at, String[] data_customer) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.data_customer = data_customer;
    }

    @Override
    public String toString() {
        return "Profile_User{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
