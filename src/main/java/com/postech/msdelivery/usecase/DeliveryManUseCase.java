package com.postech.msdelivery.usecase;

import com.postech.msdelivery.entity.DeliveryMan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryManUseCase {

    public static void validarInsertDeliveryMan(DeliveryMan deliveryManNew) {
        if (deliveryManNew.getName() == null) {
            throw new IllegalArgumentException("O nome não pode ser nulo.");
        }
    }

    public static void validarUpdateEntregador(String strId, DeliveryMan deliveryManToUpdate, DeliveryMan deliveryManNew) {

        if (deliveryManToUpdate == null) {
            throw new IllegalArgumentException("Entregador não encontrado.");
        }
    }

    public static void validarDeleteEntregador(DeliveryMan deliveryMan) {

        if (deliveryMan == null) {
            throw new IllegalArgumentException("Entregador não encontrado.");
        }
    }
}
