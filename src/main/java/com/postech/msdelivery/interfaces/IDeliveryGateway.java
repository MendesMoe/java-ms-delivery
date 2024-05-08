package com.postech.msdelivery.interfaces;

import com.postech.msdelivery.entity.Delivery;

import java.util.List;

public interface IDeliveryGateway {
    public Delivery createDelivery(Delivery delivery);

    public Delivery updateDelivery(Delivery delivery);

    public boolean deleteDelivery(String strId);

    public Delivery findDelivery(String strId);

    public List<Delivery> listAllDeliverys();
}
