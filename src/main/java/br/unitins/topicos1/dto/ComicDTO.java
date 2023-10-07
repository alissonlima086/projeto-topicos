package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Binding;

public record ComicDTO (
    String name,
    Double price,
    Integer inventory,
    Integer numPages,
    Integer binding,
    Long publisher,
    Long author
){

    
}
