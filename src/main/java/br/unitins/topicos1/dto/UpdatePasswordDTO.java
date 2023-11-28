package br.unitins.topicos1.dto;

public record UpdatePasswordDTO(
    String currentPassword,
    String newPassword
) {
}
