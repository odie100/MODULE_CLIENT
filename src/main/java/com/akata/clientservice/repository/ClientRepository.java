package com.akata.clientservice.repository;

import com.akata.clientservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c.id FROM Client c JOIN Contact ct on c.id = ct.client.id WHERE ct.value = ?1 AND c.password = ?2")
    Long login(String email, String password);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.username = ?1, c.name=?2, c.type=?3, c.description=?4, c.photo = ?5 where c.id=?6")
    int update(String username, String name, String type, String description, String photo, Long id);
}