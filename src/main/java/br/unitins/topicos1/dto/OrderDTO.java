package br.unitins.topicos1.dto;


import java.util.List;

import br.unitins.topicos1.model.Payment;

public record OrderDTO (
    // Payment payment,
    // AddressDTO address,
    List<ItemOrderDTO> itens
) {

}
