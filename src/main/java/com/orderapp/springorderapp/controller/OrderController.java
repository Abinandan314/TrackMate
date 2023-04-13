package com.orderapp.springorderapp.controller;

import com.orderapp.springorderapp.model.OrderLineItem;
import com.orderapp.springorderapp.model.Orders;
import com.orderapp.springorderapp.model.Product;
import com.orderapp.springorderapp.repository.InventoryRepository;
import com.orderapp.springorderapp.repository.OrdersRepository;
import com.orderapp.springorderapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class OrderController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdersRepository ordersRepository;

    @PostMapping("/orders/PO")
    public ResponseEntity<?> addProducts(@RequestBody List<OrderLineItem> skuCodes){
        try {
            return orderService.addPOOrder(skuCodes);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/orders")
    public ResponseEntity<?> getOrders(){
        List<Orders> ordersList = ordersRepository.findAll();
        if(ordersList.isEmpty()){
            return new ResponseEntity<>("No Orders Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ordersList,HttpStatus.OK);
    }

    @PostMapping("/orders/SO")
    public ResponseEntity<?> addSalesOrder(@RequestBody List<OrderLineItem> orderLineItemList){
        try {
            return orderService.addSalesOrder(orderLineItemList);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 }
