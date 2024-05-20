package com.postech.msdelivery.interfaces;

import com.postech.msdelivery.entity.Delivery;

import java.util.List;

public interface IDeliveryService {
    Delivery createDelivery(String orderUuid);

    public Delivery createDelivery(Delivery delivery);

    public Delivery updateDelivery(Delivery delivery);

    public boolean deleteDelivery(Long id);

    public Delivery findDelivery(Long id);

    public List<Delivery> listAllDeliverys();

    Delivery getDeliveryById(Long id);
}
