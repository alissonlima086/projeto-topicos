package br.unitins.topicos1.service;

import br.unitins.topicos1.dto.UserResponseDTO;

public interface JwtService {

    public String generateJwt(UserResponseDTO dto);
    
}