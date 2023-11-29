package br.unitins.topicos1.dto;

import java.util.HashMap;
import java.util.Map;

import br.unitins.topicos1.model.ItemOrder;

public record ItemOrderResponseDTO(
    Long id,
    int quantity,
    double totalItem,
    Map<String, Object> product,
    Map<String, Object> user
) {
    public ItemOrderResponseDTO(ItemOrder item) {
        this(item.getId(),item.getQuantity(),item.getTotalItem(),viewProduto(item.getProduct().getName()),viewUsuario(item.getUser().getEmail()));
    }

    public static Map<String, Object> viewUsuario(String name) {

        Map<String, Object> user = new HashMap<>();

        user.put("loginUsu", name);

        return user;
    }

    public static Map<String, Object> viewProduto(String name) {

        Map<String, Object> product = new HashMap<>();

        product.put("nome", name);

        return product;
    }
}
