package com.codebook.controller;

import com.codebook.domain.MemberDTO;
import com.codebook.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

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
    public int memberDuplicateCheck(@RequestParam("email") String email){
        //반환값이 1이라면 중복 0이면 중복 아님.
        return memberService.duplicateCheck(email);
    }

    //토큰을 통해 유저의 정보를 추출 후 반환
    @GetMapping("/member")
    public Map<String, Object> extractMemberDataFromToken(HttpServletRequest req, HttpServletResponse res) {
        return memberService.extractMemberData(req, res);
    }
}
