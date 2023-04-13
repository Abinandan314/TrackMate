package com.orderapp.springorderapp.service;

import com.orderapp.springorderapp.model.Product;
import com.orderapp.springorderapp.repository.ProductRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
@SpringBootTest
@AutoConfigureDataMongo()
class ProductServiceTest{
    @Autowired
    private ProductRepository productRepository;


    private Product product;
    @BeforeEach
    public void productItemSetup(){
         product = new Product("Sample ID","Sugar","Daily usage",100,"sugar");
    }

    @Test
    public void testAddProduct(){
        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct);
    }
    @Test
    public void testingFindBySkuCode(){
        Product savedProduct = productRepository.save(product);
        assertEquals(savedProduct.getName(),productRepository.findBySkuCode("sugar").get().getName());
    }

    @AfterEach
    public void removingProducts(){
        productRepository.delete(product);
    }
}