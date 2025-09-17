package com.gamestudio.game.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gamestudio.game.dto.CommentDTO;
import com.gamestudio.game.entity.Comment;
import com.gamestudio.game.mapper.CommentMapper;
import com.gamestudio.game.repository.CommentRepository;
import com.gamestudio.game.service.CommentService;
import com.gamestudio.game.service.UserValidationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private CommentMapper commentMapper;

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(commentRepository, commentMapper, userValidationService);

        // Мокаем SecurityContext
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(1L); // userId
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testSaveComment() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("Test comment");

        Comment commentEntity = new Comment();
        commentEntity.setText("Test comment");

        Comment savedEntity = new Comment();
        savedEntity.setId(1L);
        savedEntity.setUserId(1L);
        savedEntity.setText("Test comment");

        CommentDTO savedDTO = new CommentDTO();
        savedDTO.setId(1L);
        savedDTO.setUserId(1L);
        savedDTO.setText("Test comment");

        when(commentMapper.toEntity(commentDTO)).thenReturn(commentEntity);
        when(userValidationService.userExists(1L)).thenReturn(true);
        when(commentRepository.save(commentEntity)).thenReturn(savedEntity);
        when(commentMapper.toDTO(savedEntity)).thenReturn(savedDTO);

        CommentDTO result = commentService.saveComment(commentDTO);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals("Test comment", result.getText());
    }
}