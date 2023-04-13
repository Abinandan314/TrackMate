package com.orderapp.springorderapp.service;

import com.orderapp.springorderapp.model.Inventory;
import com.orderapp.springorderapp.model.Product;
import com.orderapp.springorderapp.repository.InventoryRepository;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;
@DataMongoTest
@AutoConfigureDataMongo
class InventoryServiceTest {
    @Autowired
    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService = new InventoryService();
    private Inventory inventoryItem;
    private Product product;

    @BeforeEach
    public void inventorySetup(){
        product = new Product("Sample ID","Sugar","Daily usage",100,"sugar");
        inventoryItem = new Inventory("Sample Inventory Item",product,2,product.getSkuCode());
    }
    @Test
    public void testSetNewPrice(){
         Inventory savedInventoryItem = inventoryRepository.save(inventoryItem);
        System.out.println(savedInventoryItem.getSkuCode());
        assertTrue(inventoryRepository.existsBySkuCode(savedInventoryItem.getSkuCode()));
//        inventoryRepository.existsBySkuCode(savedInventoryItem.getSkuCode());
//        assertEquals(120,savedInventoryItem.getProduct().getPrice());
    }
    @AfterEach
    public void cleanUp(){
        inventoryRepository.delete(inventoryItem);
    }
}