package com.gamestudio.game.controller;

import com.gamestudio.game.dto.ScoreDTO;
import com.gamestudio.game.service.ScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping
    public List<ScoreDTO> getAllScores() {
        return scoreService.getAllScores();
    }

    @GetMapping("/user/{userId}")
    public List<ScoreDTO> getScoresByUser(@PathVariable Long userId) {
        return scoreService.getScoresByUser(userId);
    }

    @PostMapping
    public ScoreDTO addScore(@RequestBody ScoreDTO scoreDTO) {
        return scoreService.saveScore(scoreDTO);
    }
}