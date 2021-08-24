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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void serviceMemberSignUpTest(){
        MemberDTO member = new MemberDTO();
        String testEmail = ((int)(Math.random()*10000)+1) + "@testemail.com";
        member.setEmail(testEmail);
        member.setNickname("test");
        member.setPassword("1234");
        memberService.signUp(member);
    }

    /*@Test
    void findMemberByEmailTest(){
        MemberDTO member = membermapper.findMemberByEmail("asd@asd.com");
        System.out.println(member.getEmail());
    }*/

    /*@Test
    void board_list_test(){
        System.out.println(boardMapper.board_list());
    }
    @Test
    void 보드서비스리스트테스트(){
        System.out.println(boardService.board_list());
    }
    @Test
    void 보드디테일테스트(){
        int bno = 1;
        boardService.board_detail(bno);
    }*/
}
