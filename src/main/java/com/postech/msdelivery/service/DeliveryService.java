package com.postech.msdelivery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.msdelivery.client.*;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryStatus;
import com.postech.msdelivery.exception.DeliveryNotFoundException;
import com.postech.msdelivery.exception.ResourceNotFoundException;
import com.postech.msdelivery.interfaces.IDeliveryService;
import com.postech.msdelivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService implements IDeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderClient orderClient;
    private final CustomerClient customerClient;
    private final GoogleDirectionsClient directionsClient;

    @Value("${origin.address}")
    private String ORIGIN_ADDRESS;
    static RestTemplate restTemplate = new RestTemplate();


    @Override
    public Delivery createDelivery(String orderUuid) {
        try {
            if(ORIGIN_ADDRESS == null) ORIGIN_ADDRESS = "CEP 01001-001";
            Order order = orderClient.getOrderById(orderUuid);
            Customer customer = customerClient.getCustomer(order.getIdCustomer().toString());
            Delivery delivery = new Delivery();
            delivery.setOrderUuid(orderUuid);
            delivery.setStatus(DeliveryStatus.PLACED);

            String destination = parseCustomerAddress(customer);
            delivery.setCustomerAddress(destination);

            // Calculate route
            Route route = directionsClient.calculateRoute(ORIGIN_ADDRESS,destination);
            //delivery.setRoute(route);

            // Estimate delivery time based on route duration
            delivery.setEstimatedDeliveryTime(LocalDateTime.now().plusSeconds(route.getDurationInSeconds()));

            return deliveryRepository.save(delivery);
        } catch (Exception e){
            log.error("Error saving new delivery {}", e.getMessage());
            throw e;
        }

    }

    private String parseCustomerAddress(Customer customer) {
        try {
            String destination;
            StringBuilder stringBuilder = new StringBuilder();
            destination = stringBuilder
                    .append(customer.getEndereco())
                    .append(", ")
                    .append(customer.getCidade())
                    .append(", ")
                    .append("CEP ")
                    .append(customer.getCep())
                    .toString();
            return destination;
        } catch (Exception e){
          log.error("Error parsing customer address {}", customer.getId());
          throw e;
        }
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

    @Override
    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
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
