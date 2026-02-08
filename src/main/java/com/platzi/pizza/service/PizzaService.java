package com.platzi.pizza.service;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.persistence.repository.PizzaReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    private final PizzaReposiroty pizzaReposiroty;

    @Autowired
    public PizzaService(PizzaReposiroty pizzaReposiroty) {
        this.pizzaReposiroty = pizzaReposiroty;
    }

    public List<PizzaEntity> getAll(){
        return pizzaReposiroty.findAll();
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
}
