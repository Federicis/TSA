package com.example.backend.Util.Mapper;

import com.example.backend.DTO.BotDTO;
import com.example.backend.model.BotModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BotMapper {

    @Mapping(target = "userId", source = "user.id")
    BotDTO toDTO(BotModel bot);

    @Mapping(target = "user.id", source = "userId")
    BotModel toModel(BotDTO bot);
}
