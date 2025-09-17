package com.gamestudio.game.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long userId;
    private String text;
}