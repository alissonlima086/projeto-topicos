package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;

public interface UserService {
    public UserResponseDTO insert(UserDTO dto);

    public UserResponseDTO update(Long id, UserDTO dto);

    public void delete(long id);

    public PhoneResponseDTO insertPhone(Long id, PhoneDTO dto);

    public PhoneResponseDTO updatePhone(Long id, PhoneDTO dto);

    public List<PhoneResponseDTO> findAllPhones();

    public UserResponseDTO findById(long id);

    public List<UserResponseDTO> findByUsername(String username);

    public UserResponseDTO findByEmailAndPassword(String email, String Password);

    public List<UserResponseDTO> findAll();
    
}
