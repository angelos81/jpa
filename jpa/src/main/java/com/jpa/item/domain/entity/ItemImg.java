package com.jpa.item.domain.entity;

import com.jpa.common.domain.DateInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ITEM_IMG")
@Getter
@Setter
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

    /**
     * 상품 이미지 정보 생성
     * @param name 
     * @param url
     * @return
     */
    public static ItemImg createItemImg(String name, String url) {
        ItemImg itemImg = new ItemImg();
        itemImg.setName(name);
        itemImg.setUrl(url);
        itemImg.setDateInfo(new DateInfo(LocalDateTime.now(), null));

        return itemImg;
    }

    /**
     * 상품 이미지 정보 수정
     * @param name 
     * @param url
     */
    public void updateItemImg(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
