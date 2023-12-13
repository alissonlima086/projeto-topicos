package br.unitins.topicos1.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.topicos1.dto.ItemOrderDTO;
import br.unitins.topicos1.dto.OrderDTO;
import br.unitins.topicos1.dto.OrderResponseDTO;
import br.unitins.topicos1.model.Comic;
import br.unitins.topicos1.model.ItemOrder;
import br.unitins.topicos1.model.Order;
import br.unitins.topicos1.model.User;
import br.unitins.topicos1.repository.ComicRepository;
import br.unitins.topicos1.repository.OrderRepository;
import br.unitins.topicos1.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import br.unitins.topicos1.validation.ValidationException;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    @Inject
    ComicRepository comicRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderResponseDTO insert(OrderDTO dto, String login) {

        if(dto.itens().isEmpty() || dto.itens().size() == 0 || dto.itens() == null){
            throw new ValidationException("400", "Não há produtos na compra");
        }
        User user = userRepository.findByEmail(login);

        if(user.getPhysicalPerson() == null || user.getPhysicalPerson().getCpf() == null || user.getPhysicalPerson().getName() == null){
            throw new ValidationException("400", "Seu perfil de usuario não está completo");
        }

        Order pedido = new Order();
        pedido.setDateHour(LocalDateTime.now());

        Double total = 0.0;

        for (ItemOrderDTO itemDto : dto.itens()) {
            total += (comicRepository.findById(itemDto.idProduct()).getPrice() * itemDto.quantity());
        }
        pedido.setTotalOrder(total);

        pedido.setItens(new ArrayList<ItemOrder>());
        for (ItemOrderDTO itemDto : dto.itens()) {
            ItemOrder item = new ItemOrder();
            item.setPrice(comicRepository.findById(itemDto.idProduct()).getPrice());
            item.setQuantity(itemDto.quantity());
            item.setOrder(pedido);
            Comic comic = comicRepository.findById(itemDto.idProduct());
            item.setProduct(comic);

            comic.setInventory(comic.getInventory() - item.getQuantity());

            pedido.getItens().add(item);
        }

        pedido.setUser(userRepository.findByEmail(login));

        orderRepository.persist(pedido);

        return OrderResponseDTO.valueOf(pedido);
        
    }

    @Override
    public OrderResponseDTO findById(Long id) {
        return OrderResponseDTO.valueOf(orderRepository.findById(id));
    }

    @Override
    public List<OrderResponseDTO> findByAll() {
        return orderRepository.listAll().stream()
            .map(e -> OrderResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<OrderResponseDTO> findByAll(String login) {
        return orderRepository.listAll().stream()
            .map(e -> OrderResponseDTO.valueOf(e)).toList();
    }
    
}