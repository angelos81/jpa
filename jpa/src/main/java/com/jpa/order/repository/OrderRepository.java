package com.jpa.order.repository;

import com.jpa.order.domain.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select count(*) " +
            "from Order o " +
            "where o.member.id = :id")
    Long orderCount(@Param("id") String id);

    @Query("select o " +
            "from Order o " +
            "where o.member.id = :id " +
            "order by o.orderDate desc, o.orderStatus desc")
    List<Order> findOrderList(@Param("id") String id, Pageable pageable);
}
