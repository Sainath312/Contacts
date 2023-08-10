package com.example.Contacts.exceptions;

public class ContactNotFound extends RuntimeException{
    public ContactNotFound(String message) {
        super(message);
    }
}
