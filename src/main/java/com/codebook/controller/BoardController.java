package com.codebook.controller;


import com.codebook.domain.BoardDTO;
import com.codebook.service.BoardService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8090")
public class BoardController {//게시판 정보 가져오는....

    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/board")
    public Map<String, List<BoardDTO>> board_view(){
        Map<String, List<BoardDTO>> board = new HashMap<>();
        board.put("board_list",boardService.board_list());
        return board;
    }
    @GetMapping("/board-detail")
    public Map<String,BoardDTO> board_detail(@Param("bno")int bno){
        Map<String,BoardDTO> board_detail = new HashMap<>();

        board_detail.put("board_detail",boardService.board_detail(bno));

        return board_detail;

    }
}
