package com.usertask.service;

import java.util.List;

import com.usertask.model.Task;

public interface TaskService {
    Task findById(long id);
    
    List<Task> findByStatus(String status);
    
    Task findByDescription(String description);
    
    List<Task> findByUserID(long user_id);
    
    Task findByUserID(long user_id, long id);
     
    void saveTask(Task task);
     
    void updateTask(Task user);
     
    void deleteTaskById(long id);
 
    List<Task> findAllTasks();
     
    void deleteAllTasks();
     
    boolean isTaskExist(Task task);
    
    public Task findTaskByUserIDTaskID(long user_id, long id);
    public void deleteTaskByUserIDTaskID(Task task);
}
