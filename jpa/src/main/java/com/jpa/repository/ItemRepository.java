package com.jpa.repository;

import com.jpa.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository  extends JpaRepository<Item, Long>, QuerydslPredicateExecutor {
    List<Item> findByName(String name);

    List<Item> findByNameOrDesc(String name, String desc);

    List<Item> findByNameOrDescOrderByIdDesc(String name, String desc);

    // 아이템 상세 설명 like 검색 (JPQL)
    @Query("select i from Item i where i.desc like %:desc% order by id asc")
    List<Item> findByItemDesc(@Param("desc") String desc);

    // 아이템 상세 설명 like 검색 (Native Query)
    @Query(value = "select * from item i where i.desc like %:desc% order by item_id asc", nativeQuery = true)
    List<Item> findByItemDescNative(@Param("desc") String desc);

}
