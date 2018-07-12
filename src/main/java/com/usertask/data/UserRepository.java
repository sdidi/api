package com.usertask.data;

import org.springframework.data.repository.CrudRepository;

import com.usertask.model.User;;

public interface UserRepository extends CrudRepository<User,Long> {

}
