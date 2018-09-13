package com.cts.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQuery(name="findTasks", query="from Task e")
@NamedQuery(name="findById", query="select e from Task e where e.taskId=:taskId")
public class Task {
	
	public Task(){}

	public Task(/*int parentId, */String task, Date startDate, Date endDate, int priority) {
		super();
		//this.parentId = parentId;
		this.task = task;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + /*", parentId=" + parentId +*/ ", task=" + task + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", priority=" + priority + "]";
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int taskId;
	//int parentId;
	String task;
	Date startDate;
	Date endDate;
	int priority;

	@ManyToOne
	@JsonIgnore
	/*@JoinTable(
            name = "PARENTTASK_TASK",
            joinColumns = @JoinColumn(name = "taskId"),
            inverseJoinColumns = @JoinColumn(name = "parentId")
    )*/
	ParentTask parentTask;
	
	
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	/*public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}*/

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ParentTask getParentTask() {
		return parentTask;
	}

	public void setParentTask(ParentTask parentTask) {
		this.parentTask = parentTask;
	}
	



	
	
	
}
