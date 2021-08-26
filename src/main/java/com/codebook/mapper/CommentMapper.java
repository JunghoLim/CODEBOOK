package com.codebook.mapper;

import com.codebook.domain.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    List<CommentDTO> comment(int bno);
}
