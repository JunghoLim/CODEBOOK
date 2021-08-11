package com.codebook.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8090")
@RequestMapping("/api")
public class BoardController {//게시판 정보 가져오는....
    @RequestMapping(value = "/board",method = RequestMethod.POST)
    public Map<String,String> board_view(){
        Map<String,String> board = new HashMap<>();

        return board;
    }
}
