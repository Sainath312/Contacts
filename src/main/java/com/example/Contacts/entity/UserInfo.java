package com.example.Contacts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="userinfo")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String name;
    @NotBlank
    @Pattern(regexp ="^[7-9]\\d{9}$",message = "Enter Valid Indian PhoneNumber")
    @Column(unique = true)
    public String phoneNumber;
    @NotBlank
    @Email(message = "enter Valid Email")
    @Column(unique = true)
    public String emailID;
    @NotBlank(message = "Enter Valid Password")
    public String password;
    @NotBlank(message = "Enter Valid Role")
    public String role;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailID() {
        return emailID;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

}
