package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.AddressDTO;
import br.unitins.topicos1.dto.AddressResponseDTO;

public interface AddressService {

    public AddressResponseDTO insert(Long id, AddressDTO dto);

    public AddressResponseDTO update(Long idAddress, Long idUser, AddressDTO dto);

    public void delete(Long idAddress, Long idUser);

    public List<AddressResponseDTO> findAll(Long id);

    public List<AddressResponseDTO> findByUserAndCity(Long user, Long city);
    
}
