package com.codebook;

import com.codebook.domain.MemberDTO;
import com.codebook.mapper.MemberMapper;
import com.codebook.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class CodebookApplicationTests {

    @Autowired
    private MemberMapper membermapper;

    @Autowired
    private MemberService memberService;

    @Test
    void contextLoads() {
    }

    @Test
    void memberInsertTest(){
        MemberDTO memberDTO = new MemberDTO();
        String testEmail = ((int)(Math.random()*10000)+1) + "@testemail.com";
        memberDTO.setEmail(testEmail);
        memberDTO.setName("jungho");
        memberDTO.setPassword("12345");
        memberDTO.setNickname("test");
        membermapper.memberSignUp(memberDTO);
    }

    @Test
    void serviceMemberSignUpTest(){
        Map<String, String> testJson = new HashMap<>();
        String testEmail = ((int)(Math.random()*10000)+1) + "@testemail.com";
        testJson.put("email", testEmail);
        testJson.put("name", "jungho");
        testJson.put("password", "1234");
        testJson.put("nickname", "test");
        memberService.signUp(testJson);
    }

    @Test
    void findMemberByEmailTest(){
        MemberDTO member = membermapper.findMemberByEmail("asd@asd.com");
        System.out.println(member.getEmail());
    }

}
