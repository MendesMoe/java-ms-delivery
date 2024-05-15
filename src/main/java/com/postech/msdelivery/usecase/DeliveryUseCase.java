package com.postech.msdelivery.usecase;

import com.postech.msdelivery.entity.Delivery;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@NoArgsConstructor
@Service
public class DeliveryUseCase {
    static RestTemplate restTemplate = new RestTemplate();

    public static void validateInsertDelivery(Delivery deliveryNew) {
        findOrder(String.valueOf(deliveryNew.getOrderId()));
    }

    public static boolean findOrder(String idOrder) {
        String url = "http://localhost:8082/orders/" + idOrder;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public static String findOneOrder() {
        String url = "http://localhost:8082/orders";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        int start = response.getBody().indexOf("id")+5;
        int end = response.getBody().indexOf("idCustomer")-3;
        return response.getBody().substring(start,end);
    }
}
