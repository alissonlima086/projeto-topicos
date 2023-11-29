package br.unitins.topicos1.service;

import java.util.List;

import br.unitins.topicos1.dto.CreditCardDTO;
import br.unitins.topicos1.dto.OrderResponseDTO;

public interface OrderService {
    // recursos basicos
    List<OrderResponseDTO> findAll();

    OrderResponseDTO findById(Long id);

    OrderResponseDTO buyUsingPix(Long idUser);

    OrderResponseDTO buyUsingCreditCard(Long idUser, CreditCardDTO creditCardDto);

    public List<OrderResponseDTO> findAllUsers(Long idUser);

}
