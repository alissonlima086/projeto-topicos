package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.User;

public record UsernameDTO(
    String username
) {
    public static UsernameDTO valueOf(User user) {
        return new UsernameDTO(user.getUsername());
    }
}
