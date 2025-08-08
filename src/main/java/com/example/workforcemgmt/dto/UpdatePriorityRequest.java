package com.example.workforcemgmt.dto;



import com.example.workforcemgmt.model.enums.Priority;

import lombok.Data;

@Data
public class UpdatePriorityRequest {
    private Long taskId;
    private Priority newPriority;
}
