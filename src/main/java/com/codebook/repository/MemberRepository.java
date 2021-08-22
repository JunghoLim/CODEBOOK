package com.codebook.repository;

import com.codebook.domain.MemberDTO;

public interface MemberRepository {

    void insertMemberAndProfile(MemberDTO memberDTO);
}
