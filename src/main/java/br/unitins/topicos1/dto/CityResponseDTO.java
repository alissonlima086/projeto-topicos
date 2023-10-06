package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.City;

public record CityResponseDTO (
    Long id,
    String name,
    String state
){
    public static CityResponseDTO valueOf(City city){
        return new CityResponseDTO(city.getId(), city.getName(), city.getState().getname());
    }
    
}
