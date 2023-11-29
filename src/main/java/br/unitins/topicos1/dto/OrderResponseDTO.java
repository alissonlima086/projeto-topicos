package br.unitins.topicos1.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unitins.topicos1.model.Order;
import br.unitins.topicos1.model.Payment;
import br.unitins.topicos1.model.ItemOrder;

public record OrderResponseDTO(
    Long id,
    double totalOrder,
    List<Map<String, Object>> listOfItens,
    Payment payment
) {
    public OrderResponseDTO(Order order) {
        this(order.getId(),order.getTotalOrder(), viewProducts(order.getListOfItens()),order.getPayment());
    }

    public static Map<String, Object> findProduct(String name, double price, int quantity) {

        Map<String, Object> product = new HashMap<>();

        product.put("nome", name);
        product.put("preco", price);
        product.put("quantidade", quantity);

        return product;
    }

    private static List<Map<String, Object>> viewProducts(List<ItemOrder> list) {

        List<Map<String, Object>> listaOfProducts = new ArrayList<>();

        for (ItemOrder products : list) {

            Map<String, Object> product = new HashMap<>();

            product = findProduct(products.getProduct().getName(), products.getProduct().getPrice(), products.getQuantity());

            listaOfProducts.add(product);
        }

        return listaOfProducts;
    }
}
