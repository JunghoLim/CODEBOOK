package com.codebook.service;

import com.codebook.domain.BoardDTO;
import com.codebook.domain.CommentDTO;
import com.codebook.mapper.BoardMapper;
import com.codebook.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;

    public BoardService(BoardMapper boardMapper, CommentMapper commentMapper){this.boardMapper = boardMapper;
        this.commentMapper = commentMapper;
    }

    public List<BoardDTO> board_list(int page, String category){
        int all_data = boardMapper.all_board(category);
        int start_row= page*10-9;
        int end_row= start_row+9;
        if( end_row > all_data){
            end_row = all_data;
        }
        return boardMapper.page_per_board(start_row,end_row, category);
    }
    public List<BoardDTO> board_list_search(int page, String searchText){
        int all_data = boardMapper.all_board_search(searchText);
        int start_row= page*10-9;
        int end_row= start_row+9;
        if( end_row > all_data){
            end_row = all_data;
        }
        return boardMapper.page_per_board_search(start_row,end_row, searchText);
    }

    public List<BoardDTO> board_list_search_byEmail(int page, String searchText){
        int all_data = boardMapper.all_board_search_byEmail(searchText);
        int start_row= page*10-9;
        int end_row= start_row+9;
        if( end_row > all_data){
            end_row = all_data;
        }
        return boardMapper.page_per_board_search_byEmail(start_row,end_row, searchText);
    }

    public int page_per_data(String category){
        return boardMapper.all_board(category)%10==0 ? boardMapper.all_board(category)/10 : (boardMapper.all_board(category)/10)+1;
    }

    public int page_per_data_search(String searchText){
        return boardMapper.all_board_search(searchText)%10==0 ? boardMapper.all_board_search(searchText)/10 : (boardMapper.all_board_search(searchText)/10)+1;
    }

    public int page_per_data_search_byEmail(String searchText){
        return boardMapper.all_board_search_byEmail(searchText)%10==0 ? boardMapper.all_board_search_byEmail(searchText)/10 : (boardMapper.all_board_search_byEmail(searchText)/10)+1;
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

    public Map<String, Object> getMainBoards(){
        Map<String, Object> mainBoards = new HashMap<>();
        mainBoards.put("weeklyBestBoards", boardMapper.select_main_termBoard(7, 6));
        mainBoards.put("monthlyBestBoards", boardMapper.select_main_termBoard(30, 3));
        mainBoards.put("latestBoards", boardMapper.select_main_latestBoard());
        mainBoards.put("mostFollowedMember", boardMapper.select_main_profile());
        return mainBoards;
    }
    public int deleteComment(String email,int cno){

        return boardMapper.deleteComment(email,cno);
    }
}
