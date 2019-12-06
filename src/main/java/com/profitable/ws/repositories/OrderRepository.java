package com.profitable.ws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.profitable.ws.model.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}
