package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.AddressDTO;
import br.unitins.topicos1.dto.AddressResponseDTO;

public interface AddressService {

    public AddressResponseDTO insert(AddressDTO dto);

    public AddressResponseDTO update(Long id, AddressDTO dto);

    public void delete(Long id);

    public List<AddressResponseDTO> findAll();
    
}
