package com.codebook.service;

import com.codebook.domain.MemberDTO;
import com.codebook.domain.MemberProfileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface MemberService {

    void signUp(MemberDTO member);
    int duplicateCheck(String email);
    Map<String, Object> extractMemberData(HttpServletRequest req, HttpServletResponse res);
    void updateProfile(MemberProfileDTO profile);
    void updateProfileImg(HttpServletRequest req, HttpServletResponse res, MultipartFile file) throws IOException;
    ResponseEntity<byte[]> getProfileImg(String imgPath) throws IOException;
    Map<String, Object> findProfile(String nickname);
    int followingMember(String from, String to);
}
