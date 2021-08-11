package com.codebook.domain;


import lombok.Data;

@Data
public class BoardDTO {
    private int bno;//글번호
    private String title;//타이틀
    private String write_date;//작성일
    private String email;//이메일
    private int views;//조회수
    private int recommend;//추천수
    private int comment_c;//댓글 수를 comment_count하면 길어서 c로 줄였습니다.
    private int report_c;

}
