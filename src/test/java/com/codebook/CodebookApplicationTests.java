package com.codebook;

import com.codebook.dto.Member;
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
        Member member = new Member();
        member.setEmail("asd@asd.com");
        member.setName("jungho");
        member.setPassword("12345");
        membermapper.memberSignUp(member);
    }

    @Test
    void serviceMemberSignUpTest(){
        Map<String, String> testJson = new HashMap<>();
        testJson.put("email", "zxc@zxc.com");
        testJson.put("name", "jungho");
        testJson.put("password", "1234");
        memberService.signUp(testJson);
    }

}
