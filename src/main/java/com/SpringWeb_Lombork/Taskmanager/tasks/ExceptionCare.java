package com.SpringWeb_Lombork.Taskmanager.tasks;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.SpringWeb_Lombork.Taskmanager.tasks.TasksService.*;
@ControllerAdvice
public class ExceptionCare extends ResponseEntityExceptionHandler {
    /*
    All exceptions handled
     */
    @ExceptionHandler(
            {
                    RuntimeException.class,
                    IllegalArgumentException.class,
                    TaskNotFoundException.class
            }
    )
    String multiExceptionHandler(Exception e){
        if(e instanceof IllegalArgumentException){
            return "Illegal Args";
        } else if (e instanceof RuntimeException) {
            return "Something went wrong at Run time";
        } else if (e instanceof TaskNotFoundException) {
            return "Tasks you are looking for Doesn't exist";
        } else{
            return " I don't know what went wrong. It ain't runtime/illegal arg/task not found!";
        }

    }
}
