package com.akata.clientservice.repository;

import com.akata.clientservice.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Location c SET c.address = ?1, c.country = ?2, c.town = ?3 where c.id = ?4")
    int update(String address, String country, String town, Long id);
}
