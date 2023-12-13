package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.ComicDTO;
import br.unitins.topicos1.dto.ComicResponseDTO;

public interface ComicService {

    public ComicResponseDTO insert(ComicDTO dto);

    public ComicResponseDTO update(Long id, ComicDTO dto);

    public void delete(Long id);

    public List<ComicResponseDTO> findAll();

    public List<ComicResponseDTO> findByName(String name);

    public ComicResponseDTO findById(Long id);
    
}