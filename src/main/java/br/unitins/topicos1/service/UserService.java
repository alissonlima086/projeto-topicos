package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.CompleteUserDTO;
import br.unitins.topicos1.dto.CompleteUserResponseDTO;
import br.unitins.topicos1.dto.EmailDTO;
import br.unitins.topicos1.dto.PhoneDTO;
import br.unitins.topicos1.dto.PhoneResponseDTO;
import br.unitins.topicos1.dto.UpdatePasswordDTO;
import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.dto.UsernameDTO;

public interface UserService {

    //User
    public UserResponseDTO insert(UserDTO dto);

    public UserResponseDTO update(Long id, UserDTO dto);

    public UserResponseDTO updateImageName(Long id, String imageName);

    public void delete(long id);

    //Complete User
    public UsernameDTO insertUsername(Long id, UsernameDTO usernameDTO);

    public List<CompleteUserResponseDTO> findAllCompleteUsers();

    public CompleteUserResponseDTO findCompleteUserByEmail(String email);

    public CompleteUserResponseDTO completeUser(Long id, CompleteUserDTO dto);

    //Phone
    public PhoneResponseDTO insertPhone(Long id, PhoneDTO dto);

    public PhoneResponseDTO updatePhone(Long id, PhoneDTO dto);

    public void deletePhone(Long id);

    public List<PhoneResponseDTO> findAllPhones();

    public List<PhoneResponseDTO> findPhoneByUserId(Long id);


    //Find
    public UserResponseDTO findById(long id);

    public UserResponseDTO findByUsername(String username);

    public UserResponseDTO findByEmail(String email);

    public UserResponseDTO findByEmailAndPassword(String email, String Password);

    public List<UserResponseDTO> findAll();
    
    //Updates

    public UserResponseDTO updateEmail(String login, EmailDTO newEmail);

    public UserResponseDTO updatePassword(String login, UpdatePasswordDTO updatePassword);

    public UserResponseDTO updateUsername(String login, UsernameDTO newUsername);
}
