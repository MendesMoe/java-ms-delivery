package com.postech.msdelivery.service;

import com.postech.msdelivery.dto.DriverDTO;
import com.postech.msdelivery.entity.Driver;
import com.postech.msdelivery.repository.DriverRepository;
import com.postech.msdelivery.usecase.mapper.DriverMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DriverService {

    private final DriverRepository driverRepository;


    public DriverDTO createDriver(DriverDTO driverDTO) {
        try {
            DriverMapping.INSTANCE.toDriver(driverDTO);
            Driver driver = new Driver(driverDTO.getName(), driverDTO.getEmail());
            driver.setAvailable(true);
            Driver savedDriver = driverRepository.save(driver);
            driverDTO = DriverMapping.INSTANCE.toDriverDTO(savedDriver);
            return driverDTO;
        } catch (Exception e){
            log.error("Error creating driver {} {}",driverDTO.getEmail(), e.getMessage());
            throw e;
        }

    }

}

