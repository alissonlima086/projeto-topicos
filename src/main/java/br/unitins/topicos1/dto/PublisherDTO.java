package br.unitins.topicos1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublisherDTO (
    @NotBlank
    @Size(min = 2, max = 30, message = "Publisher name must between 2 and 30 characters") 
    String name
){
    
}