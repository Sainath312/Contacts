package com.example.Contacts.repository;

import com.example.Contacts.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

@Repository
public interface ContactRepo extends JpaRepository<ContactEntity,Long> {
}
