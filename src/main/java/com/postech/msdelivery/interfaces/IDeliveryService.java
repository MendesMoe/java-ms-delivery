package com.postech.msdelivery.interfaces;

import com.postech.msdelivery.entity.Delivery;

import java.util.List;

public interface IDeliveryService {
    Delivery createDelivery(Long orderId);

    public Delivery createDelivery(Delivery delivery);

    public Delivery updateDelivery(Delivery delivery);

    public boolean deleteDelivery(Long id);

    public Delivery findDelivery(Long id);

    public List<Delivery> listAllDeliverys();

}