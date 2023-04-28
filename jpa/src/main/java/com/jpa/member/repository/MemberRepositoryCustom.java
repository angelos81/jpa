package com.jpa.member.repository;

import com.jpa.member.domain.dto.MemberSearchDto;
import com.jpa.member.domain.model.MemberModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    /**
     * 회원검색
     * @param memberSearchDto
     * @param pageable
     * @return Page<MemberModel>
     */
    Page<MemberModel> searchMember(MemberSearchDto memberSearchDto, Pageable pageable);
}
