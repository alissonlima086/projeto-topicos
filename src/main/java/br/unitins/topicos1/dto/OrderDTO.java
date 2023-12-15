package br.unitins.topicos1.dto;

import java.util.List;

public record OrderDTO (
    // AddressDTO address,
    List<ItemOrderDTO> itens
) {

}
