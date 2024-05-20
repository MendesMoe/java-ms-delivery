package com.postech.msdelivery.repository;


import com.postech.msdelivery.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Driver findByEmail(String email);
    Optional<Driver> findFirstByAvailableTrue();

}

