package com.example.workforcemgmt.common.exception;

import lombok.Getter;

@Getter
public enum StatusCode {

    SUCCESS(200,"Success"),
    BAD_REQUEST(400,"BAD Request"),
    NOT_FOUND(404,"Resource Not Found"),
    INTERNAL_SERVER_ERROR(500,"Internal server Error");

    private final int code;
    private final String message;

    StatusCode(int code,String message){
        this.code=code;
        this.message=message;
    }

    // public static final String SUCCESS = null;
    
}
