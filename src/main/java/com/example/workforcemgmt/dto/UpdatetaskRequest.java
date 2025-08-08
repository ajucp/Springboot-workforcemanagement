package com.example.workforcemgmt.dto;

import com.example.workforcemgmt.model.enums.TaskStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import java.util.List;

public class UpdatetaskRequest {

    private List<RequestItem>requests;

    public List<RequestItem> getRequests() {
        return requests;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RequestItem{
        private Long taskId;
        private TaskStatus taskStatus;
        private String description;

        // public Long getTaskId() { return taskId; }
        // public TaskStatus getTaskStatus() { return taskStatus; }
        // public String getDescription() { return description; }
    }
    
}
