package com.postech.msdelivery.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.interfaces.IDeliveryGateway;
import com.postech.msdelivery.repository.DeliveryRepository;
import com.postech.msdelivery.usecase.DeliveryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class DeliveryGateway implements IDeliveryGateway {
    private final DeliveryRepository deliveryRepository;
    static RestTemplate restTemplate = new RestTemplate();

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

    public List<Delivery> updateCep(List<Delivery> deliveryList){
        for (Delivery delivery : deliveryList) {
            String cep = getCepCustomer(delivery.getIdOrder());
            delivery.setCepCustomer(cep);
        }
        return deliveryList;
    }

    public UUID getCustomerIdFromOrder(UUID idOrder) {
        try {
            String url = "http://localhost:8082/orders/" + idOrder;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(response.getBody(), Map.class);
            return UUID.fromString(map.get("idCustomer").toString());
        } catch (Exception e) {
            return null;
        }
    }
    public String getCepCustomer(UUID idCustomer) {
        try {
            String url = "http://localhost:8081/customers/" + idCustomer;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(response.getBody(), Map.class);
            return map.get("cep").toString();
        } catch (Exception e) {
            return "";
        }
    }
}
