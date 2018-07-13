package com.usertask.service;

import java.util.List;

import com.usertask.model.Task;

public interface TaskService {
    Task findById(long id);
    
    Task findByDescription(String description);
    
    List<Task> findByUserID(long id);
     
    void saveTask(Task task);
     
    void updateTask(Task user);
     
    void deleteTaskById(long id);
 
    List<Task> findAllTasks();
     
    void deleteAllTasks();
     
    boolean isTaskExist(Task task);
}
