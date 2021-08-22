package com.codebook.service;

import com.codebook.domain.MemberDTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface MemberService {

    void signUp(MemberDTO member);
    int duplicateCheck(String email);
    Map<String, Object> extractMemberData(HttpServletRequest req, HttpServletResponse res);
}
