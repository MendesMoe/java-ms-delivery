package com.postech.msdelivery.repository;


import com.postech.msdelivery.entity.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {

    DeliveryPerson findByEmail(String email);
}

