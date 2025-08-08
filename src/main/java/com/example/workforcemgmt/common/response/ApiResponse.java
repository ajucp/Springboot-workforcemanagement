package com.example.workforcemgmt.common.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private T data;
    private Status status;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, new Status(200, "Success"));
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, new Status(500, message));
    }

    @Data
    @AllArgsConstructor
    public static class Status {
        private int code;
        private String message;
    }
}

