package com.orderapp.springorderapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Orders {
    @Id
    private String id;
    private List<Product> products;
    private String OrderType;
    private int quantity;
    private int totalPrice;

}
