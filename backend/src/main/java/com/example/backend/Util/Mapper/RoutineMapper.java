package com.example.backend.Util.Mapper;

import com.example.backend.DTO.RoutineDTO;
import com.example.backend.model.RoutineModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface RoutineMapper {
    @Mapping(target = "tasks", source = "tasks", qualifiedByName = "toTask")
    @Mapping(target = "bot.id", source = "botId")
    RoutineModel toRoutineModel(RoutineDTO routineDTO);
    RoutineDTO toRoutineDTO(RoutineModel routineModel);
}
