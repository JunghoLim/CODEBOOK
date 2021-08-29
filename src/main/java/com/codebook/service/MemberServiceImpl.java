package com.codebook.service;

import com.codebook.domain.MemberDTO;
import com.codebook.domain.MemberProfileDTO;
import com.codebook.domain.UploadImgFileDTO;
import com.codebook.handler.ProfileImgFileHandler;
import com.codebook.mapper.MemberMapper;
import com.codebook.repository.MemberRepository;
import com.codebook.security.authentication.token.JwtTokenProvider;
import com.codebook.security.user.Role;
import com.codebook.security.user.Status;
import com.codebook.security.user.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileImgFileHandler profileImgFileHandler;

    public Authentication getAuth(HttpServletRequest req, HttpServletResponse res){
        String token = jwtTokenProvider.resolveTokenFromRequest(req);
        Authentication auth = null;
        try{
            auth = jwtTokenProvider.getAuthentication(token);
        }catch (ExpiredJwtException e){
            token = jwtTokenProvider.resolveTokenFromResponse(res);
            auth = jwtTokenProvider.getAuthentication(token);
        }
        return auth;
    }

    @Override
    public void signUp(MemberDTO member){
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setStatus(Status.NONBLOCKED.name());
        member.setRole(Role.ROLE_USER.name());
        member.setToken("");
        memberRepository.insertMemberAndProfile(member);
    }

    @Override
    public int duplicateCheck(String email){
        return memberMapper.duplicateCheckMemberEmail(email);
    }

    @Override
    public Map<String, Object> extractMemberData(HttpServletRequest req, HttpServletResponse res) {
        Authentication auth = getAuth(req, res);
        UserDetailsImpl member = (UserDetailsImpl) auth.getPrincipal();
        MemberProfileDTO profile = memberMapper.findProfileByEmail(member.getUsername());
        String[] role = member.getRole().split("_");
        Map<String, Object> memberData = new HashMap<>();
        memberData.put("email", member.getUsername());
        memberData.put("profile", profile);
        memberData.put("role", role[1]);
        return memberData;
    }

    @Override
    public void updateProfile(MemberProfileDTO profile) {
        memberMapper.update_ProfileInfo(profile);
    }

    @Override
    public void updateProfileImg(HttpServletRequest req, HttpServletResponse res, MultipartFile file) throws IOException {
        Authentication auth = getAuth(req, res);
        UserDetailsImpl member = (UserDetailsImpl) auth.getPrincipal();
        UploadImgFileDTO uploadImgFile = profileImgFileHandler.parseImgFile(member.getUsername(),file);
        memberRepository.updateMemberProfileImg(uploadImgFile);
    }

    @Override
    public ResponseEntity<byte[]> getProfileImg(String imgName) throws IOException {
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String path = "./src/main/resources/static/uploadedfiles/images/";
        InputStream imageStream = new FileInputStream(absolutePath + path + imgName);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }

    @Override
    public Map<String, Object> findProfile(String nickname) {
        Map<String, Object> profile = new HashMap<>();
        profile.put("profiles",memberMapper.findProfileByNickname(nickname));
        return profile;
    }

    @Override
    public int followingMember(String from, String to) {
        if(memberMapper.duplicate_following(from, to) == 0){
            memberMapper.insert_follow(from, to);
            return 1;
        }else{
            memberMapper.delete_following(from, to);
            return 0;
        }
    }

}
