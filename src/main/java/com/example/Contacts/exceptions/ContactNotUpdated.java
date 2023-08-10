package com.example.Contacts.exceptions;

public class ContactNotUpdated extends RuntimeException{
    public ContactNotUpdated(String message) {
        super(message);
    }
}
