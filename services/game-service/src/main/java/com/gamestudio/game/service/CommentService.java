package com.gamestudio.game.service;

import com.gamestudio.game.dto.CommentDTO;
import com.gamestudio.game.entity.Comment;
import com.gamestudio.game.mapper.CommentMapper;
import com.gamestudio.game.repository.CommentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserValidationService userValidationService;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper,
                          UserValidationService userValidationService) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userValidationService = userValidationService;
    }

    public CommentDTO saveComment(CommentDTO commentDTO) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userValidationService.userExists(userId)) {
            throw new IllegalArgumentException("Пользователь с id " + userId + " не найден!");
        }

        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setUserId(userId);

        return commentMapper.toDTO(commentRepository.save(comment));
    }

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByUser(Long userId) {
        return commentRepository.findByUserId(userId)
                .stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }
}