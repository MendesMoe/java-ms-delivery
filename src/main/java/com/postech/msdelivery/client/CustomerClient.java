package com.postech.msdelivery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-customer", url="${ms-customer.url}")
public interface CustomerClient {
    @GetMapping("/customers/{customerUuid}")
    Customer getCustomer(@PathVariable String uuid);
}

