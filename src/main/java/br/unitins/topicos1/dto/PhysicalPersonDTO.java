package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotBlank;

public record PhysicalPersonDTO(

        @NotBlank(message = "O campo cnpj deve ser informado.")
        String name,

        @NotBlank(message = "O campo cnpj deve ser informado.")
        String cpf,

        Integer gender

) {

}
