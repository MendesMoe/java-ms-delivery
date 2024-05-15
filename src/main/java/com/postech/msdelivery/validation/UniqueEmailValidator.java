package com.postech.msdelivery.validation;

import com.postech.msdelivery.repository.DeliveryPersonRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final DeliveryPersonRepository deliveryPersonRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return deliveryPersonRepository.findByEmail(email) == null;
    }
}

