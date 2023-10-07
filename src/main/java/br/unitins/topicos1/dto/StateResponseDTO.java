package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.State;

public record StateResponseDTO(
    Long id,
    String name,
    String abbreviation
) {
    public StateResponseDTO valueOf(State state){
        return new StateResponseDTO(state.getId(), state.getname(), state.getabbreviation());
    }
    
}
