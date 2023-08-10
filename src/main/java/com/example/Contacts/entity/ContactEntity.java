package com.example.Contacts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Entity
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Table(name = "Contacts")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactId")
    private long contactId;

    @NotEmpty(message = "First Name Should Not Be Null")
    @Column(name = "firstName")
    private String firstName;

    @NotEmpty(message = "Last Name Should Not Be Null")
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    public Set<NumberEntity> phoneNumber= new HashSet<>();

    @Email(message = "Enter Valid EmailID")
    @NotEmpty(message = "a")
    @Column(unique = true,name = "email")
    private String emailId;

    @Temporal(TemporalType.TIMESTAMP)
    @JoinColumn(name = "updated_at")
    private Date updatedAt;

    public Date getUpdatedAt() {
        return updatedAt;
    }

//    @PreUpdate
//    public void onUpdate(){
//        updatedAt = new Date();
//    }

    public long getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<NumberEntity> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Set<NumberEntity> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @Email(message = "Enter Valid EmailID") @NotEmpty(message = "a") String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }


}