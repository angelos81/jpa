package com.jpa.item.domain.entity;

import com.jpa.common.domain.DateInfo;
import com.jpa.constant.ItemStatus;
import com.jpa.item.domain.dto.ItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ITEM")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "item_name", nullable = false, length = 100)
    private String name;

    private Integer price;

    private Integer stock;

    private String desc;

    public Item(String name, Integer price, Integer stock, String desc) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.desc = desc;
        this.status = ItemStatus.SELL;
        this.dateInfo = new DateInfo(LocalDateTime.now(), null);
    }

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @Embedded
    private DateInfo dateInfo;

    /**
     * 상품 업데이트 정보 설정
     * @param itemDto 
     */
    public void updateItem(ItemDto itemDto) {
        this.name = itemDto.getName();
        this.price = itemDto.getPrice();
        this.stock = itemDto.getStock();
        this.desc = itemDto.getDesc();

        if (itemDto.getStock() <= 0) {
            this.status = ItemStatus.SOLD_OUT;
        } else {
            this.status = ItemStatus.SELL;
        }
    }
}
