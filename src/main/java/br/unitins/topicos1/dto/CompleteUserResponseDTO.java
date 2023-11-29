package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Gender;
import br.unitins.topicos1.model.Profile;
import br.unitins.topicos1.model.User;

public record CompleteUserResponseDTO(
    Long id,
    String fullName,
    String username,
    String email,
    String cpf,
    Gender gender,
    Profile profile,
    String imageName

) {
    public static CompleteUserResponseDTO valueOf(User user){
        return new CompleteUserResponseDTO(user.getId(), user.getPhysicalPerson().getName(), user.getUsername(), user.getEmail(), user.getPhysicalPerson().getCpf(), user.getPhysicalPerson().getGender(), user.getProfile(), user.getImageName());
    }
}