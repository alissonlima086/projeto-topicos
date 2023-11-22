package br.unitins.topicos1.dto;

public record AddressDTO (
    String name,
    String postalCode,
    String address,
    String complement,
    Long city,
    Long user
){

    
}
