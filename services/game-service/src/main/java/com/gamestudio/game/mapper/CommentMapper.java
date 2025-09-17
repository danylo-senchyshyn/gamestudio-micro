package com.gamestudio.game.mapper;

import com.gamestudio.game.dto.CommentDTO;
import com.gamestudio.game.entity.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDTO toDTO(Comment comment);

    Comment toEntity(CommentDTO commentDTO);

    List<CommentDTO> toDTOList(List<Comment> comments);
}