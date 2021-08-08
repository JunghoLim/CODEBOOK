package com.codebook.domain;

import lombok.Data;

@Data
public class MemberDTO {
    private String email;
    private String password;
    private String name;
    private String nickName;
}
