package com.orderapp.springorderapp.controller;

import com.orderapp.springorderapp.model.Product;
import com.orderapp.springorderapp.repository.ProductRepository;
import com.orderapp.springorderapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        List<Product> productList = productRepository.findAll();
        if(productList.isEmpty()){
            return new ResponseEntity<>("No Products Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }
    @PostMapping("/products")
    public ResponseEntity<?> addProducts(@RequestBody Product product){
        try {
            //check for duplicates
            productRepository.save(product);
            return new ResponseEntity<Product>(product,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/products/price")
    public ResponseEntity<?> changeProductPrice(String skuCode,int price){
        return productService.setProductPrice(skuCode,price);
    }
}
