package com.codebook.controller;

import com.codebook.domain.FollowerDTO;
import com.codebook.domain.MemberDTO;
import com.codebook.domain.MemberProfileDTO;
import com.codebook.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public int memberDuplicateCheck(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "nickname", required = false) String nickname){
        //반환값이 1이라면 중복 0이면 중복 아님.
        if(StringUtils.isNotEmpty(email)){
            return memberService.duplicateCheck(email);
        }else{
            return memberService.nicknameDuplicateCheck(nickname);
        }
    }

    //토큰을 통해 유저의 정보를 추출 후 반환
    @GetMapping("/member")
    public Map<String, Object> extractMemberDataFromToken(HttpServletRequest req, HttpServletResponse res) {
        return memberService.extractMemberData(req, res);
    }

    @PostMapping("/member/profile")
    public void updateMemberProfile(@RequestBody MemberProfileDTO profile){
        memberService.updateProfile(profile);
    }

    @PostMapping("/member/profile/img")
    public void updateProfileImg(HttpServletRequest req, HttpServletResponse res, @RequestParam("file") MultipartFile file) throws IOException {
        memberService.updateProfileImg(req, res, file);
    }

    @GetMapping(value = {"/member/profile/img/{imgName}", "/file/img/{imgName}"})
    public ResponseEntity<byte[]> getProfileImg(@PathVariable("imgName") String imgName) throws IOException {
        return memberService.getProfileImg(imgName);
    }

    @GetMapping("/member/profile")
    public Map<String, Object> findMember(@RequestParam("searchText") String searchText){
        return memberService.findProfile(searchText);
    }

    @PostMapping("/member/follow")
    public int followingMember(@RequestBody FollowerDTO follower){
        return memberService.followingMember(follower.getFrom(), follower.getTo());
    }

}
