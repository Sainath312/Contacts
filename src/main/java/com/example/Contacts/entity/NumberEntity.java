package com.example.Contacts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PhoneNumbers")
public class NumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @NotEmpty(message = "Mobile Number Is Mandatory")
    @Pattern(regexp = "^[7-9]\\d{9}$",message = "Enter Valid PhoneNumber")
    public String phoneNumber;
    @NotEmpty(message = "Type Of Number Is Mandatory")
    public String type;

}
