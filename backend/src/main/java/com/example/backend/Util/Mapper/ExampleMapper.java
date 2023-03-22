package com.example.backend.Util.Mapper;

import com.example.backend.DTO.ExampleDTO;
import com.example.backend.model.ExampleModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExampleMapper {
    ExampleDTO toExampleDTO(ExampleModel message);
}
