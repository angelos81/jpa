package com.jpa.item.repository;

import com.jpa.item.domain.dto.ItemSearchDto;
import com.jpa.item.domain.entity.Item;
import com.jpa.item.domain.entity.QItem;
import com.jpa.item.domain.model.ItemListModel;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

public class ItemRepositoryCustomImpl extends QuerydslRepositorySupport implements ItemRepositoryCustom{
    public ItemRepositoryCustomImpl() {
        super(Item.class);
    }

    /**
     * 상품 검색 조건 공통화
     * @param itemSearchDto
     * @return BooleanBuilder
     */
    private BooleanBuilder itemSearch(ItemSearchDto itemSearchDto) {
        QItem item = QItem.item;

        BooleanBuilder bb = new BooleanBuilder();

        if (StringUtils.hasText(itemSearchDto.getName())) {
            bb.and(item.name.contains(itemSearchDto.getName()));
        }

        if (itemSearchDto.getPrice() != null) {
            bb.and(item.price.gt(itemSearchDto.getPrice()));
        }

        if (itemSearchDto.getStock() != null) {
            bb.and(item.stock.gt(itemSearchDto.getStock()));
        }

        if (StringUtils.hasText(itemSearchDto.getDesc())) {
            bb.and(item.desc.contains(itemSearchDto.getDesc()));
        }

        return bb;
    }

    @Override
    public List<Item> search(ItemSearchDto itemSearchDto) {
        QItem item = QItem.item;

        JPQLQuery<Item> jpaQuery = from(item);

        if (StringUtils.hasText(itemSearchDto.getName())) {
            jpaQuery.where(item.name.contains(itemSearchDto.getName()));   // %:name%
        }

        if (itemSearchDto.getPrice() != null) {
            jpaQuery.where(item.price.gt(itemSearchDto.getPrice()));
        }

        if (itemSearchDto.getStock() != null) {
            jpaQuery.where(item.stock.gt(itemSearchDto.getStock()));
        }

        if (StringUtils.hasText(itemSearchDto.getDesc())) {
            jpaQuery.where(item.desc.contains(itemSearchDto.getDesc()));
        }

        jpaQuery.orderBy(item.id.desc());

        return jpaQuery.fetch();
    }

    @Override
    public Page<ItemListModel> searchPaging(ItemSearchDto itemSearchDto, Pageable pageable) {
        /**
         * fetchResults() : 조회 대상 리스트 및 전체 개수를 포함하는 QueryResults 반환
         * fetch() : 조회 대상 List 반환
         * fetchOne() : 조회 대상이 1건이면 해당 타입 반환
         *              조회 대상이 1건 초과면 에러 발생
         * fetchFirst() : 조회 대상이 1건 또는 1건 이상이면 1건만 반환
         * fetchCount() : 해당 데이터 전체 개수 반환
         */

        QItem item = QItem.item;

        JPQLQuery<ItemListModel> jpaQuery = from(item)
                .where(itemSearch(itemSearchDto))
                .orderBy(item.id.desc()).orderBy(item.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .select(Projections.bean(ItemListModel.class, item.id, item.name, item.price, item.stock, item.desc));
        QueryResults<ItemListModel> results = jpaQuery.fetchResults();

//        JPQLQuery<ItemListModel> jpaQuery = from(item)
//                .where(itemSearch(itemSearchDto))
//                .orderBy(item.id.desc()).orderBy(item.name.asc())
//                .select(Projections.bean(ItemListModel.class, item.id, item.name, item.price, item.stock, item.desc));
//
//        JPQLQuery<ItemListModel> query = getQuerydsl().applyPagination(pageable, jpaQuery);
//        QueryResults<ItemListModel> results = query.fetchResults();


//        BooleanBuilder bb = itemSearch(itemSearchDto);
//        JPQLQuery<ItemListModel> jpaQuery = from(item)
//                .where(bb)
//                .orderBy(item.id.desc()).orderBy(item.name.asc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .select(Projections.bean(ItemListModel.class, item.id, item.name, item.price, item.stock, item.desc));
//        QueryResults<ItemListModel> results = jpaQuery.fetchResults();


        List<ItemListModel> returnModel = results.getResults();
        long totalCnt = results.getTotal();

        return new PageImpl<>(returnModel, pageable, totalCnt);
    }
}
