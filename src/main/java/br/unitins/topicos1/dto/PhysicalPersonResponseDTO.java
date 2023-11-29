package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.PhysicalPerson;
import br.unitins.topicos1.model.Gender;

public record PhysicalPersonResponseDTO(
        Long id,
        String name,
        String cpf,
        Gender gender) {
    public PhysicalPersonResponseDTO(PhysicalPerson physicalPerson) {
        this(physicalPerson.getId(),physicalPerson.getName(), physicalPerson.getCpf(), physicalPerson.getGender());
    }
}
