package com.postech.msdelivery.gateway;

import com.postech.msdelivery.entity.DeliveryMan;
import com.postech.msdelivery.interfaces.IDeliveryManGateway;
import com.postech.msdelivery.repository.DeliveryManRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DeliveryManGateway implements IDeliveryManGateway {
    private final DeliveryManRepository deliveryManRepository;

    public DeliveryManGateway(DeliveryManRepository deliveryMan) {
        this.deliveryManRepository = deliveryMan;
    }

    @Override
    public DeliveryMan createDeliveryMan(DeliveryMan deliveryMan) {
        return deliveryManRepository.save(deliveryMan);
    }

    @Override
    public DeliveryMan updateDeliveryMan(DeliveryMan deliveryMan) {
        return deliveryManRepository.save(deliveryMan);
    }

    @Override
    public boolean deleteDeliveryMan(String strId) {
        try {
            UUID uuid = UUID.fromString(strId);
            deliveryManRepository.deleteById(uuid);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public DeliveryMan findDeliveryMan(String strId) {
        try {
            UUID uuid = UUID.fromString(strId);
            return deliveryManRepository.findById(uuid).orElseThrow();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DeliveryMan> listAllDeliveryMans() {
        return deliveryManRepository.findAll();
    }

}
