package com.postech.msdelivery.controller;

import com.postech.msdelivery.dto.DriverDTO;
import com.postech.msdelivery.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("")
    @Operation(summary = "Request for create a Driver", responses = {
            @ApiResponse(description = "The Driver was created", responseCode = "200")
    })
    public ResponseEntity<DriverDTO> createDriver(@Valid @RequestBody DriverDTO driver) {
        DriverDTO createdDriver = driverService.createDriver(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDriver);
    }
}
