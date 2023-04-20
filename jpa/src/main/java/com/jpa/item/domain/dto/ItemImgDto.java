package com.jpa.item.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class ItemImgDto {
    private Long id;

    @NotBlank(message = "이미지명은 필수 입력 입니다.")
    private String name;

    private String url;
}
