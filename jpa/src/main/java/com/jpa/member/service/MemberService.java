package com.jpa.member.service;

import com.jpa.common.exception.ApiException;
import com.jpa.member.domain.entity.Member;
import com.jpa.member.domain.model.MemberModel;
import com.jpa.member.domain.mapper.MemberMapper;
import com.jpa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    /**
     * 빈을 주입하는 방법으로는 @Autowired, Setter 주입, 생성자 주입 방식이 존재
     * @RequiredArgsConstructor 어노테이션은 final이나 @NonNull이 붙은 필드에 생성자를 자동으로 생성
     * 빈에 생성자가 1개이고 생성자의 파라미터 타입이 빈으로 등록이 가능하면 @Autowired 없이 의존성 주입 가능
     */
    private final MemberRepository memberRepository;

    /**
     * 회원 등록
     * @param member
     * @return MemberModel
     */
    public MemberModel saveMember(Member member) {
        // 이메일 validation
        this.validDuplicateMember(member);

        // 회원정보 저장
        Member savedMember = memberRepository.save(member);

        MemberModel model = MemberMapper.INSTANCE.entityToModel(savedMember);
        log.info("회원저장 데이터 -> {}", model.toString());

        return model;
    }

    /**
     * 이메일 중복 체크
     * @param member 
     */
    private void validDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {
            throw new ApiException("중복 가입된 회원");
        }
    }
}
