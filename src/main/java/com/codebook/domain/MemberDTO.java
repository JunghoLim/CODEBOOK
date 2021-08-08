package com.codebook.domain;

import lombok.Data;

@Data
public class MemberDTO {
    private String username;    //DB에서는 email이나 security 설정의 편의성을 위해 username으로 사용
    private String password;
    private String name;
    private String nickName;
}
