package com.gamestudio.game.mapper;

import com.gamestudio.game.dto.ScoreDTO;
import com.gamestudio.game.entity.Score;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoreMapper {
    ScoreDTO toDTO(Score score);
    Score toEntity(ScoreDTO scoreDTO);
}