package com.gamestudio.game.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; // id игрока из user-service

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private String result; // "WIN", "LOSE", "DRAW"
}