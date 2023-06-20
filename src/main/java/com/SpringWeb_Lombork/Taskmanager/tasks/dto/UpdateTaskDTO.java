package com.SpringWeb_Lombork.Taskmanager.tasks.dto;

import lombok.*;

import java.util.Date;

@Data
@Setter
public class UpdateTaskDTO {
    @Getter
    boolean completed=false;
    @Getter
    Date dueDate;

}


