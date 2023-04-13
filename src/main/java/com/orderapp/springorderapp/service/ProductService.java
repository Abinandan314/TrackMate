package com.orderapp.springorderapp.service;

import com.orderapp.springorderapp.model.Product;
import com.orderapp.springorderapp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductService{
    @Autowired
    private ProductRepository productRepository;
    public ResponseEntity<?> setProductPrice(String skuCode,int price){
        Optional<Product>  product = productRepository.findBySkuCode(skuCode);
        if(product.isEmpty())
        {
            return new ResponseEntity<String>("Product with sku-code "+skuCode+" not Found in Inventory", HttpStatus.NOT_MODIFIED);
        }
        product.get().setPrice(price);
        productRepository.save(product.get());
        return new ResponseEntity<String>("Product price Updated",HttpStatus.ACCEPTED);
    }
}
