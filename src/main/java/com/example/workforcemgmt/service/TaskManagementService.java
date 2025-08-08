package com.example.workforcemgmt.service;

import java.util.List;

import com.example.workforcemgmt.dto.AddCommentRequest;
import com.example.workforcemgmt.dto.AssignByReferenceRequest;
import com.example.workforcemgmt.dto.TaskCreateRequest;
import com.example.workforcemgmt.dto.TaskFetchByDateRequest;
import com.example.workforcemgmt.dto.TaskManagementDto;
import com.example.workforcemgmt.dto.UpdatePriorityRequest;
import com.example.workforcemgmt.dto.UpdatetaskRequest;

public interface TaskManagementService {

    List<TaskManagementDto>createTasks(TaskCreateRequest request);
    List<TaskManagementDto>updateTasks(UpdatetaskRequest request);
    String assignByReference(AssignByReferenceRequest request);
    List<TaskManagementDto>fetchTasksByDate(TaskFetchByDateRequest request);
    TaskManagementDto findTaskById(Long id);
    String updateTaskPriority(UpdatePriorityRequest request);
    List<TaskManagementDto> getTasksByPriority(String priority);
    String addComment(AddCommentRequest request);


} 