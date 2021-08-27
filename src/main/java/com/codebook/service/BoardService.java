package com.codebook.service;

import com.codebook.domain.BoardDTO;
import com.codebook.domain.CommentDTO;
import com.codebook.mapper.BoardMapper;
import com.codebook.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;
    public BoardService(BoardMapper boardMapper, CommentMapper commentMapper){this.boardMapper = boardMapper;
        this.commentMapper = commentMapper;
    }
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

    public List<CommentDTO> comment(int bno){
        return commentMapper.comment(bno);
    }

    public int insert_comment(String comment,int bno,String email){
        int result = boardMapper.insert_comment(comment,bno,email);
        if(result==1) return result;
        else return 0;
    }
}
