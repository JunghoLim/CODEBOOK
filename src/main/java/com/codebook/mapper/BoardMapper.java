package com.codebook.mapper;

import com.codebook.domain.BoardDTO;
import com.codebook.domain.CommentDTO;
import com.codebook.domain.MemberProfileDTO;
import com.codebook.domain.UploadImgFileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {
    BoardDTO board_detail(int bno);

    List<BoardDTO> page_per_board(int start_row,int end_row, String category);

    List<BoardDTO> page_per_board_search(int start_row,int end_row, String searchText);

    List<BoardDTO> page_per_board_search_byEmail(int start_row,int end_row, String searchText);

    int all_board(String category);

    int all_board_search(String searchText);

    int all_board_search_byEmail(String searchText);

    int insert_comment(String comment, int bno,String email);

    List<BoardDTO> select_main_termBoard(int term, int endNum);

    List<BoardDTO> select_main_latestBoard();

    List<MemberProfileDTO> select_main_profile();

    int deleteComment(String email,int cno);

    int updateComment(int cno,String comment);

    int insert_board(BoardDTO boardDTO);

    int insert_imgfile(UploadImgFileDTO uploadImgFileDTO);

    void update_views(int bno);

    int verificateEmail(String email,int cno,int bno);

    int deleteRecommend(String email,int cno,int bno);

    int insertRecommend(String email,int cno,int bno);

    void updateCommentRecommend(int cno,int countRecommend);

    int countCommentRecommend(int cno);

    //boardrecommend 테이블에서 갯수 세는 메서드
    int countBoardRecommend(int bno);

    int validateBoardRecommend(String email,int bno);

    //게시판 추천 버튼 누름
    void insertBoardRecommend(String email,int bno);

    //게시판에 추천 한거 넣음
    int updateBoardRecommend(int recommendCount,int bno);

}
