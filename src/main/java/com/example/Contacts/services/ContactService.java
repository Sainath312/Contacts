package com.example.Contacts.services;

import com.example.Contacts.entity.ContactEntity;
import com.example.Contacts.entity.UserInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactService {
    ContactEntity newContact(ContactEntity con);
    List<ContactEntity> listContents();
    List<ContactEntity> sortByUserFirstName();
    ResponseEntity<String> findContactById(Long id);
    ResponseEntity<String> updateContact(ContactEntity contact, Long id);
    ResponseEntity<String> addUserAndAdmin(UserInfo userInfo);
    ResponseEntity<String> getContactStatusById(Long contactId);
}

