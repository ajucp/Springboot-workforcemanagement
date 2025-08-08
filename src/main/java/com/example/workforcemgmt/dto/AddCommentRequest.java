package com.example.workforcemgmt.dto;



import lombok.Data;

@Data
public class AddCommentRequest {
    private Long taskId;
    private String comment;
}
