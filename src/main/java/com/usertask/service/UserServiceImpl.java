package com.usertask.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;

import com.usertask.data.UserRepository;
import com.usertask.model.User;
@EntityScan( basePackages = {"com.usertask"} )
@Service("userService")
public class UserServiceImpl implements UserService {
	private static final AtomicLong counter = new AtomicLong();
	@Autowired
	private UserRepository userRepository;
    
    private static List<User> users;
     
        
    /***
     * method returns a list from a recordset 
     * Uses a CRUDRepository method findAll()
     */
    public List<User> findAllUsers() {
    	users = new ArrayList<User>(); 
    	//uses CrudRepository method to find all instances of user in the database and 
    	//then iterates over each record to assign it to the list users. 
        userRepository.findAll().forEach(users::add); 
       return users;
    }
     
    /***
     * returns a user is present or else null
     * It uses uses CRUDRepository method findById and orElse(null)
     */
   	public User findById(long id) {
   		 return userRepository.findById(id).orElse(null);
    }
   	
    /***
     * Calls a findAllUsers() method to get a list of users.
     * Returns a user searched by username.
     */
    public User findByName(String name) {
    	users = findAllUsers(); 
        for(User user : users){
            if(user.getUsername().equalsIgnoreCase(name)){
                return user;
            }
        }
        return null;
    }
     
    /***
     *  takes a user instance as an argument
     *  persist it to a database using a CRUDRepository method save()
     */
    public void saveUser(User user) {
        user.setUser_id(counter.incrementAndGet());
        userRepository.save(user);
    }
    
    /***
     * takes a user instance as an argument
     * as in saveUser method update uses a CRUDRepository methods save()
     * If the id exists it updates otherwise it creates a new record
     */
    public void updateUser(User user) {
    	userRepository.save(user);
    }
 
    /***
     * tasks an id and uses CRUDRepository method deleteById()
     */
    public void deleteUserById(long id) {
         userRepository.deleteById(id);
    }
 
    /***
     * checks existance of a user instance by using CRUDRepository method existsById();
     */
    public boolean isUserExist(User user) {
        return userRepository.existsById(user.getUser_id());
    }
    
    /***
     * tasks an id and uses CRUDRepository method deleteAll()
     */
    public void deleteAllUsers(){
       userRepository.deleteAll();
    }
    
    
}
