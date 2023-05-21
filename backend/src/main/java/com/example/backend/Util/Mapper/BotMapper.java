package com.example.backend.Util.Mapper;

import com.example.backend.DTO.BotDTO;
import com.example.backend.model.BotModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BotMapper {
    BotDTO toDTO(BotModel bot);
    BotModel toModel(BotDTO bot);
}
