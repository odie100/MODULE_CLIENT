package com.akata.clientservice.repository;

import com.akata.clientservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c JOIN Contact ct on c.id = ct.user.id WHERE ct.value = ?1 AND c.password = ?2")
    Client login(String email, String password);
}
