package com.SpringWeb_Lombork.Taskmanager.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
@Setter
@ToString
public class Task {
    private Integer id;
    private String name;
    private Date dueDate;
    private boolean completed;


}
