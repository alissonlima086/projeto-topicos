package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Gender;
import br.unitins.topicos1.model.PhysicalPerson;
import br.unitins.topicos1.model.Profile;
import br.unitins.topicos1.model.User;

public record CompleteUserResponseDTO(
    Long id,
    String username,
    String email,
    PhysicalPerson person,
    Profile profile,
    String imageName

) {
    public static CompleteUserResponseDTO valueOf(User user){
        return new CompleteUserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPhysicalPerson(), user.getProfile(), user.getImageName());
    }
}