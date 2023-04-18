package com.jpa.member.controller;

import com.jpa.member.dto.MemberDto;
import com.jpa.member.entity.Member;
import com.jpa.member.model.MemberModel;
import com.jpa.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Slf4j
public class MemberController {

    /**
     * 빈을 주입하는 방법으로는 @Autowired, Setter 주입, 생성자 주입 방식이 존재
     * @RequiredArgsConstructor 어노테이션은 final이나 @NonNull이 붙은 필드에 생성자를 자동으로 생성
     * 빈에 생성자가 1개이고 생성자의 파라미터 타입이 빈으로 등록이 가능하면 @Autowired 없이 의존성 주입 가능
     */
    private final MemberService memberService;


    /**
     * 회원정보 저장
     */
    @PostMapping("")
    public Object saveMember(@Valid @RequestBody MemberDto memberDto, BindingResult bindingResult) {
        log.info("memberDto -> {}", memberDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        MemberModel model = memberService.saveMember(Member.setMember(memberDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

}
