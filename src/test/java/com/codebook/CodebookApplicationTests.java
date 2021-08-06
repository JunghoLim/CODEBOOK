package com.codebook;

import com.codebook.dto.Member;
import com.codebook.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CodebookApplicationTests {

    @Autowired
    private MemberMapper membermapper;


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

}
