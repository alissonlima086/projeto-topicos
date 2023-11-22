package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.CityResponseDTO;
import br.unitins.topicos1.repository.CityRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CityServiceImpl implements CityService{

    @Inject
    CityRepository repository;

    @Override
    public CityResponseDTO findById(Long id) {
        return CityResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<CityResponseDTO> findByName(String name) {
        return repository.findByName(name).stream().map(e -> CityResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<CityResponseDTO> findAll() {
        return repository.listAll().stream().map(e -> CityResponseDTO.valueOf(e)).toList();
    }
}
