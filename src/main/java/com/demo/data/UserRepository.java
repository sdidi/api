package com.demo.data;

import org.springframework.data.repository.CrudRepository;

import com.demo.model.User;;

public interface UserRepository extends CrudRepository<User,Long> {

}
