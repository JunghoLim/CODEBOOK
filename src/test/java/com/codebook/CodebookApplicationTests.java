package com.codebook;

import com.codebook.controller.BoardController;
import com.codebook.domain.BoardDTO;
import com.codebook.domain.MemberDTO;
import com.codebook.mapper.BoardMapper;
import com.codebook.mapper.MemberMapper;
import com.codebook.service.BoardService;
import com.codebook.service.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@SpringBootTest
class CodebookApplicationTests {

//    @Autowired
//    private MemberMapper membermapper;

//    @Autowired
//    private MemberServiceImpl memberService;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private BoardService boardService;



//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    void serviceMemberSignUpTest(){
//        MemberDTO member = new MemberDTO();
//        String testEmail = ((int)(Math.random()*10000)+1) + "@testemail.com";
//        member.setEmail(testEmail);
//        member.setPassword("1234");
//        memberService.signUp(member);
//    }
    @Test
    void 페이지네이션테스트(){
       List<BoardDTO> list =  boardService.board_list(1);
        System.out.println(list.toString());
    }
    @Test
    void rptlqnfrkwudhrl(){
        int all = boardMapper.all_board();
        System.out.println(all);
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
