package com.postech.msdelivery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-order", url="${ms-order.url}")
public interface OrderClient {
    @GetMapping("/orders/{orderId}")
    Order getOrderById(@PathVariable Long orderId);
}

