package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.ItemOrderDTO;
import br.unitins.topicos1.dto.ItemOrderResponseDTO;

public interface ItemOrderService {
    
    // recursos basicos
    List<ItemOrderResponseDTO> findAll();

    ItemOrderResponseDTO findById(Long id);

    ItemOrderResponseDTO create(Long idUser, ItemOrderDTO ItemOrderDto);

    void delete(Long idUser, Long idItemCart);

    long countItemOrder(Long id);

    public long count();

    public List<ItemOrderResponseDTO> findAllCart(Long idUser);

}
