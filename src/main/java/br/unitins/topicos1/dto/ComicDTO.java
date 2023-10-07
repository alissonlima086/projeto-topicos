package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Binding;

public record ComicDTO (
    Integer numPages,
    Binding binding,
    Long publisher,
    Long author
){

    
}
