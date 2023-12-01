package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComicDTO (
    
    @NotBlank(message = "O nome não pode ser nulo")
    String name,
    @NotNull(message = "O preço não pode ser nulo")
    Double price,
    @NotNull(message = "A quantidade em estoque não pode estar nula")
    Integer inventory,
    Integer numPages,
    @NotNull(message = "A encadernação não pode ser nula")
    Integer binding,
    Long publisher,
    Long author
){

    
}
