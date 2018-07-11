package com.demo.service;

import java.util.List;

import com.demo.model.Task;

public interface TaskService {
    Task findById(long id);
    
    Task findByDescription(String description);
     
    void saveTask(Task task);
     
    void updateTask(Task user);
     
    void deleteTaskById(long id);
 
    List<Task> findAllTasks();
     
    void deleteAllTasks();
     
    boolean isTaskExist(Task task);
}
