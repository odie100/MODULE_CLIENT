package com.akata.clientservice.repository;

import com.akata.clientservice.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact c where c.client.id = ?1")
    List<Contact> findAllByIdUser(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Contact c SET  c.value = ?1 WHERE c.client.id = ?2 AND type = 'email'")
    int updateEmail(String value, Long client_id);

    @Modifying
    @Transactional
    @Query("UPDATE Contact c SET c.value = ?1 WHERE c.client.id = ?2 AND type = 'tel'")
    int updateTel(String value, Long client_id);

    @Query("SELECT c FROM Contact c WHERE c.type = ?1 and c.client.id = ?2")
    Contact findExisting(String type, Long id);

    Contact findContactByClientIdAndType(Long id, String type);
}
