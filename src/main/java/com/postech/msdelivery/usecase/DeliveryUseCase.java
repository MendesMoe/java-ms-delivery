package com.postech.msdelivery.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.msdelivery.entity.Delivery;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@NoArgsConstructor
@Service
public class DeliveryUseCase {
    static RestTemplate restTemplate = new RestTemplate();

    public static void validateInsertDelivery(Delivery deliveryNew) {
        getCustomerIdFromOrder(deliveryNew.getIdOrder());
    }

    public static UUID getCustomerIdFromOrder(UUID idOrder) {
        try {
            String url = "http://localhost:8082/orders/" + idOrder;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = null;
            map = mapper.readValue(response.getBody(), Map.class);
            return UUID.fromString(map.get("idCustomer").toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCepCustomer(UUID idCustomer) {
        try {
            String url = "http://localhost:8081/customers/" + idCustomer;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = null;
            map = mapper.readValue(response.getBody(), Map.class);
            return map.get("cep").toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String findOneOrder() {
        try {
            String url = "http://localhost:8082/orders";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = null;
            map = mapper.readValue(response.getBody(), Map.class);
            return map.get("id").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
