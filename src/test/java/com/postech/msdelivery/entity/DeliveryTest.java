package com.postech.msdelivery.entity;


import com.postech.msdelivery.dto.DeliveryDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryTest {
    @Test
    void testValidateGetStatusDescription0() {
        DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
        Delivery delivery = new Delivery(deliveryDTO);
        delivery.setStatus(0);
        assertEquals(delivery.getStatusDescription().toString().equals("Aguardando Envio"), true);
    }

    @Test
    void testValidateGetStatusDescription1() {
        DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
        Delivery delivery = new Delivery(deliveryDTO);
        delivery.setStatus(1);
        assertEquals(delivery.getStatusDescription().equals("Em rota de envio"), true);
    }

    @Test
    void testValidateGetStatusDescription2() {
        DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
        Delivery delivery = new Delivery(deliveryDTO);

        delivery.setStatus(2);
        assertEquals(delivery.getStatusDescription().equals("A caminho"), true);
    }

    @Test
    void testValidateGetStatusDescription3() {
        DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
        Delivery delivery = new Delivery(deliveryDTO);
        delivery.setStatus(3);
        assertEquals(delivery.getStatusDescription().equals("Entregue"), true);

    }

    @Test
    void testValidateGetStatusDescription4() {
        DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
        Delivery delivery = new Delivery(deliveryDTO);
        delivery.setStatus(4);
        assertEquals(delivery.getStatusDescription().equals("Entrega Rejeitada"), true);
    }

    @Test
    void testValidateGetStatusDescription5() {
        DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
        Delivery delivery = new Delivery(deliveryDTO);
        delivery.setStatus(5);
        assertEquals(delivery.getStatusDescription().equals("Entrega Cancelada"), true);
    }

    @Test
    void testValidateGetStatusDescription6() {
        DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
        Delivery delivery = new Delivery(deliveryDTO);

        delivery.setStatus(6);
        assertEquals(delivery.getStatusDescription().equals("Status Inválido"), true);
    }

    @Test
    void testValidateGetCep() {
        DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
        Delivery delivery = new Delivery(deliveryDTO);

        delivery.setIdOrder(UUID.fromString("b1fcbe89-fc7d-4e34-98c1-093e511cfa13"));
        assertEquals(delivery.getCepCustomer().equals(""), true);
    }
    
}
