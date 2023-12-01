package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDTO (
    @Size(min = 2, max = 30, message = "Username must between 2 and 30 characters") 
    String username,
    @NotBlank(message = "O e-mail não pode estar em branco")
    String email,
    @NotBlank(message = "A senha não pode estar em branco")
    String password,
    @NotNull(message = "O perfil não pode ser nulo")
    Integer profile
){
    
}
