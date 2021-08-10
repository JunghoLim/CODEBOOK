package com.codebook.controller;

import com.codebook.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8090")
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //서버 컨넥팅 테스트용
    @GetMapping("/test")
    public String connectTest(@RequestParam("req") String req){
        return "connecting success your request is" + req;
    }

    //회원가입
    @PostMapping("/member/new")
    public void memberSignUp(@RequestParam Map<String, String> param){
        memberService.signUp(param);
    }

    //로그인
    @GetMapping( "/member")
    public Map<String,String> memberLogin(@RequestParam Map<String,String> param){
        return param;
    }

    //아이디 중복 검사
    @GetMapping("/member/duplicate")
    public int memberDuplicateCheck(@RequestParam("email")String email){
        //반환값이 1이라면 중복 0이면 중복 아님.
        return memberService.duplicateCheck(email);
    }
}
