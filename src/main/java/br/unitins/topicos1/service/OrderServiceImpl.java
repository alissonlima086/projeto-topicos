package br.unitins.topicos1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.dto.CreditCardDTO;
import br.unitins.topicos1.dto.OrderResponseDTO;
import br.unitins.topicos1.model.CardBrand;
import br.unitins.topicos1.model.CreditCard;
import br.unitins.topicos1.model.Order;
import br.unitins.topicos1.model.ItemOrder;
import br.unitins.topicos1.model.Pix;
import br.unitins.topicos1.model.User;
import br.unitins.topicos1.repository.ComicRepository;
import br.unitins.topicos1.repository.CreditCardRepository;
import br.unitins.topicos1.repository.OrderRepository;
import br.unitins.topicos1.repository.ItemOrderRepository;
import br.unitins.topicos1.repository.PixRepository;
import br.unitins.topicos1.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    @Inject
    ItemOrderRepository itemOrderRepository;

    @Inject
    CreditCardRepository creditCardRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    PixRepository pixRepository;

    @Inject
    ComicRepository comicRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    Validator validator;

    @Override
    public List<OrderResponseDTO> findAll() {
        List<Order> list = orderRepository.listAll();
        return list.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> findAllUsers(Long idUser) {
        User user = userRepository.findById(idUser);
        List<Order> list = orderRepository.findOrderByIdUser(user);
        return list.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id);
        if (order == null)
            throw new NotFoundException("Compra não encontrada.");
        return new OrderResponseDTO(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO buyUsingPix(Long idUser) {
        double total = 0;
        Order entity = new Order();
        User user = userRepository.findById(idUser);
        List<ItemOrder> listOfItens = new ArrayList<ItemOrder>();
        listOfItens = itemOrderRepository.findItemOrderByUser(user);

        if(listOfItens.isEmpty() == true) throw new NotFoundException("Sem itens");

        if(user.getAddresses() == null || user.getPhones() == null || user.getPhysicalPerson() == null) throw new NotFoundException("Complete seu cadastro");

        for(int i=0; i<listOfItens.size();i++){
            total = total + listOfItens.get(i).getTotalItem();
            listOfItens.get(i).setIdBuyed(true);
        }

        entity.setListOfItens(listOfItens);
        entity.setTotalOrder(total);
        entity.setUser(userRepository.findById(idUser));

        Pix payment = new Pix(entity.getTotalOrder(), entity.getUser().getPhysicalPerson().getName(), entity.getUser().getPhysicalPerson().getCpf());
        pixRepository.persist(payment);

        entity.setPayment(payment);
        orderRepository.persist(entity);

        return new OrderResponseDTO(entity);
    }

    @Override
    @Transactional
    public OrderResponseDTO buyUsingCreditCard(Long idUser, CreditCardDTO creditCardDto) {
        double total = 0;
        Order entity = new Order();
        User user = userRepository.findById(idUser);
        List<ItemOrder> listOfitens = new ArrayList<ItemOrder>();
        listOfitens = itemOrderRepository.findItemOrderByUser(user);

        if(listOfitens.isEmpty() == true) throw new NotFoundException("Sem itens");

        for(int i=0; i<listOfitens.size();i++){
            total = total + listOfitens.get(i).getTotalItem();
            listOfitens.get(i).setIdBuyed(true);
        }

        entity.setListOfItens(listOfitens);
        entity.setTotalOrder(total);
        entity.setUser(userRepository.findById(idUser));

        CreditCard payement = new CreditCard(entity.getTotalOrder(),
                                            creditCardDto.cardNumber(),
                                            creditCardDto.impressedCardName(),
                                            user.getPhysicalPerson().getCpf(),
                                            CardBrand.valueOf(creditCardDto.cardBrand()));
        
        creditCardRepository.persist(payement);

        entity.setPayment(payement);
        orderRepository.persist(entity);

        return new OrderResponseDTO(entity);
    }
    
}
