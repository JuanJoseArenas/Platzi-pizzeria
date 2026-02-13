package com.platzi.pizza.service;

import com.platzi.pizza.persistence.entity.OrderEntity;
import com.platzi.pizza.persistence.projecction.OrderSummary;
import com.platzi.pizza.persistence.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {
    private final  OrderRepository orderRepository;
    private static final String DELIVERY = "D";
    private static final String CARRYOUT = "C";
    private static final String ON_SITE = "S";

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderEntity> getAll() {
        return this.orderRepository.findAll();
    }

    public List<OrderEntity> getByDate(){
        LocalDateTime today = LocalDate.now().atTime(0,0);
        return this.orderRepository.findAllByDateAfter(today);
    }

    public List<OrderEntity> getByMethod(){
        List<String> methods = Arrays.asList(DELIVERY, CARRYOUT, ON_SITE);
        return this.orderRepository.findAllByMethodIn(methods);
    }

    public List<OrderEntity> getOrderByIdCustomer(String idCustomer){
        return this.orderRepository.findCustomerOrders(idCustomer);
    }

    public OrderSummary getSummary(int orderId){
        return this.orderRepository.findSummary(orderId);
    }

    public OrderEntity getById(int idOrder){
        return this.orderRepository.findById(idOrder)
                .orElseThrow(()-> new EntityNotFoundException("Order no Encontrada"));
    }
}

