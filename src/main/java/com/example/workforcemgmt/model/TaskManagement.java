package com.example.workforcemgmt.model;



import java.util.ArrayList;
import java.util.List;

import com.example.workforcemgmt.common.model.enums.ReferenceType;
import com.example.workforcemgmt.model.enums.Priority;
import com.example.workforcemgmt.model.enums.Task;
import com.example.workforcemgmt.model.enums.TaskStatus;

import lombok.Data;

@Data
public class TaskManagement {
    public Long getCreatedTime() {
    return createdTime;
}

public void setCreatedTime(Long createdTime) {
    this.createdTime = createdTime;
}
    private Long id;
    private Long referenceId;
    private ReferenceType referenceType;
    private Task task;
    private String description;
    private TaskStatus status;
    private Long assigneeId;
    private Long taskDeadlineTime;
    private Priority priority;

    private Long startTime;
    private Long createdTime;
    private List<String> comments = new ArrayList<>();
    private List<String> activityHistory = new ArrayList<>();

    
}
