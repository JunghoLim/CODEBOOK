package com.codebook;

import com.codebook.controller.BoardController;
import com.codebook.domain.MemberDTO;
import com.codebook.mapper.BoardMapper;
import com.codebook.mapper.MemberMapper;
import com.codebook.service.BoardService;
import com.codebook.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class CodebookApplicationTests {

    @Autowired
    private MemberMapper membermapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardController boardController;

    @Test
    void contextLoads() {
    }

    @Test
    void memberInsertTest(){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail("asd@asd.com");
        memberDTO.setName("jungho");
        memberDTO.setPassword("12345");
        membermapper.memberSignUp(memberDTO);
    }

    @Test
    void serviceMemberSignUpTest(){
        Map<String, String> testJson = new HashMap<>();
        testJson.put("email", "zxc@zxc.com");
        testJson.put("name", "jungho");
        testJson.put("password", "1234");
        memberService.signUp(testJson);
    }

    @Test
    void findMemberByEmailTest(){
        MemberDTO member = membermapper.findMemberByEmail("asd@asd.com");
        System.out.println(member.getEmail());
    }

    @Test
    void board_list_test(){
        System.out.println(boardMapper.board_list());
    }
    @Test
    void 보드서비스리스트테스트(){
        System.out.println(boardService.board_list());
    }

}
