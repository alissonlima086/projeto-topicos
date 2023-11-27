package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;

public interface UserService {

    //User
    public UserResponseDTO insert(UserDTO dto);

    public UserResponseDTO update(Long id, UserDTO dto);

    public UserResponseDTO updateImageName(Long id, String imageName);

    public void delete(long id);

    //Phone
    public PhoneResponseDTO insertPhone(Long id, PhoneDTO dto);

    public PhoneResponseDTO updatePhone(Long id, PhoneDTO dto);

    public List<PhoneResponseDTO> findAllPhones();

    public List<PhoneResponseDTO> findPhoneByUserId(Long id);


    //Find
    public UserResponseDTO findById(long id);

    public List<UserResponseDTO> findByUsername(String username);

    public UserResponseDTO findByEmail(String email);

    public UserResponseDTO findByEmailAndPassword(String email, String Password);

    public List<UserResponseDTO> findAll();
    
}
