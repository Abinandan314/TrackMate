package com.orderapp.springorderapp.controller;

import com.orderapp.springorderapp.model.Inventory;
import com.orderapp.springorderapp.model.Product;
import com.orderapp.springorderapp.repository.InventoryRepository;
import com.orderapp.springorderapp.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class InventoryController {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/inventory")
    public ResponseEntity<?> getInventoryData(){
        List<Inventory> items = inventoryRepository.findAll();
        if(items.isEmpty()){
            return new ResponseEntity<>("No Products Found in the Inventory", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(items,HttpStatus.OK);
    }
    @PostMapping("/inventory/price")
    public ResponseEntity<?> setProductPrice(String skuCode,int price){
//        Optional<Inventory> product = inventoryRepository.findBySkuCode(skuCode);
//        if(product.isEmpty())
//        {
//            return new ResponseEntity<String>("Product with sku-code "+skuCode+" not Found in Inventory", HttpStatus.NOT_MODIFIED);
//        }
//        product.get().getProduct().setPrice(price);
//        inventoryRepository.save(product.get());
//        return new ResponseEntity<String>("Product price Updated",HttpStatus.ACCEPTED);
        return inventoryService.setNewProductPrice(skuCode, price);
    }
}
