package com.SpringWeb_Lombork.Taskmanager.tasks;

import com.SpringWeb_Lombork.Taskmanager.tasks.dto.CreateTaskDTO;
import com.SpringWeb_Lombork.Taskmanager.tasks.dto.UpdateTaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.SpringWeb_Lombork.Taskmanager.tasks.TasksService;

import java.util.Date;
import java.util.List;

//import static jdk.internal.org.jline.utils.ShutdownHooks.tasks;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    private final TasksService tasksService;

    public TasksController(TasksService tasksService){
        this.tasksService = tasksService;
    }
    @GetMapping("")
    ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(value = "beforeDate", required = false) Date beforeDate,
            @RequestParam(value = "afterDate", required = false) Date afterDate,
            @RequestParam(value = "completed", required = false) Boolean completed
    ) {


        var taskFilter = TasksService.TaskFilter.fromQueryParams(beforeDate, afterDate, false);
        //taskFilter.completed = completed != null ? completed : false;

        var tasks = tasksService.getAllTasks(taskFilter);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    ResponseEntity<Task> getTaskById(@PathVariable("id") Integer id){
        var task=tasksService.getTaskById(id);
        return ResponseEntity.ok(task);

    }

    @PostMapping("")
    ResponseEntity<Task> createTask(@RequestBody CreateTaskDTO createTaskDTO) {
        try {
            // checking for invalid name
            String name = createTaskDTO.getName();
            if (name.length() < 4 || name.length() > 100) {
                throw new IllegalArgumentException("Invalid name length. The name should be between 4 and 100 characters.");
            }

            // checking for invalid due date
            Date dueDate = createTaskDTO.getDueDate();
            /*if (dueDate != null && dueDate.before(new Date())) {
                throw new IllegalArgumentException("Invalid due date. The date cannot be in the past.");
            }

             */

            var task = tasksService.createTask(name, dueDate);
            return ResponseEntity.ok(task);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/{id}")
    ResponseEntity<Task>updateTask(@PathVariable("id")Integer id,@RequestBody UpdateTaskDTO updateTaskDTO){
        var task = tasksService.getTaskById(id);
        if (updateTaskDTO.isCompleted()) {
            task.setCompleted(updateTaskDTO.isCompleted());
        }

        try{
            if(updateTaskDTO.getDueDate()!=null && updateTaskDTO.getDueDate().before(new Date())){
                throw new IllegalArgumentException("Invalid due date" + updateTaskDTO.getDueDate() + " Date cannot be of past");            }
                task.setDueDate(updateTaskDTO.getDueDate());
                // Save the updated task
            task = tasksService.updateTask(task.getId(),task.getDueDate(),task.isCompleted());
            return ResponseEntity.ok(task);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void>deleteTask(@PathVariable("id") Integer id ){
        tasksService.deleteTask(id);
        return ResponseEntity.noContent().build();
        // noContent gives err#204 and notFound() would yield 404. Both can be used
    }
    // Exception handling


    @ExceptionHandler(TasksService.TaskNotFoundException.class)
    ResponseEntity<String>handleTaskNotFoundException(TasksService.TaskNotFoundException e){
        return ResponseEntity.notFound().build();
    }


}
