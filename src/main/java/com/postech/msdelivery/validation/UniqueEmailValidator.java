package com.postech.msdelivery.validation;

import com.postech.msdelivery.repository.DriverRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final DriverRepository driverRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return driverRepository.findByEmail(email) == null;
    }
}

