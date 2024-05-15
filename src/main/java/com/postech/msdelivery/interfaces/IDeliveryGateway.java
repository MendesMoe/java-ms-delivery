package com.postech.msdelivery.interfaces;

import com.postech.msdelivery.entity.Delivery;

import java.util.List;

public interface IDeliveryGateway {
    public Delivery createDelivery(Delivery delivery);

    public Delivery updateDelivery(Delivery delivery);

    public boolean deleteDelivery(Long id);

    public Delivery findDelivery(Long id);

    public List<Delivery> listAllDeliverys();

    public List<Delivery> findDeliverysByIdDeliveryMan(String idDeliveryMan);
}
