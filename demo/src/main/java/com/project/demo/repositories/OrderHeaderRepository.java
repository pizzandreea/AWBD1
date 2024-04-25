package com.project.demo.repositories;

import com.project.demo.models.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Integer> {
}
