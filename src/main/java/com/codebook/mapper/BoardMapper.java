package com.codebook.mapper;

import com.codebook.domain.BoardDTO;
import com.codebook.domain.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {
    BoardDTO board_detail(int bno);
    List<BoardDTO> page_per_board(int start_row,int end_row, String category);
    List<BoardDTO> page_per_board_search(int start_row,int end_row, String searchText);
    int all_board(String category);
    int all_board_search(String searchText);
    int insert_comment(String comment, int bno,String email);
}
