package com.jpa.item.repository;

import com.jpa.item.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository  extends JpaRepository<Item, Long> {
    /**
     * name(=)으로 item 검색
     * @param name
     * @return
     */
    List<Item> findByName(String name);

    /**
     * name(=), desc(=)으로 item 검색
     * @param name
     * @param desc
     * @return
     */
    List<Item> findByNameOrDesc(String name, String desc);

    /**
     * name(=), desc(=)으로 검색 후 id(desc) 정렬
     * @param name
     * @param desc
     * @return
     */
    List<Item> findByNameOrDescOrderByIdDesc(String name, String desc);

    /**
     * JPQL : desc(like) 검색 후 item_id(asc) 정렬
     * @param desc 
     * @return
     */
    @Query("select i from Item i where i.desc like %:desc% order by id asc")
    List<Item> findByItemDesc(@Param("desc") String desc);

    /**
     * Native : desc(like) 검색 후 item_id(asc) 정렬
     * @param desc 
     * @return
     */
    @Query(value = "select * from item i where i.desc like %:desc% order by item_id asc", nativeQuery = true)
    List<Item> findByItemDescNative(@Param("desc") String desc);

}
