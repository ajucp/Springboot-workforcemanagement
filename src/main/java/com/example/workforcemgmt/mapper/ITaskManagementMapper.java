package com.example.workforcemgmt.mapper;

import org.mapstruct.Mapper;
import java.util.List;
import com.example.workforcemgmt.dto.TaskManagementDto;
import com.example.workforcemgmt.model.TaskManagement;


@Mapper(componentModel = "spring")
public interface ITaskManagementMapper {

    TaskManagementDto modelToDto(TaskManagement task);
    List<TaskManagementDto>modelListToDtoList(List<TaskManagement>tasks);
} 