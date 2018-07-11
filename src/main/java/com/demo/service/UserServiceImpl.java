package com.demo.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import com.demo.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static final AtomicLong counter = new AtomicLong();
    
    private static List<User> users;
     
    static{
        users= populateUsers();
    }
 
    public List<User> findAllUsers() {
        return users;
    }
     
   	public User findById(long id) {
        for(User user : users){
            if(user.getUser_id() == id){
                return user;
            }
        }
        return null;
    }
     
    public User findByName(String name) {
        for(User user : users){
            if(user.getUsername().equalsIgnoreCase(name)){
                return user;
            }
        }
        return null;
    }
     
    public void saveUser(User user) {
        user.setUser_id(counter.incrementAndGet());
        users.add(user);
    }
 
    public void updateUser(User user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }
 
    public void deleteUserById(long id) {
         
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getUser_id() == id) {
                iterator.remove();
            }
        }
    }
 
    public boolean isUserExist(User user) {
        return findByName(user.getUsername())!=null;
    }
     
    public void deleteAllUsers(){
        users.clear();
    }
 
    private static List<User> populateUsers(){
    	List<User> users = new ArrayList<User>();
        users.add(new User(counter.incrementAndGet(),"Sabre","Zenzo", "Didi"));
        users.add(new User(counter.incrementAndGet(),"Mankiri", "Nothabo", "Didi"));
        users.add(new User(counter.incrementAndGet(),"JSmith","John", "Smith"));
        users.add(new User(counter.incrementAndGet(),"VDidi","Vuyolwethu", "Didi"));
      //  users.add(new User(counter.incrementAndGet(),"Niel",33, 70000));
        return users;
    }
    
}
