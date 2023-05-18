package com.example.backend.Util.Mapper;

import com.example.backend.DTO.BotDTO;
import com.example.backend.model.BotModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BotMapper {
    @Mapping(target = "username", source = "user.username")
    BotDTO toDTO(BotModel bot);
    @Mapping(target = "user.username", source = "username")
    BotModel toModel(BotDTO bot);
}
