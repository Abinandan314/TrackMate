package com.orderapp.springorderapp.repository;

import com.orderapp.springorderapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    Optional<Product> findBySkuCode(String s);
}
