package com.gamestudio.game.controller;

import com.gamestudio.game.entity.Score;
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

    @PostMapping
    public Score createScore(@RequestBody Score score) {
        return scoreService.saveScore(score);
    }

    @GetMapping
    public List<Score> getAllScores() {
        return scoreService.getAllScores();
    }
}