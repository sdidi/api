package com.demo.model;
import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
@Transactional
public interface UserD extends CrudRepository<User, Long> {
	
}