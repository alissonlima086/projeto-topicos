package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Author;
import br.unitins.topicos1.model.Binding;
import br.unitins.topicos1.model.Comic;
import br.unitins.topicos1.model.Publisher;

public record ComicResponseDTO (
    String name,
    Double price,
    Integer inventory,
    Integer numPages,
    Binding binding,
    Publisher publisher,
    Author author
){
    public static ComicResponseDTO valueOf(Comic comic){
        return new ComicResponseDTO(comic.getName(), comic.getPrice(), comic.getInventory(), comic.getNumPages(), comic.getBinding(), comic.getPublisher(), comic.getAuthor());
    }
    
}
