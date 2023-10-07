package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Author;
import br.unitins.topicos1.model.Binding;
import br.unitins.topicos1.model.Comic;
import br.unitins.topicos1.model.Publisher;

public record ComicResponseDTO (
    Integer numPages,
    Binding binding,
    Publisher publisher,
    Author author
){
    public static ComicResponseDTO valueOf(Comic Comic){
        return new ComicResponseDTO(Comic.getNumPages(), Comic.getBinding(), Comic.getPublisher(), Comic.getAuthor());
    }
    
}
