package com.jpa.member.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MemberListModel {
    private Long totalCnt;

    List<MemberModel> memberModelList = new ArrayList<>();
}
