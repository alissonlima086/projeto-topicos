package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.AuthorDTO;
import br.unitins.topicos1.dto.AuthorResponseDTO;

public interface AuthorService {
    public AuthorResponseDTO insert(AuthorDTO dto);

    public AuthorResponseDTO update(Long id, AuthorDTO dto);

    public void delete(long id);

    public AuthorResponseDTO findById(long id);

    public List<AuthorResponseDTO> findByName(String name);

    public List<AuthorResponseDTO> findAll();
    
}
