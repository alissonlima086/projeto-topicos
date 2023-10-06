package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.CityResponseDTO;

public interface CityService {

    public CityResponseDTO findById(Long id);

    public List<CityResponseDTO> findByName(String name);

    public List<CityResponseDTO> findAll();
    
}
