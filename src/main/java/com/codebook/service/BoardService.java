package com.codebook.service;

import com.codebook.domain.BoardDTO;
import com.codebook.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardMapper boardMapper;

    public BoardService(BoardMapper boardMapper){this.boardMapper = boardMapper;}
    public List<BoardDTO> board_list(int page){
        int all_data = boardMapper.all_board();
        int start_row= page*10-9;
        int end_row= start_row+9;
        if( end_row > all_data){
            end_row = all_data;
        }
        return boardMapper.page_per_board(start_row,end_row);
    }
    public int page_per_data(){

        return boardMapper.all_board()%10==0 ? boardMapper.all_board()/10 : (boardMapper.all_board()/10)+1;

    }
    public BoardDTO board_detail(int bno){return boardMapper.board_detail(bno);}

}
