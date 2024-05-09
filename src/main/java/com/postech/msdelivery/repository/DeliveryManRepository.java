package com.postech.msdelivery.repository;

import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryManRepository extends JpaRepository<DeliveryMan, UUID> {
}
