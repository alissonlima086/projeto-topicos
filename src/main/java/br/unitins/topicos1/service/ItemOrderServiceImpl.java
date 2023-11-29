package br.unitins.topicos1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.dto.ItemOrderDTO;
import br.unitins.topicos1.dto.ItemOrderResponseDTO;
import br.unitins.topicos1.model.Comic;
import br.unitins.topicos1.model.ItemOrder;
import br.unitins.topicos1.model.User;
import br.unitins.topicos1.repository.ComicRepository;
import br.unitins.topicos1.repository.ItemOrderRepository;
import br.unitins.topicos1.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemOrderServiceImpl implements ItemOrderService{
    
    @Inject
    ItemOrderRepository itemOrderRepository;

    @Inject
    ComicRepository comicRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    Validator validator;

    @Override
    public List<ItemOrderResponseDTO> findAll() {
        List<ItemOrder> list = itemOrderRepository.listAll();
        return list.stream().map(ItemOrderResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ItemOrderResponseDTO> findAllCart(Long idUser) {
        User user = userRepository.findById(idUser);
        List<ItemOrder> list = itemOrderRepository.findItemOrderByUser(user);
        return list.stream().map(ItemOrderResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public ItemOrderResponseDTO findById(Long id) {
        ItemOrder itemOrder = itemOrderRepository.findById(id);
        if (itemOrder == null)
            throw new NotFoundException("Compra não encontrada.");
        return new ItemOrderResponseDTO(itemOrder);
    }

    @Override
    @Transactional
    public ItemOrderResponseDTO create(Long idUser, ItemOrderDTO itemOrderDto) throws ConstraintViolationException {

        ItemOrder entity = new ItemOrder();
        Comic comic = comicRepository.findById(itemOrderDto.idProduct());
        if(itemOrderDto.quantity() > comic.getInventory()){
            throw new NotFoundException("Produto sem Estoque");
        }

        comic.setInventory(comic.getInventory()-itemOrderDto.quantity());

        entity.setQuantity(itemOrderDto.quantity());
        entity.setProduct(comicRepository.findById(itemOrderDto.idProduct()));
        entity.setUser(userRepository.findById(idUser));
        entity.setTotalItem(entity.getProduct().getPrice() * entity.getQuantity());

        itemOrderRepository.persist(entity);

        return new ItemOrderResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long idUser, Long idItem) {

        ItemOrder entity = itemOrderRepository.findById(idItem);
        Comic comic = comicRepository.findById(entity.getProduct().getId());

        if(entity.isIdBuyed() == true){
            throw new NotAuthorizedException("Esse Item ja foi Orderdo");
        }

        if(entity.getUser().getId() == idUser){
            comic.setInventory(comic.getInventory() + entity.getQuantity());
            itemOrderRepository.deleteById(idItem);
        }
        else
            throw new NotAuthorizedException("Você não pode excluir items de outros usuarios");
    }

    @Override
    public long count() {
        return itemOrderRepository.count();
    }

    @Override
    public long countItemOrder(Long id) throws NullPointerException {
        User user = userRepository.findById(id);
        List<ItemOrder> listOfItens = new ArrayList<ItemOrder>();
        listOfItens = itemOrderRepository.findItemOrderByUser(user);
        return listOfItens.size();
    }
}
