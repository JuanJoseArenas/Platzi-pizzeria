package com.platzi.pizza.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pizza_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_order", nullable = false)
    private Integer idOrder;

    @Column(name = "id_customer", nullable = false,length = 15)
    private String idCustomer;

    @Column(nullable = false,columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "Decimal(6,2)")
    private Double total;

    @Column(nullable = false, length = 1)
    private String method;

    @Column(name = "additional_notes",length = 200)
    private String additionalNotes;

    @OneToOne
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer", updatable = false, insertable = false)
    private CustomerEntity customerEntity;


    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> items;

}
