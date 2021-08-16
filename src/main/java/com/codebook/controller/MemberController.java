package com.codebook.controller;

import com.codebook.domain.MemberDTO;
import com.codebook.service.MemberService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //서버 컨넥팅 테스트용
    @GetMapping("/test")
    public String connectTest(Map<String, Object> req){
        return "connecting success your request is" + req;
    }

    //회원가입
    @PostMapping("/member/new")
    public void memberSignUp(@RequestBody MemberDTO member){
        memberService.signUp(member);
    }

    //아이디 중복 검사
    @GetMapping("/member/duplicate")
    public int memberDuplicateCheck(@RequestParam("email")String email){
        //반환값이 1이라면 중복 0이면 중복 아님.
        return memberService.duplicateCheck(email);
    }

}
