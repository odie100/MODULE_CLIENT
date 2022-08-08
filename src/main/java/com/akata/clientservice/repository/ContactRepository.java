package com.akata.clientservice.repository;

import com.akata.clientservice.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact c where c.user.id = ?1")
    List<Contact> findAllByIdUser(Long id);
}
