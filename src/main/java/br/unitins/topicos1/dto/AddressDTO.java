package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.City;

public record AddressDTO (
    String name,
    String postalCode,
    String address,
    String complement,
    Long city,
    Long user
){

    
}
