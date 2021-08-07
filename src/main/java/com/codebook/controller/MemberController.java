package com.codebook.controller;

import com.codebook.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8090")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/api/member/new")
    public void memberSignUp(@RequestParam Map<String, String> param){
        memberService.signUp(param);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/api/member/login")
    public Map<String,String> memberLogin(@RequestBody Map<String,String> param){
        return param;
    }
}
