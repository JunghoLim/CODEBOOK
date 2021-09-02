package com.codebook.service;
import com.codebook.domain.BoardDTO;
import com.codebook.domain.CommentDTO;
import com.codebook.domain.UploadImgFileDTO;
import com.codebook.handler.AuthenticationHandler;
import com.codebook.handler.ImgFileHandler;
import com.codebook.mapper.BoardMapper;
import com.codebook.mapper.CommentMapper;
import com.codebook.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;
    private final CommentMapper commentMapper;
    private final ImgFileHandler imgFileHandler;
    private final AuthenticationHandler authenticationHandler;

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

    public int updateComment(int cno , String comment){

        return boardMapper.updateComment(cno,comment);
    }

    public String uploadImgFile(HttpServletRequest req, HttpServletResponse res, MultipartFile file) throws IOException {
        Authentication auth = authenticationHandler.getAuth(req, res);
        UserDetailsImpl member = (UserDetailsImpl) auth.getPrincipal();
        UploadImgFileDTO uploadImgFile = imgFileHandler.parseImgFile(member.getUsername(),file);
        if(boardMapper.insert_imgfile(uploadImgFile) == 1){
            return uploadImgFile.getStoredFilePath();
        }else{
            return null;
        }
    }

    public void writeBoard(BoardDTO boardDTO){
        if(StringUtils.isEmpty(boardDTO.getMainPicture())){
            boardDTO.setMainPicture("no-image-codebook-23485.jpg");
        }
        boardMapper.insert_board(boardDTO);
    }

    public void increaseBoardViews(int bno){
        boardMapper.update_views(bno);
    }

    public int verificateRecommend(String email,int cno,int bno){
        return boardMapper.verificateEmail(email,cno,bno);
    }
    public int deleteRecommend(String email,int cno,int bno){
        return boardMapper.deleteRecommend(email,cno,bno);
    }
    public int insertRecommend(String email,int cno,int bno){
        return boardMapper.insertRecommend(email,cno,bno);
    }
    public int countRecommend(int bno){
        return boardMapper.countRecommend(bno);
    }
    public void updateRecommend(int bno,int countRecommend){
         boardMapper.updateRecommend(bno,countRecommend);
    }
}
