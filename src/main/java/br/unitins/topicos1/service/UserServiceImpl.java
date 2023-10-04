package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.UserDTO;
import br.unitins.topicos1.dto.UserResponseDTO;
import br.unitins.topicos1.model.User;
import br.unitins.topicos1.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository repository;

    @Override
    @Transactional
    public UserResponseDTO insert(UserDTO dto) {
        User newUser = new User();

        newUser.setUsername(dto.username());
        newUser.setEmail(dto.email());
        newUser.setPassword(dto.password());

        repository.persistAndFlush(newUser);

        return UserResponseDTO.valueOf(newUser);
    }

    @Override
    @Transactional
    public UserResponseDTO update(Long id, UserDTO dto) {
        User user = repository.findById(id);
        user.setUsername(dto.username());
        user.setEmail(dto.email());

        return UserResponseDTO.valueOf(user);
    }

    @Override
    @Transactional
    public void delete(long id) {
        if(!repository.deleteById(id)){
            throw new NotFoundException();
        }
    }

    @Override
    public UserResponseDTO findById(long id) {
        return UserResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<UserResponseDTO> findByUsername(String username) {
        return repository.findByUsername(username).stream().map(e -> UserResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<UserResponseDTO> findAll() {
        return repository.listAll().stream().map(e -> UserResponseDTO.valueOf(e)).toList();
    }
    
}
