package com.orderapp.springorderapp.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection= "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String category;
    private long price;
    private String skuCode;
}
