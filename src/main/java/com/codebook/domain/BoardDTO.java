package com.codebook.domain;


import lombok.Data;

@Data
public class BoardDTO {
    private int bno;//글번호
    private String nickname;//닉네임(프로필 테이블에 존재함)
    private String title;//타이틀
    private String writedate;//작성일
    private String email;//이메일
    private String content;
    private String category;
    private int views;//조회수
    private int recommend;//추천수
    private int comment_cnt;
    private String picturePath;
}
