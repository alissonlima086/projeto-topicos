package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO (
    @NotBlank(message = "O nome não pode ficar em branco")
    String name,
    @NotBlank(message = "O CEP não pode ficar em branco")
    String postalCode,
    @NotBlank(message = "O endereço não pode ficar em branco")
    String address,
    String complement,
    @NotBlank(message = "A cidade não pode ser nula")
    Long city
){

    
}
