package com.orderapp.springorderapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "inventory")
public class Inventory {
    @Id
    private String id;
    private Product product;
    private int quantity;
    private String skuCode;
}
