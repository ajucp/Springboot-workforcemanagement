package com.example.workforcemgmt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.workforcemgmt.common.exception.ResourceNotFoundException;
import com.example.workforcemgmt.dto.*;
import com.example.workforcemgmt.mapper.ITaskManagementMapper;
import com.example.workforcemgmt.model.TaskManagement;
import com.example.workforcemgmt.model.enums.Task;
import com.example.workforcemgmt.model.enums.TaskStatus;
import com.example.workforcemgmt.repository.TaskRepository;
import com.example.workforcemgmt.service.TaskManagementService;




@Service
public class TaskManagementServiceImpl implements TaskManagementService{
    
    private final TaskRepository taskRepository;
    private final ITaskManagementMapper taskMapper;

    public TaskManagementServiceImpl(TaskRepository taskRepository,ITaskManagementMapper taskMapper){
        this.taskRepository=taskRepository;
        this.taskMapper=taskMapper;

    }

    @Override
    public TaskManagementDto findTaskById(Long id){
        TaskManagement task=taskRepository.findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("Task Not Found!! with Id: "+id));
        return taskMapper.modelToDto(task);
    }

    @Override
    public List<TaskManagementDto>createTasks(TaskCreateRequest createRequest){
        List<TaskManagement>createdTasks=new ArrayList<>();
        for(TaskCreateRequest.RequestItem item:createRequest.getRequests()){
            TaskManagement newTask=new TaskManagement();
            newTask.setReferenceId(item.getReferenceId());
            newTask.setReferenceType(item.getReferenceType());
            newTask.setTask(item.getTask());
            newTask.setAssigneeId(item.getAssigneeId());
            newTask.setPriority(item.getPriority());
            newTask.setTaskDeadlineTime(item.getTaskDeadlineTime());
            newTask.setStatus(TaskStatus.ASSIGNED);
            newTask.setDescription("New Task Created");
            newTask.setStartTime(System.currentTimeMillis());
            newTask.setCreatedTime(System.currentTimeMillis());
            newTask.getActivityHistory().add("Task created at " + System.currentTimeMillis());

            createdTasks.add(taskRepository.save(newTask));


        }

        return taskMapper.modelListToDtoList(createdTasks);
    }

    @Override
    public List<TaskManagementDto>updateTasks(UpdatetaskRequest updateRequest){
        List<TaskManagement>updatedTasks=new ArrayList<>();
        for(UpdatetaskRequest.RequestItem item:updateRequest.getRequests()){
            TaskManagement task=taskRepository.findById(item.getTaskId())
                .orElseThrow(()->new ResourceNotFoundException("Task Not Found with Id: "+item.getTaskId()));


            if(item.getTaskStatus() !=null){
                task.setStatus(item.getTaskStatus());
            }

            if(item.getDescription() !=null){
                task.setDescription(item.getDescription());
            }

            updatedTasks.add(taskRepository.save(task));

        }
        return taskMapper.modelListToDtoList(updatedTasks);
    }

    @Override
    public String assignByReference(AssignByReferenceRequest request){
        List<Task> applicableTasks=Task.getTaskByReferenceType(request.getReferenceType());

        List<TaskManagement> existingTasks=taskRepository.findByReferenceIdAndReferenceType(request.getReferenceId(), request.getReferenceType());

        for(Task taskType:applicableTasks){
            List<TaskManagement>taskOfType=existingTasks.stream()
                            .filter(t->t.getTask()==taskType&&t.getStatus()!=TaskStatus.COMPLETED).collect(Collectors.toList());

            // BUG #1 (to fix later): reassigns ALL instead of canceling old ones
            
            if(!taskOfType.isEmpty()){
                //change the previous as cancelled
                for(TaskManagement oldTask:taskOfType){
                    oldTask.setStatus(TaskStatus.CANCELLED);
                    oldTask.setDescription("Task Reassigned and this one is Canccelled!!");
                    taskRepository.save(oldTask);
                }
            }

            //create a new task for the new assignee
            
            TaskManagement newTask=new TaskManagement();
            newTask.setReferenceId(request.getReferenceId());
            newTask.setReferenceType(request.getReferenceType());
            newTask.setTask(taskType);
            newTask.setAssigneeId(request.getAssigneeId());
            newTask.setStatus(TaskStatus.ASSIGNED);
            newTask.setDescription("New Task reassigned to new user");
            newTask.setStartTime(System.currentTimeMillis());
            newTask.setCreatedTime(System.currentTimeMillis());
            newTask.getActivityHistory().add("Task reassigned to user " + request.getAssigneeId() + " at " + System.currentTimeMillis());
            taskRepository.save(newTask);
            
        }
        return "Tasks Assigned Successfully For Reference : "+request.getReferenceId();
    }

    @Override
    public List<TaskManagementDto> fetchTasksByDate(TaskFetchByDateRequest request){
        List<TaskManagement> tasks=taskRepository.findAll();

        // BUG #2 (to fix later): does not filter CANCELLED tasks or by date
        List<TaskManagement>filteredTasks=tasks.stream()
                    //featuring the fetch date
                    .filter(task -> request.getAssigneeIds().contains(task.getAssigneeId()))
                    .filter(task->task.getStatus()!=TaskStatus.CANCELLED)
                    .filter(task -> task.getCreatedTime() != null)
                    .filter(task->
                    
                        //Task Starts in the range
                        (task.getCreatedTime() >=request.getStartTime()&& task.getCreatedTime()<=request.getEndTime())
                        ||
                        //or task started before starttime but is still Active
                        (task.getCreatedTime()<request.getStartTime() && task.getStatus()!=TaskStatus.COMPLETED)
                    )
                    .collect(Collectors.toList());

        return taskMapper.modelListToDtoList(filteredTasks);

    }

    @Override
    public String updateTaskPriority(UpdatePriorityRequest request){
        TaskManagement task=taskRepository.findById(request.getTaskId()).orElseThrow(()->new ResourceNotFoundException("Task Not Found with Id: "+request.getTaskId()));

        task.setPriority(request.getNewPriority());
        task.setDescription("Priority updated to " + request.getNewPriority());
        task.getActivityHistory().add("Priority changed to " + request.getNewPriority() + " at " + System.currentTimeMillis());


    taskRepository.save(task);

        return "Task priority updated successfully!";
    }

    @Override
    public List<TaskManagementDto> getTasksByPriority(String priority) {
    List<TaskManagement> allTasks = taskRepository.findAll();

    return allTasks.stream()
            .filter(task -> task.getPriority().name().equalsIgnoreCase(priority))
            .map(taskMapper::modelToDto)
            .collect(Collectors.toList());
    }

    @Override
    public String addComment(AddCommentRequest request) {
        TaskManagement task = taskRepository.findById(request.getTaskId())
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.getComments().add(request.getComment());
        task.getActivityHistory().add("Comment added at " + System.currentTimeMillis());

        taskRepository.save(task);

        return "Comment added successfully!";
    }   



}
