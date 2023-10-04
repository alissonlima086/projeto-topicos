package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;

public interface UserService {
    public UserResponseDTO insert(UserDTO dto);

    public UserResponseDTO update(Long id, UserDTO dto);

    public void delete(long id);

    public UserResponseDTO findById(long id);

    public List<UserResponseDTO> findByNome(String nome);

    public List<UserResponseDTO> findAll();
    
}
