package com.demo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.demo.model.Task;

public interface TaskRepository  extends CrudRepository<Task,Long> {

}
