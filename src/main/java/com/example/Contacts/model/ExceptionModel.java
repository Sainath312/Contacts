package com.example.Contacts.model;

import lombok.Data;


@Data
public class ExceptionModel {
    public String status;
    public String errorMessage;
    public String path;

    public ExceptionModel(String status, String errorMessage, String path) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.path = path;
    }

}
