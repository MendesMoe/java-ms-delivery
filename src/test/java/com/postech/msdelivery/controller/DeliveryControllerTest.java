package com.postech.msdelivery.controller;

import com.postech.msdelivery.dto.DeliveryDTO;
import com.postech.msdelivery.entity.Delivery;
import com.postech.msdelivery.gateway.DeliveryGateway;
import com.postech.msdelivery.usecase.DeliveryUseCase;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DeliveryControllerTest {

    @Mock
    private DeliveryGateway deliveryGateway;

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
            String idOrder = DeliveryUseCase.findOneOrder();
            DeliveryDTO deliveryDTO = new DeliveryDTO(idOrder);
            when(deliveryGateway.createDelivery(any())).thenReturn(new Delivery());
            ResponseEntity<?> response = deliveryController.createDelivery(deliveryDTO);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }
        @Test
        void devePermitirAtualizarEntrega() throws Exception {
            String idOrder = DeliveryUseCase.findOneOrder();
            DeliveryDTO deliveryDTO = new DeliveryDTO(idOrder);
            when(deliveryGateway.updateDelivery(any())).thenReturn(new Delivery());
            when(deliveryGateway.findDelivery(any())).thenReturn(new Delivery(deliveryDTO));
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
        void deveGerarExcecaoQuandoRegistrarEntregaCOmpraInvalida() throws Exception {
            String idOrder = "a795dbef-c772-4df6-be7a-732d4167a7f0";
            DeliveryDTO deliveryDTO = new DeliveryDTO(idOrder);
            when(deliveryGateway.createDelivery(any())).thenReturn(new Delivery());
            ResponseEntity<?> response = deliveryController.createDelivery(deliveryDTO);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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

