package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotBlank;

public record PhoneDTO (
    @NotBlank(message = "O codigo de area não pode estar nulo")
    String areaCode,

    @NotBlank(message = "O numero não pode estar nulo")
    String number
){

    
}
