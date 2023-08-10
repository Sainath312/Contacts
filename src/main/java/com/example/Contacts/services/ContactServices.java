package com.example.Contacts.services;

import com.example.Contacts.entity.ContactEntity;
import com.example.Contacts.entity.NumberEntity;
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

    public ResponseEntity<String> findContactById(Long id) {
        ContactEntity user = contentRepo.findById(id).orElseThrow(() -> new ContactNotFound("not found"));
        logger.info("User is found: " + user.getContactId());
        StringBuilder response = new StringBuilder("Contact ID: " + user.getContactId() + "\n" +
                "First Name: " + user.getFirstName() + "\n" +
                "Last Name: " + user.getLastName() + "\n" +
                "Phone Numbers:" + "\n");
            for (NumberEntity phoneNumber : user.getPhoneNumber()) {
                response.append("   Number: ").append(phoneNumber.getPhoneNumber()).append(", Type: ").append(phoneNumber.getType()).append("\n");
            }
            response.append("Email: ").append(user.getEmailId()).append("\n");
            return ResponseEntity.ok().header("Content-type", "application/json").body(response.toString());
        }

    public ResponseEntity<String> updateContact(ContactEntity contact, Long id) {
        ContactEntity user = contentRepo.findById(id).orElseThrow(() -> new ContactNotFound("not found"));
        logger.info("Contact Id : "+contact.getContactId()+" is Found");
        user.setFirstName(contact.getFirstName());
        user.setLastName(contact.getLastName());
        user.setEmailId(contact.getEmailId());
        user.setPhoneNumber(contact.getPhoneNumber());
        contentRepo.save(user);
        logger.info("New Contact Is Updated");
        return ResponseEntity.ok().header("Content-type", "application/json").body(user.toString());
    }

    public ResponseEntity<String> addUserAndAdmin(UserInfo userInfo) {
        List<UserInfo> userList = repository.findAll();
        for (UserInfo user : userList) {
            flag = user.getUserName().equals(userInfo.getUserName());
        }
        if(!flag) {
            userInfo.setPassKey(passwordEncoder.encode(userInfo.getPassKey()));
            repository.save(userInfo);
            logger.info("User Saved successfully");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type","application/json").body("{\"message\": \"User Saved\"}");
        }
        logger.warn("User already exists");
        throw new UserAlreadyExists("User already exists");
    }

    public ResponseEntity<String> getContactStatusById(Long id) {
        ContactEntity user = contentRepo.findById(id).orElseThrow(() -> new ContactNotFound("not found"));
        logger.info("Contact Id :"+user.getContactId()+" is Found");
        Date lastUpdatedTime = user.getUpdatedAt();
        if (lastUpdatedTime != null) {
            logger.info("Phone Number Is Updated at time " + lastUpdatedTime);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Content-Type", "application/json")
                    .body("Contact was last updated on: \": " + lastUpdatedTime );
        } else {
            logger.info("Phone Number is not Updated");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Content-Type", "application/json")
                    .body("{\"message\": \"Contact found but not updated yet.\"}");
        }
    }

}


