package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.User;

public record UserResponseDTO (
    Long id,
    String username,
    String email
){
    public static UserResponseDTO valueOf(User user){
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }
    
}
