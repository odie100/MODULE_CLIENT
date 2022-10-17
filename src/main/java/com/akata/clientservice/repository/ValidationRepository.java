package com.akata.clientservice.repository;

import com.akata.clientservice.entities.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationRepository extends JpaRepository<ValidationCode, Long> {
    @Query("SELECT v FROM ValidationCode v WHERE v.id_user = ?1")
    ValidationCode getByIdUser(Long id_user);
}
