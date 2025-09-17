package com.gamestudio.game.service;

import com.gamestudio.game.dto.ScoreDTO;
import com.gamestudio.game.entity.Score;
import com.gamestudio.game.mapper.ScoreMapper;
import com.gamestudio.game.repository.ScoreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private ScoreMapper scoreMapper;

    @InjectMocks
    private ScoreService scoreService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создаём моки для SecurityContext
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(1L); // например, ID пользователя
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testSaveScore() {
        // DTO, который приходит в сервис
        ScoreDTO scoreDTO = new ScoreDTO();
        scoreDTO.setUserId(1L);
        scoreDTO.setPoints(100);
        scoreDTO.setResult("WIN");

        // Сущность, в которую маппер преобразует DTO
        Score scoreEntity = new Score();
        scoreEntity.setUserId(1L);
        scoreEntity.setPoints(100);
        scoreEntity.setResult("WIN");

        // Моки для всех зависимостей
        when(userValidationService.userExists(1L)).thenReturn(true);
        when(scoreMapper.toEntity(scoreDTO)).thenReturn(scoreEntity);
        when(scoreRepository.save(scoreEntity)).thenReturn(scoreEntity);
        when(scoreMapper.toDTO(scoreEntity)).thenReturn(scoreDTO);

        // Вызов метода сервиса
        ScoreDTO saved = scoreService.saveScore(scoreDTO);

        // Проверки
        assertNotNull(saved);
        assertEquals(100, saved.getPoints());
        assertEquals("WIN", saved.getResult());
        verify(scoreRepository, times(1)).save(scoreEntity);
    }

    @Test
    void testGetScoresByUser() {
        Score score = new Score();
        score.setUserId(1L);
        score.setPoints(50);
        score.setResult("LOSE");

        when(scoreRepository.findByUserId(1L)).thenReturn(List.of(score));
        when(scoreMapper.toDTO(score)).thenReturn(new ScoreDTO());

        List<ScoreDTO> scores = scoreService.getScoresByUser(1L);

        assertEquals(1, scores.size());
        verify(scoreRepository, times(1)).findByUserId(1L);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }
}