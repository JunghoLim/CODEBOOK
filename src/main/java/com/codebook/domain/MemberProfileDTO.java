package com.codebook.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberProfileDTO {
    private String email;
    private String nickname;
    private int follower;
    private String aboutMe;
    private int boardCnt;
    private String status;
    private String picturePath;
}
