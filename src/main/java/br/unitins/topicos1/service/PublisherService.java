package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.PublisherDTO;
import br.unitins.topicos1.dto.PublisherResponseDTO;

public interface PublisherService {
    public PublisherResponseDTO insert(PublisherDTO dto);

    public PublisherResponseDTO update(Long id, PublisherDTO dto);

    public void delete(Long id);

    public PublisherResponseDTO findById(Long id);

    public List<PublisherResponseDTO> findByName(String name);

    public List<PublisherResponseDTO> findAll();
}