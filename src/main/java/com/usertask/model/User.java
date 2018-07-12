package com.usertask.model;

import javax.persistence.*;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
@Entity
@Table(name = "user")
public class User {
	//@Column(name="user_id", unique=true, updatable=false, nullable=false)
	@Id @GeneratedValue
	private Long user_id;
    
	//@Column(name="username", unique=true, updatable=false, nullable=false)
    private String username;
    
	//@Column(name="firstName", unique=false, updatable=true, nullable=false)
    private String firstname;
	
	//@Column(name="lastName", unique=false, updatable=true, nullable=false)
    private String lastname;
    
    public User(){
        user_id=new Long(0);
    }
     
    public User(Long user_id, String username, String firstname, String lastname){
        this.user_id = user_id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }
     
    public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (user_id ^ (user_id >>> 32));
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (user_id != other.user_id)
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "User [user_id=" + user_id + ", username=" + username+ ", firstname=" + firstname + ", lastname=" + lastname + "]";
    }

}
