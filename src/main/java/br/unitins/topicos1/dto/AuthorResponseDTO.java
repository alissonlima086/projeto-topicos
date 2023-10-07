package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Author;

public record AuthorResponseDTO (
    Long id,
    String name,
    String email
){
    public static AuthorResponseDTO valueOf(Author Author){
        return new AuthorResponseDTO(Author.getId(), Author.getName(), Author.getEmail());
    }
    
}
