package com.platzi.pizza.service;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.persistence.repository.PizzaPagSortRepository;
import com.platzi.pizza.persistence.repository.PizzaReposiroty;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import com.platzi.pizza.service.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    private final PizzaReposiroty pizzaReposiroty;
    private final PizzaPagSortRepository pizzaPagSortRepository;

    @Autowired
    public PizzaService(PizzaReposiroty pizzaReposiroty, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaReposiroty = pizzaReposiroty;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    public Page<PizzaEntity> getAll(int page, int elements) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return pizzaPagSortRepository.findAll(pageRequest);
    }

    public Page<PizzaEntity> getAvailable(int page, int elements, String sortBy, String sortDirection){
        System.out.println(this.pizzaReposiroty.countByVeganIsTrue());

        Sort sort =Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return pizzaPagSortRepository.findByAvailableTrue(pageRequest);
    }

    public PizzaEntity getByName(String name){
        return pizzaReposiroty.findAllByAvailableTrueAndNameIgnoreCase(name);
    }

    public PizzaEntity getFirstByName(String name){
        return pizzaReposiroty.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(()->new RuntimeException("La Pizza no existe"));
    }
    public List<PizzaEntity> getContaining(String description){
        return pizzaReposiroty.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(description);
    }

    public List<PizzaEntity> getcheapest(double price){
        return pizzaReposiroty.findTop3ByAvailableIsTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }


    public List<PizzaEntity> getNotContaining(String description){
        return pizzaReposiroty.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(description);
    }

    public Optional<PizzaEntity> getById(int idPizza){
        return pizzaReposiroty.findById(idPizza);
    }

    public PizzaEntity save(PizzaEntity pizza){
        return pizzaReposiroty.save(pizza);
    }

    public boolean exists(int idPizza){
        return this.pizzaReposiroty.existsById(idPizza);
    }

    public void delete(int idPizza){
        pizzaReposiroty.deleteById(idPizza);
    }

    @Transactional(noRollbackFor = EmailApiException.class)
    public void updatePrice(UpdatePizzaPriceDto dto){
        this.pizzaReposiroty.updatePrice(dto);
        this.sendEmail();
    }

    private void sendEmail(){
        throw new EmailApiException();

    }

}
