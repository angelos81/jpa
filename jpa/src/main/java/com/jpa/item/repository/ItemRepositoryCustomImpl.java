package com.jpa.item.repository;

import com.jpa.item.domain.dto.ItemSearchDto;
import com.jpa.item.domain.entity.Item;
import com.jpa.item.domain.entity.QItem;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

public class ItemRepositoryCustomImpl extends QuerydslRepositorySupport implements ItemRepositoryCustom{
    public ItemRepositoryCustomImpl() {
        super(Item.class);
    }

    @Override
    public List<Item> search(ItemSearchDto itemSearchDto) {
        QItem item = QItem.item;

        JPQLQuery<Item> query = from(item);

        if (StringUtils.hasText(itemSearchDto.getName())) {
            query.where(item.name.contains(itemSearchDto.getName()));   // %:name%
        }

        if (itemSearchDto.getPrice() != null) {
            query.where(item.price.gt(itemSearchDto.getPrice()));
        }

        if (itemSearchDto.getStock() != null) {
            query.where(item.stock.gt(itemSearchDto.getStock()));
        }

        if (StringUtils.hasText(itemSearchDto.getDesc())) {
            query.where(item.desc.contains(itemSearchDto.getDesc()));
        }

        // 결과를 다른 Dto에 매핑 가능
        //query.select()


        return query.fetch();
    }
}
