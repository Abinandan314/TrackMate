package com.orderapp.springorderapp.repository;

import com.orderapp.springorderapp.model.Inventory;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
@Repository
public interface InventoryRepository extends MongoRepository<Inventory,String> {
    boolean existsBySkuCode(String skuCode);
    Optional<Inventory> findBySkuCode(String skuCode);
}
