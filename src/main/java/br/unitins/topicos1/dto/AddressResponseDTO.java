package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Address;
import br.unitins.topicos1.model.City;
import br.unitins.topicos1.model.User;

public record AddressResponseDTO (
    String user,
    String name,
    String postalCode,
    String address,
    String complement,
    City city
){
    public static AddressResponseDTO valueOf(Address address){
        return new AddressResponseDTO(address.getUser().getEmail(), address.getName(), address.getPostalCode(), address.getAddress(), address.getComplement(), address.getCity());
    }
    
}
