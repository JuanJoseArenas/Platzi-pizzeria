package com.platzi.pizza.web.controller;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.service.PizzaService;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    private final PizzaService  pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> consultar(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam (defaultValue = "8") int elements){
        Page<PizzaEntity> pizzas = pizzaService.getAll(page, elements);
        if(pizzas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(pizzaService.getAll(page,elements), HttpStatus.OK);

    }
    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> consultarById(@PathVariable int idPizza){
        return pizzaService.getById(idPizza).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/available")
    public ResponseEntity<Page<PizzaEntity>> consultarByAvailable(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam (defaultValue = "8") int elements,
                                                                  @RequestParam (defaultValue = "price")String sortBy,
                                                                  @RequestParam (defaultValue = "ASC") String sortDirection){
        return new ResponseEntity<>(pizzaService.getAvailable(page,elements,sortBy,sortDirection),HttpStatus.OK);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> consultarByName(@PathVariable String name){
        return new ResponseEntity<>(pizzaService.getByName(name),HttpStatus.OK);

    }

    @GetMapping("/price/{price}")
    public ResponseEntity<List<PizzaEntity>> consultar3ByPrice(@PathVariable double price){
        return new ResponseEntity<>(pizzaService.getcheapest(price),HttpStatus.OK);

    }

    @GetMapping("/names/{name}")
    public ResponseEntity<PizzaEntity> consultarFirstByName(@PathVariable String name){
        return new ResponseEntity<>(pizzaService.getFirstByName(name),HttpStatus.OK);

    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List<PizzaEntity>> consultarByContaining(@PathVariable String description){
        return new ResponseEntity<>(pizzaService.getContaining(description),HttpStatus.OK);

    }

    @GetMapping("/Notdescription/{description}")
    public ResponseEntity<List<PizzaEntity>> consultarByNotContaining(@PathVariable String description){
        return new ResponseEntity<>(pizzaService.getNotContaining(description),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PizzaEntity> guardar(@RequestBody PizzaEntity pizza){
        if(pizza.getIdPizza()==null || !pizzaService.exists(pizza.getIdPizza())){
            return new ResponseEntity<>(pizzaService.save(pizza),  HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping()
    public ResponseEntity<PizzaEntity> actualizar(@RequestBody PizzaEntity pizza){
        if(!(pizza.getIdPizza() ==null) || pizzaService.exists(pizza.getIdPizza())){
            return new ResponseEntity<>(pizzaService.save(pizza),  HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/price")
    public ResponseEntity<Void> updatePrice(@RequestBody UpdatePizzaPriceDto dto) {
        if (pizzaService.exists(dto.getPizzaId())) {
            this.pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    


    @DeleteMapping("/{idPizza}")
    public ResponseEntity<Void> eliminarById(@PathVariable int idPizza) {
        if (pizzaService.exists(idPizza)) {
            pizzaService.delete(idPizza);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
