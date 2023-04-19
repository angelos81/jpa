package com.jpa.item.domain.entity;

import com.jpa.common.domain.DateInfo;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ITEM_IMG")
@NoArgsConstructor
public class ItemImg {
    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "img_name")
    private String name;

    private String url;

    @Column(name = "rep_img_yn")
    private String repImgYn;

    @Embedded
    private DateInfo dateInfo;
}
