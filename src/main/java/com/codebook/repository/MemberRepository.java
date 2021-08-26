package com.codebook.repository;

import com.codebook.domain.MemberDTO;
import com.codebook.domain.UploadImgFileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MemberRepository {

    void insertMemberAndProfile(MemberDTO memberDTO);
    void updateMemberProfileImg(UploadImgFileDTO uploadImgFile);
}
