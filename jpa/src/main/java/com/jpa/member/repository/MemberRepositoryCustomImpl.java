package com.jpa.member.repository;

import com.jpa.member.domain.dto.MemberSearchDto;
import com.jpa.member.domain.entity.Member;
import com.jpa.member.domain.entity.QMember;
import com.jpa.member.domain.model.MemberModel;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class MemberRepositoryCustomImpl extends QuerydslRepositorySupport implements MemberRepositoryCustom {
    public MemberRepositoryCustomImpl() {
        super(Member.class);
    }

    @Override
    public Page<MemberModel> searchMember(MemberSearchDto memberSearchDto, Pageable pageable) {
        QMember member = QMember.member;

        JPQLQuery<MemberModel> jpqlQuery = from(member)
                .where(searchCondition(memberSearchDto))
                .orderBy(member.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .select(Projections.bean(MemberModel.class, member.id, member.name, member.email, member.address));

        QueryResults<MemberModel> results = jpqlQuery.fetchResults();

        List<MemberModel> memberModelList = results.getResults();
        long totalCnt = results.getTotal();;

        return new PageImpl<>(memberModelList, pageable, totalCnt);
    }

    /**
     * 회원 검색 조건 설정
     * @param memberSearchDto
     * @return BooleanBuilder
     */
    private BooleanBuilder searchCondition(MemberSearchDto memberSearchDto) {
        QMember member = QMember.member;

        // id 조건 : =
        BooleanBuilder condition = new BooleanBuilder();
        if (StringUtils.isNotBlank(memberSearchDto.getId())) {
            condition.and(member.id.eq(memberSearchDto.getId()));
        }

        // 이름 조건 : %name%
        if (StringUtils.isNotBlank(memberSearchDto.getName())) {
            condition.and(member.name.contains(memberSearchDto.getName()));
        }

        // 이메일 조건 : email%
        if (StringUtils.isNotBlank(memberSearchDto.getEmail())) {
            condition.and(member.email.startsWith(memberSearchDto.getEmail()));
        }

        // 주소 조건 : %address%
        if (StringUtils.isNotBlank(memberSearchDto.getAddress())) {
            condition.or(member.address.contains(memberSearchDto.getAddress()));
        }

        return condition;
    }
}
