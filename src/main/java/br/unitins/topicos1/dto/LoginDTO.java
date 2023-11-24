package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginDTO (
    @NotEmpty(message = "O campo nome não pode ser nulo.")
    String email,
    @NotEmpty(message = "O campo senha não pode ser nulo.")
    String password
) {

}