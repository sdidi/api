package com.usertask.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.domain.EntityScan;
@EntityScan
@Entity
@Table(name = "Task")
public class Task implements Serializable{
	@Id @GeneratedValue
	private long task_id;
	
	@JoinColumn
	 private long user_id;
	 
	 private String status;
	 
	 private String name;
	 
	 private String description;
	 
	 private Date due_date;
	
	 private Date assign_date;
	 
	 public Task() {
		 task_id = 0;
	 }
	 
	 public Task(long id, String status, String description, String assign_date, String due_date) {
		 Date date1 =null , date2 = null;  
		 try {
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date1= sdf1.parse(assign_date);
			date2 = sdf1.parse(due_date);
		  }catch(Exception e) {
			  System.err.println("Incorrect date format");
		  }
		 this.task_id = id;
		 this.status = status;
		 this.description = description;
		 this.assign_date = date1;
		 this.due_date = date2;
	 }
	
	 public long getUser_id() {
			return user_id;
		}
		public void setUser_id(long user_id) {
			this.user_id = user_id;}
		
	 public Date getDue_date() {		 
		return due_date;
	}

	public void setDue_date(String due_date) {
		Date date1 =null;  
		 try {
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date1= sdf1.parse(due_date);
			}catch(Exception e) {
			  System.err.println("Incorrect date format");
		  }
		
		this.due_date = date1;
	}

	public Date getAssign_date() {
		return assign_date;
	}

	public void setAssign_date(String assign_date) {
		Date date1 =null;  
		 try {
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			date1= sdf1.parse(assign_date);
			}catch(Exception e) {
			  System.err.println("Incorrect date format");
		  }
		this.assign_date = date1;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getTask_id() {
		return task_id;
	}
	public void setTask_id(long task_id) {
		this.task_id = task_id;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	@Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + (int) (task_id ^ (task_id >>> 32));
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
	        Task other = (Task) obj;
	        if (task_id != other.task_id)
	            return false;
	        return true;
	    }
	 
	    @Override
	    public String toString() {
	        return "User [id=" + task_id + ", user_id= " + user_id + ", description=" + description + ", date assigned=" + assign_date
	                + ", Date Due=" + due_date + "status"+status+"]";
	    }

}
