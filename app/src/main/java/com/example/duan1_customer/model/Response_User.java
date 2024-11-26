package com.example.duan1_customer.model;
public class Response_User {
    class Errors {
        class Email {
            public String type;
            public String value;
            public String msg;
            public String path;
            public String location;

            public Email(String type, String value, String msg, String path, String location) {
                this.type = type;
                this.value = value;
                this.msg = msg;
                this.path = path;
            }
        }
        public Email email;

        public Errors(Email email) {
            this.email = email;
        }
    }
    public boolean success;
    public String message;
    public Result_User result;
    public Errors errors;

    public Response_User(boolean success, String message, Result_User result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    public Response_User(String message, Errors errors) {
        this.message = message;
        this.errors = errors;
    }
}
