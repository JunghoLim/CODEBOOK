package com.codebook;

import com.codebook.controller.BoardController;
import com.codebook.domain.MemberDTO;
import com.codebook.mapper.BoardMapper;
import com.codebook.mapper.MemberMapper;
import com.codebook.security.user.Role;
import com.codebook.security.user.Status;
import com.codebook.service.BoardService;
import com.codebook.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void serviceMemberSignUpTest(){
        MemberDTO member = new MemberDTO();
        String testEmail = ((int)(Math.random()*10000)+1) + "@testemail.com";
        member.setEmail(testEmail);
        member.setPassword(passwordEncoder.encode("1234"));
        member.setNickname("testnickname");
        member.setRole(Role.ROLE_USER.name());
        member.setStatus(Status.NONBLOCKED.name());
        member.setToken("");
        memberService.signUp(member);
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
    @Test
    void 보드디테일테스트(){
        int bno = 1;
        boardService.board_detail(bno);
    }
}
