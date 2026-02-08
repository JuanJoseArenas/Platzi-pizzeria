package com.platzi.pizza.web.controller;

import com.platzi.pizza.persistence.entity.OrderEntity;
import com.platzi.pizza.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService   orderService;


    @GetMapping()
    public ResponseEntity<List<OrderEntity>> getAll(){
        return new ResponseEntity<>(this.orderService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{idOrder}")
    public ResponseEntity<OrderEntity> getAll(@PathVariable int idOrder){
        return orderService.getById(idOrder).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}
