package com.gamestudio.game.dto;

import lombok.Data;

@Data
public class ScoreDTO {
    private Long id;
    private Long userId;
    private int points;
    private String result; // "WIN", "LOSE", "DRAW"
}