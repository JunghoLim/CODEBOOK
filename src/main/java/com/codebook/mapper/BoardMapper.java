package com.codebook.mapper;

import com.codebook.domain.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardMapper {
    BoardDTO board_detail(int bno);
    List<BoardDTO> page_per_board(int start_row,int end_row);
    int all_board();
}
