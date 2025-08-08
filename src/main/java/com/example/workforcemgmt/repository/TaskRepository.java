package com.example.workforcemgmt.repository;


import java.util.Optional;
import com.example.workforcemgmt.model.TaskManagement;
import com.example.workforcemgmt.common.model.enums.ReferenceType;

import java.util.List;



public interface TaskRepository {
    Optional<TaskManagement>findById(Long id);
    TaskManagement save(TaskManagement task);
    List<TaskManagement> findAll();
    List<TaskManagement> findByReferenceIdAndReferenceType(Long referenceId,ReferenceType referenceType);
    List<TaskManagement> findByAssigneeIdInAndStartTimeBetween(List<Long> assigneeIds,Long startTime, Long endTime);
    List<TaskManagement> findByAssigneeIdIn(List<Long> assigneeIds);
    
} 
