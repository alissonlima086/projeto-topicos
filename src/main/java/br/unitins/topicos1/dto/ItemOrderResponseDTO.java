package br.unitins.topicos1.dto;

import java.util.List;

import br.unitins.topicos1.model.ItemOrder;

public record ItemOrderResponseDTO(
    Integer quantity,
    Double price,
    Long idProduct,
    String name
) { 
    public static ItemOrderResponseDTO valueOf(ItemOrder item){
        return new ItemOrderResponseDTO(
            item.getQuantity(), 
            item.getPrice(),
            item.getProduct().getId(),
            item.getProduct().getName());
    }

    public static List<ItemOrderResponseDTO> valueOf(List<ItemOrder> item) {
       return item.stream().map(i -> ItemOrderResponseDTO.valueOf(i)).toList();
    }

}