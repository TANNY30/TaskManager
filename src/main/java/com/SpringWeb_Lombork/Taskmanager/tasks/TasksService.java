package com.SpringWeb_Lombork.Taskmanager.tasks;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TasksService {
    private ArrayList<Task> tasks = new ArrayList<>();
    private Integer id=0;
    public List<Task>getAllTasks(TaskFilter taskFilter ){
        if (taskFilter==null){
            return tasks;
        }
        else {
            var filteredTasks = tasks.stream().filter(task -> {
                if (taskFilter.beforeDate != null && task.getDueDate().after(taskFilter.beforeDate)) {
                    return false;
                }
                if (taskFilter.afterDate != null && task.getDueDate().before(taskFilter.afterDate)) {
                    return false;
                }
                if (taskFilter.completed  && task.isCompleted() != taskFilter.completed) {
                    return false;
                }
                return true;
            }).toList();
            return filteredTasks;
        }
    }
    public Task getTaskById(Integer id){
        for(Task task:tasks){
            if(task.getId().equals(id)){
                return task;
            }
        }
        throw new TaskNotFoundException(id);
    }

    public Task createTask (String name, Date dueDate){
        if (name.length() < 4 || name.length() > 100) {
            throw new IllegalArgumentException("Invalid name length. The name should be between 4 and 100 characters.");
        }
        /*if(dueDate!=null && dueDate.before(new Date())){
            throw new IllegalArgumentException("Invalid due date"+dueDate+" Date cannot be of past");
        }
        */
        Task task = new Task(id++,name,dueDate,false);
        tasks.add(task);

        return task;
    }

    public Task updateTask(Integer id,Date dueDate,boolean completed){

        Task task = getTaskById(id);
        if(dueDate!=null && dueDate.before(new Date())){
            throw new IllegalArgumentException("Invalid due date"+dueDate+" Date cannot be of past");
        }

        if (dueDate!=null){
            task.setDueDate(dueDate);
        }
        if(completed){
            task.setCompleted(completed);
        }
        return task;
    }

    public void deleteTask(Integer id){
        Task task = getTaskById(id);
        tasks.remove(task);
        }
    public static class TaskNotFoundException extends IllegalStateException {
        public TaskNotFoundException(Integer id) {
            super("Task with id" + id + " not found");

        }
    }

    static class TaskFilter{
        Date beforeDate;
        Date afterDate;
        boolean completed;
        public static TaskFilter fromQueryParams(Date beforeDate,Date afterDate,boolean completed){
            TaskFilter taskFilter = new TaskFilter();
            taskFilter.beforeDate=beforeDate;
            taskFilter.afterDate=afterDate;
            taskFilter.completed=completed;
            return taskFilter;
        }
    }






}
