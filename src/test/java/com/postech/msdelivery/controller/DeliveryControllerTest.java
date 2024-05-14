package com.postech.msdelivery.controller;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.entity.DeliveryMan;
import com.postech.msdelivery.gateway.DeliveryGateway;
import com.postech.msdelivery.gateway.DeliveryManGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DeliveryControllerTest {

    @Mock
    private DeliveryGateway deliveryGateway;
    @Mock
    private DeliveryManGateway deliveryManGateway;
    @InjectMocks
    private DeliveryController deliveryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class CreatetDelivery {
        @Test
        void devePermitirRegistrarEntrega() throws Exception {
            DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
            Delivery delivery = new Delivery(deliveryDTO);

            DeliveryMan deliveryMan = new DeliveryMan();
            deliveryMan.setName("John Doe");
            when(deliveryManGateway.findDeliveryMan(any()) ).thenReturn(deliveryMan);

            when(deliveryGateway.findDelivery(any())).thenReturn(delivery);
            when(deliveryGateway.getCustomerId(any())).thenReturn(UUID.fromString("d295ee33-99c1-4214-9eaf-77e79cdc3e23"));
            when(deliveryGateway.updateDelivery(any())).thenReturn(delivery);
            ResponseEntity<?> response = deliveryController.createDelivery(deliveryDTO);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

        @Test
        void devePermitirAtualizarEntrega() throws Exception {
            DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
            Delivery delivery = new Delivery(deliveryDTO);
            DeliveryMan deliveryMan = new DeliveryMan();
            deliveryMan.setId(delivery.getIdDeliveryMan());

            when(deliveryGateway.findDelivery(any())).thenReturn(delivery);
            when(deliveryGateway.getCustomerId(any())).thenReturn(UUID.fromString("d295ee33-99c1-4214-9eaf-77e79cdc3e23"));
            when(deliveryManGateway.findDeliveryMan(any())).thenReturn(deliveryMan);
            when(deliveryGateway.updateDelivery(any())).thenReturn(delivery);
            ResponseEntity<?> response = deliveryController.updateDelivery(deliveryDTO);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

        @Test
        void deveGerarExcecaoQuandoRegistrarEntregaNomeNulo() throws Exception {
            DeliveryDTO deliveryDTO = new DeliveryDTO();
            ResponseEntity<?> response = deliveryController.createDelivery(deliveryDTO);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        void deveGerarExcecaoQuandoRegistrarEntregaInvalida() throws Exception {
            DeliveryDTO deliveryDTO = new DeliveryDTO("b1fcbe89-fc7d-4e34-98c1-093e511cfa13", "4fa3cbe9-1575-448b-89d1-e2d2667c818b");
            Delivery delivery = new Delivery(deliveryDTO);
            when(deliveryGateway.createDelivery(any())).thenReturn(delivery);
            ResponseEntity<?> response = deliveryController.createDelivery(deliveryDTO);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }

    @Nested
    class ReadDelivery {
        @Test
        void devePermitirPesquisarUmEntrega() throws Exception {
            String deliveryId = "123";
            Delivery delivery = new Delivery();
            when(deliveryGateway.findDelivery(deliveryId)).thenReturn(delivery);
            ResponseEntity<?> response = deliveryController.findDelivery(deliveryId);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(delivery, response.getBody());
        }

        @Test
        void devePermitirListarTodosEntregas() throws Exception {
            List<Delivery> delivery = new ArrayList<>();
            delivery.add(new Delivery());
            delivery.add(new Delivery());

            when(deliveryGateway.listAllDeliverys()).thenReturn(delivery);
            ResponseEntity<List<Delivery>> response = deliveryController.listAllDeliverys();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(delivery, response.getBody());
        }

        @Test
        void devePermitirListarTodosEntregasPorEntregador() throws Exception {
            String deliveryId = "123";
            List<Delivery> delivery = new ArrayList<>();
            delivery.add(new Delivery());
            delivery.add(new Delivery());
            when(deliveryGateway.findDeliverysByIdDeliveryMan(deliveryId)).thenReturn(delivery);
            ResponseEntity<List<Delivery>> response = deliveryController.listAllDeliverysBestRoute(deliveryId);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(delivery, response.getBody());
        }

        @Test
        void deveGerarExcecaoSeNaoEncontrarEntrega() throws Exception {
            // Arrange
            String invalidId = "999";
            when(deliveryGateway.findDelivery(invalidId)).thenReturn(null);
            ResponseEntity<?> response = deliveryController.findDelivery(invalidId);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Entrega não encontrado.", response.getBody());
        }
    }
}

