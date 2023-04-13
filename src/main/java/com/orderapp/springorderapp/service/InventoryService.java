package com.orderapp.springorderapp.service;

import com.orderapp.springorderapp.model.Inventory;
import com.orderapp.springorderapp.model.Product;
import com.orderapp.springorderapp.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    public void addProdcutsToInventory(Product product,int quantity){
        if(inventoryRepository.existsBySkuCode(product.getSkuCode())){
            Inventory existingInventory = inventoryRepository.findBySkuCode(product.getSkuCode()).get();
            existingInventory.setQuantity(existingInventory.getQuantity() + quantity);
            inventoryRepository.save(existingInventory);
            return;
        }
        Inventory inventory = new Inventory();
        inventory.setQuantity(quantity);
        inventory.setProduct(product);
        inventory.setSkuCode(product.getSkuCode());
        inventoryRepository.save(inventory);
    }

    public ResponseEntity<?> setNewProductPrice(String skuCode,int price){
        Optional<Inventory> product = inventoryRepository.findBySkuCode(skuCode);
        if(product.isEmpty())
        {
            return new ResponseEntity<String>("Product with sku-code "+skuCode+" not Found in Inventory", HttpStatus.NOT_MODIFIED);
        }
        product.get().getProduct().setPrice(price);
        inventoryRepository.save(product.get());
        return new ResponseEntity<String>("Product price Updated",HttpStatus.ACCEPTED);
    }
}
