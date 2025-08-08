package com.example.workforcemgmt.dto;



import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.example.workforcemgmt.common.model.enums.ReferenceType;
import com.example.workforcemgmt.model.enums.Task;
import com.example.workforcemgmt.model.enums.Priority;
import java.util.List;

import lombok.Data;

public class TaskCreateRequest {
    
    private List<RequestItem>requests;
    
     public List<RequestItem> getRequests() {
        return requests;
    }


    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RequestItem{
        private Long referenceId;
        private ReferenceType referenceType;
        private Task task;
        private Long assigneeId;
        private Long taskDeadlineTime;
        private Priority priority;

    }
}
