package com.postech.msdelivery.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.interfaces.IDeliveryGateway;
import com.postech.msdelivery.repository.DeliveryRepository;
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
        return deliveryList;
    }

    public List<Delivery> findDeliverysByIdDeliveryMan(String idDeliveryMan) {
        List<Delivery> deliveryList = deliveryRepository.findDeliverysByIdDeliveryMan(UUID.fromString(idDeliveryMan));
        Collections.sort(deliveryList, Comparator.comparing(Delivery::getCepCustomer));
        return deliveryList;
    }

    public static UUID getCustomerIdFromOrder(UUID idOrder) {
        String url = "http://localhost:8082/orders/" + idOrder;
        return UUID.fromString(FieldFromMap(url, "idCustomer"));
    }

    public UUID getCustomerId(UUID idOrder) {
        String url = "http://localhost:8082/orders/" + idOrder;
        return UUID.fromString(FieldFromMap(url, "cep"));
    }

    public static String getCepCustomer(UUID idCustomer) {
        String url = "http://localhost:8081/customers/" + idCustomer;
        return FieldFromMap(url, "cep");
    }

    private static String FieldFromMap(String url, String field) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.readValue(response.getBody(), Map.class);
            return map.get(field).toString();
        } catch (Exception e) {
            return "";
        }
    }
}
