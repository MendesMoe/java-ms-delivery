package com.postech.msdelivery.client;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Customer {
    private UUID id;
    private String nome;
    private String cpf;
    private String email;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
}

