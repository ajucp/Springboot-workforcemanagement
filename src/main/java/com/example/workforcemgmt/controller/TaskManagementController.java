package com.example.workforcemgmt.controller;

import com.example.workforcemgmt.common.model.response.Response;
import com.example.workforcemgmt.dto.*;
import com.example.workforcemgmt.service.TaskManagementService;
import com.example.workforcemgmt.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/task-mgmt")
public class TaskManagementController {
    
    private final TaskManagementService taskManagementService;

    public TaskManagementController(TaskManagementService taskManagementService){
        this.taskManagementService=taskManagementService;
    }

    @GetMapping("/{id}")
    public Response<TaskManagementDto> getTaskById(@PathVariable Long id){
        return new Response<>(taskManagementService.findTaskById(id));
    }

    @PostMapping("/create")
    public Response<List<TaskManagementDto>> createTasks(@RequestBody TaskCreateRequest request){
        return new Response<>(taskManagementService.createTasks(request));
    }


    @PostMapping("/update")
    public Response<List<TaskManagementDto>> updateTasks(@RequestBody UpdatetaskRequest request){
        return new Response<>(taskManagementService.updateTasks(request));
    }


    @PostMapping("/assign-by-ref")
    public Response<String> assignByReference(@RequestBody AssignByReferenceRequest request){
        return new Response<>(taskManagementService.assignByReference(request));
    }

    @PostMapping("/fetch-by-date/v2")
    public Response<List<TaskManagementDto>> fetchbyDate(@RequestBody TaskFetchByDateRequest request){
        return new Response<>(taskManagementService.fetchTasksByDate(request));
    }
    
   
    @PostMapping("/update-priority")
    public ResponseEntity<ApiResponse<String>> updatePriority(@RequestBody UpdatePriorityRequest request) {
        String message=taskManagementService.updateTaskPriority(request);
        
        return ResponseEntity.ok(ApiResponse.success(message));
    }
    
    @GetMapping("/priority/{priority}")
    public ResponseEntity<ApiResponse<List<TaskManagementDto>>> getByPriority(@PathVariable String priority) {
        List<TaskManagementDto> tasks = taskManagementService.getTasksByPriority(priority);
        return ResponseEntity.ok(ApiResponse.success(tasks));
}
    

    @PostMapping("/add-comment")
    public ResponseEntity<ApiResponse<String>> addComment(@RequestBody AddCommentRequest request) {
        String result=taskManagementService.addComment(request);
        return ResponseEntity.ok(ApiResponse.success(result));
    
    }
    
    

}
