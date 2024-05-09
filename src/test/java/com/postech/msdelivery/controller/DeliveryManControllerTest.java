package com.postech.msdelivery.controller;

import com.postech.msdelivery.dto.DeliveryManDTO;
import com.postech.msdelivery.entity.DeliveryMan;
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

class DeliveryManControllerTest {
    

    @Mock
    private DeliveryManGateway deliveryManGateway;

    @InjectMocks
    private DeliveryManController DeliveryManController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class CreatetDeliveryMan {
        @Test
        void devePermitirRegistrarCliente() throws Exception {
            // Arrange
            DeliveryManDTO deliveryManDTO = new DeliveryManDTO();
            deliveryManDTO.setName("John Doe");

            when(deliveryManGateway.createDeliveryMan(any())).thenReturn(new DeliveryMan());

            // Act
            ResponseEntity<?> response = DeliveryManController.createDeliveryMan(deliveryManDTO);

            // Assert
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

        @Test
        void deveGerarExcecaoQuandoRegistrarClienteNomeNulo() throws Exception {
            // Arrange
            DeliveryManDTO DeliveryManDTO = new DeliveryManDTO();

            // Act
            ResponseEntity<?> response = DeliveryManController.createDeliveryMan(DeliveryManDTO);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }

    @Nested
    class ReadDeliveryMan {
        @Test
        void devePermitirPesquisarUmCliente() throws Exception {
            // Arrange
            String DeliveryManId = "123";
            DeliveryMan DeliveryMan = new DeliveryMan();
            when(deliveryManGateway.findDeliveryMan(DeliveryManId)).thenReturn(DeliveryMan);

            // Act
            ResponseEntity<?> response = DeliveryManController.findDeliveryMan(DeliveryManId);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(DeliveryMan, response.getBody());
        }

        @Test
        void devePermitirListarTodosClientes() throws Exception {
            // Arrange
            List<DeliveryMan> DeliveryMans = new ArrayList<>();
            DeliveryMans.add(new DeliveryMan());
            DeliveryMans.add(new DeliveryMan());

            when(deliveryManGateway.listAllDeliveryMans()).thenReturn(DeliveryMans);

            // Act
            ResponseEntity<List<DeliveryMan>> response = DeliveryManController.listAllDeliveryMans();

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(DeliveryMans, response.getBody());
        }

        @Test
        void deveGerarExcecaoSeNaoEncontrarCliente() throws Exception {
            // Arrange
            String invalidId = "999";
            when(deliveryManGateway.findDeliveryMan(invalidId)).thenReturn(null);

            // Act
            ResponseEntity<?> response = DeliveryManController.findDeliveryMan(invalidId);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Cliente não encontrado.", response.getBody());
        }
    }

    @Nested
    class UpdateDeliveryMan {
        @Test
        void devePermitirAtualizarCliente() throws Exception {
            // Arrange
            UUID DeliveryManId = UUID.randomUUID();
            DeliveryManDTO DeliveryManDTO = new DeliveryManDTO();
            DeliveryManDTO.setName("John Doe");
            // Mock the behavior of DeliveryManGateway
            DeliveryMan DeliveryManOld = new DeliveryMan();
            DeliveryManOld.setId(DeliveryManId);
            DeliveryMan DeliveryManNew = new DeliveryMan(DeliveryManDTO);
            when(deliveryManGateway.findDeliveryMan(String.valueOf(DeliveryManId))).thenReturn(DeliveryManOld);
            when(deliveryManGateway.updateDeliveryMan(DeliveryManNew)).thenReturn(DeliveryManNew);

            // Act
            ResponseEntity<?> response = DeliveryManController.updateDeliveryMan(String.valueOf(DeliveryManId), DeliveryManDTO);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void deveGerarExcecaoQuandoAtualizarClienteNomeNulo() throws Exception {

        }

        @Test
        void deveGerarExcecaoQuandoAtualizarClienteNãoEncontrado() throws Exception {
            // Arrange
            String invalidId = "999";
            DeliveryManDTO DeliveryManDTO = new DeliveryManDTO();

            // Act
            ResponseEntity<?> response = DeliveryManController.updateDeliveryMan(invalidId, DeliveryManDTO);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }

    @Nested
    class DeleteDeliveryMan {
        @Test
        void devePermitirApagarCliente() throws Exception {
            // Arrange
            String DeliveryManId = UUID.randomUUID().toString();
            DeliveryMan DeliveryMan = new DeliveryMan();
            // Mock the behavior of DeliveryManGateway
            when(deliveryManGateway.findDeliveryMan(DeliveryManId)).thenReturn(DeliveryMan);

            // Act
            ResponseEntity<?> response = DeliveryManController.deleteDeliveryMan(DeliveryManId);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Cliente removido.", response.getBody());
        }

        @Test
        void deveGerarExcecaoQuandoDeletarClienteNãoEncontrado() throws Exception {
            // Arrange
            String DeliveryManId = UUID.randomUUID().toString();
            // Mock the behavior of DeliveryManGateway
            when(deliveryManGateway.findDeliveryMan(DeliveryManId)).thenReturn(null);

            // Act
            ResponseEntity<?> response = DeliveryManController.deleteDeliveryMan(DeliveryManId);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Cliente não encontrado.", response.getBody());
        }
    }
}

