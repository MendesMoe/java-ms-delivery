package com.postech.msdelivery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.msdelivery.client.GoogleDirectionsClient;
import com.postech.msdelivery.client.Order;
import com.postech.msdelivery.client.OrderClient;
import com.postech.msdelivery.client.Route;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryStatus;
import com.postech.msdelivery.exception.DeliveryNotFoundException;
import com.postech.msdelivery.interfaces.IDeliveryService;
import com.postech.msdelivery.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DeliveryService implements IDeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderClient orderClient;
    private final GoogleDirectionsClient directionsClient;

    @Value("${origin.address}")
    private String ORIGIN_ADDRESS;
    static RestTemplate restTemplate = new RestTemplate();

    public DeliveryService(DeliveryRepository deliveryRepository, OrderClient orderClient, GoogleDirectionsClient directionsClient) {
        this.deliveryRepository = deliveryRepository;
        this.orderClient = orderClient;
        this.directionsClient = directionsClient;
    }

    @Override
    public Delivery createDelivery(Long orderId) {
        Order order = orderClient.getOrderById(orderId);
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setStatus(DeliveryStatus.PLACED);
        delivery.setCustomerAddress(order.getCustomerAddress());

        // Calculate route
        Route route = directionsClient.calculateRoute(ORIGIN_ADDRESS,order.getCustomerAddress());
        //delivery.setRoute(route);

        // Estimate delivery time based on route duration
        delivery.setEstimatedDeliveryTime(LocalDateTime.now().plusSeconds(route.getDurationInSeconds()));

        return deliveryRepository.save(delivery);
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
    public boolean deleteDelivery(Long id) {
        try {
            deliveryRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Delivery findDelivery(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> new DeliveryNotFoundException(deliveryId));
    }

    @Override
    public List<Delivery> listAllDeliverys() {
        List<Delivery> deliveryList = deliveryRepository.findAll();
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
