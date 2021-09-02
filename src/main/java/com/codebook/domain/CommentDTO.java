package com.codebook.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommentDTO {
    private String nickname;
    private String email;
    private String content;
    private String writedate;
    private int commentrecommend;
    private int bno;
    private int cno;
}
