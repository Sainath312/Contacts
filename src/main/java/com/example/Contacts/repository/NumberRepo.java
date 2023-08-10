package com.example.Contacts.repository;

import com.example.Contacts.entity.NumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberRepo extends JpaRepository<NumberEntity,Long> {
}
