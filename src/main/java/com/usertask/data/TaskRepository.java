package com.usertask.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.usertask.model.Task;

public interface TaskRepository  extends CrudRepository<Task,Long> {

}
