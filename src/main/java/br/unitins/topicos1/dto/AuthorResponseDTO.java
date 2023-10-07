package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Author;

public record AuthorResponseDTO (
    Long id,
    String Authorname,
    String email
){
    public static AuthorResponseDTO valueOf(Author Author){
        return new AuthorResponseDTO(Author.getId(), Author.getAuthorName(), Author.getEmail());
    }
    
}
