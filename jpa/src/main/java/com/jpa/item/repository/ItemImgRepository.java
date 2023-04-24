package com.jpa.item.repository;

import com.jpa.item.domain.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    // @Query("select g from ItemImg g left outer join Item i on g.item_id = i.item_id where i.item_id = :itemId order by g.item_img_id asc")
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    // @Query("select g from ItemImg g left outer join Item i on g.item_id = i.item_id where i.item.id = :itemId and g.rep_img_yn = :repImgYn")
    ItemImg findByItemIdAndRepImgYn(Long itemId, String repImgYn);
}