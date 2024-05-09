package com.postech.msdelivery.interfaces;

import com.postech.msdelivery.entity.DeliveryMan;

import java.util.List;

public interface IDeliveryManGateway {
    public DeliveryMan createDeliveryMan(DeliveryMan deliveryMan);

    public DeliveryMan updateDeliveryMan(DeliveryMan deliveryMan);

    public boolean deleteDeliveryMan(String strId);

    public DeliveryMan findDeliveryMan(String strId);

    public List<DeliveryMan> listAllDeliveryMans();

}
