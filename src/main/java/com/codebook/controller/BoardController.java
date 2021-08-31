package com.codebook.controller;


import com.codebook.mapper.BoardMapper;
import com.codebook.service.BoardService;
import com.codebook.service.MemberService;
import lombok.RequiredArgsConstructor;
import oracle.ucp.proxy.annotation.Post;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8090")
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {//게시판 정보 가져오는....

    private final BoardService boardService;
    private final MemberService memberService;
    @GetMapping("/board/list")
    public Map<String, Object> board_view(@PathParam("page") int page, @PathParam("category") String category, @PathParam("searchText") String searchText) {
        if (page == 0) {
            page = 1;
        }
        int pagination = 0;
        Map<String, Object> board = new HashMap<>();
        if (StringUtils.isEmpty(category) && StringUtils.isNotEmpty(searchText)) {
            String[] isEmail = searchText.split("_");
            if (isEmail[0].equals("email")) {
                pagination = boardService.page_per_data_search_byEmail(isEmail[1]);
                board.put("board_list", boardService.board_list_search_byEmail(page, isEmail[1]));
            } else {
                pagination = boardService.page_per_data_search(searchText);
                board.put("board_list", boardService.board_list_search(page, searchText));
            }
        } else {
            pagination = boardService.page_per_data(category);
            board.put("board_list", boardService.board_list(page, category));
        }
        board.put("pagination", pagination);
        board.put("category", category);
        return board;
    }

    @GetMapping("/board")
    public Map<String, Object> board_detail(@PathParam("bno") int bno) {
        Map<String, Object> board_detail = new HashMap<>();

        board_detail.put("board_detail", boardService.board_detail(bno));

        return board_detail;

    }

    @GetMapping("/board/comment")
    public Map<String, Object> board_comment(@PathParam("bno") int bno) {
        Map<String, Object> comment = new HashMap<>();
        comment.put("comment_list", boardService.comment(bno));
        return comment;
    }

    @PostMapping("/comment")
    public int input_comment(@RequestBody Map<String, Object> param) {

        String paramBno= (String)param.get("bno");
        int bno = Integer.parseInt(paramBno);
        String email = (String) param.get("email");
        String comment = (String) param.get("comment");
        return boardService.insert_comment(comment,bno,email);

    }

    @GetMapping("/board/main/list")
    public Map<String, Object> mainBoards() {
        return boardService.getMainBoards();
    }

    @PostMapping("/comment/delete")
    public void deleteComment(@RequestBody Map<String,String> param){

            String src = param.get("cno");
            int cno = Integer.parseInt(src);
            String email  = param.get("email");
            boardService.deleteComment(email,cno);
    }
    @PostMapping("/comment/update")
    public int updateCommend(@RequestBody Map<String,String>param){
        String str = param.get("cno");
        int cno = Integer.parseInt(str);
        String comment = param.get("comment");
        return boardService.updateComment(cno,comment);
    }
}


