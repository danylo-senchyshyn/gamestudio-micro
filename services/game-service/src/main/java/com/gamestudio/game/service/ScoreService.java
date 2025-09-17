package com.gamestudio.game.service;

import com.gamestudio.game.dto.ScoreDTO;
import com.gamestudio.game.entity.Score;
import com.gamestudio.game.mapper.ScoreMapper;
import com.gamestudio.game.repository.ScoreRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserValidationService userValidationService;
    private final ScoreMapper scoreMapper;

    public ScoreService(ScoreRepository scoreRepository,
                        UserValidationService userValidationService,
                        ScoreMapper scoreMapper) {
        this.scoreRepository = scoreRepository;
        this.userValidationService = userValidationService;
        this.scoreMapper = scoreMapper;
    }

    public ScoreDTO saveScore(ScoreDTO scoreDTO) {
        // Берём userId из токена
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userValidationService.userExists(userId)) {
            throw new IllegalArgumentException("Пользователь с id " + userId + " не найден!");
        }

        Score score = scoreMapper.toEntity(scoreDTO);
        score.setUserId(userId);
        Score saved = scoreRepository.save(score);
        return scoreMapper.toDTO(saved);
    }

    public List<ScoreDTO> getScoresByUser(Long userId) {
        return scoreRepository.findByUserId(userId).stream()
                .map(scoreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ScoreDTO> getAllScores() {
        return scoreRepository.findAll().stream()
                .map(scoreMapper::toDTO)
                .collect(Collectors.toList());
    }
}