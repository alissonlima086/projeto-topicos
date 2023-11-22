package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorDTO (

    @NotBlank
    @Size(min = 2, max = 30, message = "Name must between 2 and 30 characters") 
    String name,
    String email
){
    
}
