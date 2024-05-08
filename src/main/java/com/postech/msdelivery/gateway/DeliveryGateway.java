package com.postech.msdelivery.gateway;

import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.interfaces.IDeliveryGateway;
import com.postech.msdelivery.repository.DeliveryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DeliveryGateway implements IDeliveryGateway {
    private final DeliveryRepository deliveryRepository;

    public DeliveryGateway(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery updateDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public boolean deleteDelivery(String strId) {
        try {
            UUID uuid = UUID.fromString(strId);
            deliveryRepository.deleteById(uuid);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Delivery findDelivery(String strId) {
        try {
            UUID uuid = UUID.fromString(strId);
            return deliveryRepository.findById(uuid).orElseThrow();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Delivery> listAllDeliverys() {
        return deliveryRepository.findAll();
    }
}
