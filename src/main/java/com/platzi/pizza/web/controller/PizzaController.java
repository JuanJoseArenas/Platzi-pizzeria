package com.platzi.pizza.web.controller;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    private final PizzaService  pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<List<PizzaEntity>> consultar(){
        List<PizzaEntity> pizzas = pizzaService.getAll();
        if(pizzas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(pizzaService.getAll(), HttpStatus.OK);

    }
    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> consultarById(@PathVariable int idPizza){
        return pizzaService.getById(idPizza).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping()
    public ResponseEntity<PizzaEntity> guardar(@RequestBody PizzaEntity pizza){
        return new ResponseEntity<>(pizzaService.save(pizza),  HttpStatus.OK);
    }
}
