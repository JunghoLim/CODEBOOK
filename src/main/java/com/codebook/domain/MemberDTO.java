package com.codebook.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberDTO {
    private String email;
    private String password;
    private String role;
    private String status;
    private String token;
}
