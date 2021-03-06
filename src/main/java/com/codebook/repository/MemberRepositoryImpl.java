package com.codebook.repository;

import com.codebook.domain.MemberDTO;
import com.codebook.domain.UploadImgFileDTO;
import com.codebook.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final MemberMapper memberMapper;

    @Override
    public void insertMemberAndProfile(MemberDTO memberDTO) {
        memberMapper.insert_member(memberDTO);
        memberMapper.insert_profile(memberDTO);
    }

    @Override
    public void updateMemberProfileImg(UploadImgFileDTO uploadImgFile) {
        memberMapper.insert_picture(uploadImgFile);
        memberMapper.update_ProfileImg(uploadImgFile);
    }
}
