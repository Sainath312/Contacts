package com.example.Contacts.services;

import com.example.Contacts.entity.ContactEntity;
import com.example.Contacts.entity.UserInfo;
import com.example.Contacts.exceptions.ContactNotFound;
import com.example.Contacts.exceptions.UserAlreadyExists;
import com.example.Contacts.repository.ContactRepo;
import com.example.Contacts.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ContactServices implements ContactService {

    public static final Logger logger = LoggerFactory.getLogger(ContactServices.class);
    @Autowired
    ContactRepo contentRepo;
    @Autowired
    UserInfoRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    boolean flag;

    public ContactEntity newContact(ContactEntity con) {
        return contentRepo.save(con);
    }

    public List<ContactEntity> listContents() {
        return contentRepo.findAll();
    }

    public List<ContactEntity> sortByUserFirstName() {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        return contentRepo.findAll(sort);
    }

    public ResponseEntity<ContactEntity> findContactById(Long id) {
        ContactEntity user = contentRepo.findById(id).orElseThrow(() -> new ContactNotFound("not found"));
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<ContactEntity> updateContact(ContactEntity contact, Long id) {
        ContactEntity user = contentRepo.findById(id).orElseThrow(() -> new ContactNotFound("not found"));
        logger.info("Contact Id : "+contact.getContactId()+" is Found");
        user.setFirstName(contact.getFirstName());
        user.setLastName(contact.getLastName());
        user.setEmailId(contact.getEmailId());
        user.setPhoneNumber(contact.getPhoneNumber());
        ContactEntity updatedContact = contentRepo.save(user);
        logger.info("New Contact Is Updated");
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-type", "application/json")
                .body(updatedContact);
        }

    public ResponseEntity<String> addUserAndAdmin(UserInfo userInfo) {
        List<UserInfo> userList = repository.findAll();
        for (UserInfo user : userList) {
            flag = (user.getEmailID().equals(userInfo.getEmailID())||(user.getPhoneNumber().equals(userInfo.getPhoneNumber())));
        }
        if(!flag) {
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            repository.save(userInfo);
            logger.info("User Saved successfully");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type","application/json")
                    .body("{\"message\": \"User Saved\"}");
        }
        logger.warn("User already exists");
        throw new UserAlreadyExists("User already exists");
    }

    public ResponseEntity<String> getContactStatusById(Long id) {
        ContactEntity user = contentRepo.findById(id).orElseThrow(() -> new ContactNotFound("not found"));
        logger.info("Contact Id :" + user.getContactId() + " is Found");
        Date lastUpdatedTime = user.getUpdatedAt();
        if (lastUpdatedTime != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body("{\"Updated Date \": \""+lastUpdatedTime+"\"}");
        }
        String response="Contact Id :" + user.getContactId() + " is not Updated";
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body("{\"Updated Date \": \""+response+"\"}");
    }

}


