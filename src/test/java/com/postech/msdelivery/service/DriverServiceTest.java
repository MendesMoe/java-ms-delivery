package com.postech.msdelivery.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.postech.msdelivery.dto.DriverDTO;
import com.postech.msdelivery.entity.Driver;
import com.postech.msdelivery.repository.DriverRepository;
import com.postech.msdelivery.usecase.mapper.DriverMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    private Logger logger;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    public void setup() {
        logger = (Logger) LoggerFactory.getLogger(DriverService.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    public void testCreateDriver_Success() {
        DriverDTO driverDTO = new DriverDTO("Jose Silva", "josesilva@example.com");
        Driver driver = DriverMapping.INSTANCE.toDriver(driverDTO);
        driver.setAvailable(true);

        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        DriverDTO createdDriverDTO = driverService.createDriver(driverDTO);

        assertNotNull(createdDriverDTO);
        assertEquals(driverDTO.getName(), createdDriverDTO.getName());
        assertEquals(driverDTO.getEmail(), createdDriverDTO.getEmail());
    }

    @Test
    public void testCreateDriver_AlreadyExists() {
        /*DriverDTO driverDTO = new DriverDTO("Jose Silva", "josesilva@example.com");
        Driver existingDriver = DriverMapping.INSTANCE.toDriver(driverDTO);

        when(driverRepository.findByEmail(driverDTO.getEmail())).thenReturn(existingDriver);

        driverService.createDriver(driverDTO);

        //assertThrows(Exception.class, () -> driverService.createDriver(driverDTO));

        assertEquals(1, listAppender.list.size());
        assertEquals("Error creating driver josesilva@example.com Driver already exists with name: Jose Silva", listAppender.list.get(0).getMessage());
    */
    }

}

