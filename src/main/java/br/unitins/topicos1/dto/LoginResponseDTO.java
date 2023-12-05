package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.User;

public record LoginResponseDTO (
    String email
) {
    public static LoginResponseDTO valueOf(User user){
        return new LoginResponseDTO(user.getEmail());
    }
}