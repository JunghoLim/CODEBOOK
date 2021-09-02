package com.codebook.controller;

import com.codebook.service.BoardService;
import com.codebook.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {
    private final MemberService memberService;
    private final BoardService boardService;

    /*@GetMapping("/admin/main")
    public Map<String,Object> getAdminDashBoard(){

    }*/
}
