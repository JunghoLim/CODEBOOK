package com.codebook.service;

import com.codebook.domain.BoardDTO;
import com.codebook.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public BoardService(BoardMapper boardMapper){
        this.boardMapper = boardMapper;
    }

    public List<BoardDTO> board_list(){return boardMapper.board_list();}

}
