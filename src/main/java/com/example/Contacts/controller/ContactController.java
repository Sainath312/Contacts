package com.example.Contacts.controller;

import com.example.Contacts.entity.ContactEntity;
import com.example.Contacts.entity.UserInfo;
import com.example.Contacts.model.AuthRequest;
import com.example.Contacts.services.ContactServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.example.Contacts.services.JwtService;

import java.util.List;
@RestController
@RequestMapping("/api")
public class ContactController {
    @Autowired
    ContactServices services;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("contact/newContact")
    public ContactEntity addContent(@RequestBody @Valid ContactEntity contact){
        return services.newContact(contact);
    }

    @GetMapping("/listContacts")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<ContactEntity> listOfContact(){
        return services.listContents();
    }

    @GetMapping("contact/sortByUserName")
    public List<ContactEntity> sortUserName(){
        return services.sortByUserFirstName();
    }

    @GetMapping("/getByUser/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> getByUserId(@PathVariable Long id){
        return services.findContactById(id);
    }
    @PutMapping("contact/updateContact/{id}")
    public ResponseEntity<String> updateContact(@RequestBody @Valid ContactEntity contact,@PathVariable Long id){
        return services.updateContact(contact,id);
    }
    @PostMapping("/contact/new")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo){
        return services.addUserAndAdmin(userInfo);
    }
    @PostMapping("/contact/authenticate")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Username " + authRequest.getUsername() + " not found");
        }

    }
    @GetMapping("/contact/getUpdateStatus/{id}")
    public ResponseEntity<String> getUpdateStatus(@PathVariable Long id){
        return services.getContactStatusById(id);
    }
}
