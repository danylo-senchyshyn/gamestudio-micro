package com.gamestudio.game.service;

import com.gamestudio.game.entity.Score;
import com.gamestudio.game.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public Score saveScore(Score score) {
        return scoreRepository.save(score);
    }

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }
}