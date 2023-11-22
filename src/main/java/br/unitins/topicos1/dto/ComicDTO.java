package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ComicDTO (
    
    @NotBlank
    @Size(min = 2, max = 30, message = "Comic name must between 2 and 30 characters") 
    String name,
    Double price,
    Integer inventory,
    Integer numPages,
    Integer binding,
    Long publisher,
    Long author
){

    
}
