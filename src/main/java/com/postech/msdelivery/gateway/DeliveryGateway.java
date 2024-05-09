package com.postech.msdelivery.gateway;

import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.interfaces.IDeliveryGateway;
import com.postech.msdelivery.repository.DeliveryRepository;
import com.postech.msdelivery.usecase.DeliveryUseCase;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
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
        List<Delivery> deliveryList = deliveryRepository.findAll();
        deliveryList = updateCep(deliveryList);
        return deliveryList;
    }

    public List<Delivery> findDeliverysByIdDeliveryMan(String idDeliveryMan){
        List<Delivery> deliveryList = deliveryRepository.findDeliverysByIdDeliveryMan(UUID.fromString(idDeliveryMan));
        deliveryList = updateCep(deliveryList);
        Collections.sort(deliveryList,Comparator.comparing(Delivery::getCepCustomer));
        return deliveryList;
    }

    private List<Delivery> updateCep(List<Delivery> deliveryList){
        for (Delivery delivery : deliveryList) {
            String cep = getCepCustomer(delivery.getIdOrder());
            delivery.setCepCustomer(cep);
        }
        return deliveryList;
    }
    private String getCepCustomer(UUID idOrder) {
        UUID idCustomer = DeliveryUseCase.getCustomerIdFromOrder(idOrder);
        return DeliveryUseCase.getCepCustomer(idCustomer);
    }

}
