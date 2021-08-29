package com.codebook.controller;


import com.codebook.service.BoardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8090")
@RequestMapping("/api")
public class BoardController {//게시판 정보 가져오는....

    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/board/list")
    public Map<String, Object> board_view(@PathParam("page") int page, @PathParam("category") String category, @PathParam("searchText") String searchText){
        if(page==0){
            page = 1;
        }
        int pagination = 0;
        Map<String,Object> board = new HashMap<>();
        if(StringUtils.isEmpty(category) && StringUtils.isNotEmpty(searchText)){
            pagination = boardService.page_per_data_search(searchText);
            board.put("board_list", boardService.board_list_search(page, searchText));
        }else {
            pagination = boardService.page_per_data(category);
            board.put("board_list", boardService.board_list(page, category));
        }
        board.put("pagination", pagination);
        board.put("category", category);
        return board;
    }
    @GetMapping("/board")
    public Map<String,Object> board_detail(@PathParam("bno")int bno){
        Map<String,Object> board_detail = new HashMap<>();

        board_detail.put("board_detail",boardService.board_detail(bno));

        return board_detail;

    }
    @GetMapping("/board/comment")
        public Map<String,Object> board_comment(@PathParam("bno")int bno){
            Map<String,Object> comment = new HashMap<>();
            comment.put("comment_list",boardService.comment(bno));
            return comment;
        }

    @PostMapping("/comment")
    public int input_comment(@RequestBody Map<String,Object> param){
        int bno = (int)param.get("bno");
        String email = (String)param.get("email");
        String comment = (String)param.get("comment");

        int result = boardService.insert_comment(comment,bno,email);
        return result;
        }
    }

