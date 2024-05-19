package com.postech.msdelivery.repository;

import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    // Find deliveries by status
    List<Delivery> findByStatus(DeliveryStatus status);

    // Find deliveries by order ID
    Optional<Delivery> findByOrderId(Long orderId);

}
