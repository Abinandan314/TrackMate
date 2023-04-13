package com.orderapp.springorderapp.service;

import com.orderapp.springorderapp.model.Inventory;
import com.orderapp.springorderapp.model.OrderLineItem;
import com.orderapp.springorderapp.model.Orders;
import com.orderapp.springorderapp.model.Product;
import com.orderapp.springorderapp.repository.InventoryRepository;
import com.orderapp.springorderapp.repository.OrdersRepository;
import com.orderapp.springorderapp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    public ResponseEntity<?> addPOOrder(List<OrderLineItem> orderLineItems){
        Orders orders = new Orders();
        List<Product> productList = new ArrayList<>();
        int totalQuantity = 0,notAddedItems = 0,totalPrice = 0;
        for(int index=0;index<orderLineItems.size();index++){
            if(productRepository.findBySkuCode(orderLineItems.get(index).getSkuCode()).isEmpty()){
                log.info("Item with sku-code : " + orderLineItems.get(index).getSkuCode() + " is not found in the Products List");
                notAddedItems++;
                continue;
            }
            productList.add(productRepository.findBySkuCode(orderLineItems.get(index).getSkuCode()).get());
            totalQuantity+=(orderLineItems.get(index).getQuantity());
            totalPrice+=(productRepository.findBySkuCode(orderLineItems.get(index).getSkuCode()).get().getPrice() * orderLineItems.get(index).getQuantity());
            inventoryService.addProdcutsToInventory(productRepository.findBySkuCode(orderLineItems.get(index).getSkuCode()).get(),orderLineItems.get(index).getQuantity());
        }
        if(productList.size() > 0){
         orders.setProducts(productList);
         orders.setOrderType("PO");
         orders.setQuantity(totalQuantity);
         orders.setTotalPrice(totalPrice);
         ordersRepository.save(orders);

         return new ResponseEntity<String>("Out of "+ orderLineItems.size() + " items " + (orderLineItems.size()-notAddedItems) +" is/are updated", HttpStatus.OK);
        }
        return new ResponseEntity<String>("No products Added",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addSalesOrder(List<OrderLineItem> orderLineItems){
        Orders orders = new Orders();
        List<Product> orderedItems = new ArrayList<>();
        int totalQuantity = 0,notAddedItems = 0,totalPrice = 0;
        int numberOfItemsOrdered = orderLineItems.size();
        for(int index = 0;index < numberOfItemsOrdered;index++){
            String skuCode = orderLineItems.get(index).getSkuCode();
            int quantity  = orderLineItems.get(index).getQuantity();
            Optional<Inventory> product = inventoryRepository.findBySkuCode(skuCode);
            int availableQuantity = product.get().getQuantity();
            if (product.isEmpty()){
                return new ResponseEntity<String>("Product with sku-code "+skuCode+" not Found in Inventory, So has not been placed",HttpStatus.NOT_FOUND);
            }
            if(quantity > availableQuantity){
                return new ResponseEntity<String>("Product with sku-code "+skuCode+" has been requested for " + quantity + ". But only "+availableQuantity+" is found, So has not been processed",HttpStatus.NOT_FOUND);
            }
            orderedItems.add(product.get().getProduct());
            totalPrice+=(product.get().getProduct().getPrice() * quantity);
            totalQuantity+=quantity;
            orders.setOrderType("SO");
            orders.setProducts(orderedItems);
            orders.setQuantity(totalQuantity);
            orders.setTotalPrice(totalPrice);
            ordersRepository.save(orders);
            product.get().setQuantity(availableQuantity-quantity);
            if(product.get().getQuantity() == 0){
                inventoryRepository.delete(product.get());
            }
            else {
                inventoryRepository.save(product.get());
            }
        }
        return new ResponseEntity<String>("Order Placed",HttpStatus.OK);
    }

}
