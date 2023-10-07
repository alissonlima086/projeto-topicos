package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Publisher;

public record PublisherResponseDTO (
    Long id,
    String name
){
    public static PublisherResponseDTO valueOf (Publisher Publisher){
        return new PublisherResponseDTO(Publisher.getId(), Publisher.getName());
    }
}