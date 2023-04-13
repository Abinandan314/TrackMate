package com.orderapp.springorderapp.repository;

import com.orderapp.springorderapp.model.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends MongoRepository<Orders,String> {
}
