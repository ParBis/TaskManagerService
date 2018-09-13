package com.cts.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name="findPTById", query="select e from ParentTask e where e.parentId=:parentId")
@NamedQuery(name="findParentTasks", query="from ParentTask e")
public class ParentTask {
	
	public ParentTask(){}

	public ParentTask(String parentTask) {
		super();
		this.parentTask = parentTask;
	}

	@Override
	public String toString() {
		return "ParentTask [parentId=" + parentId + ", parentTask=" + parentTask + "]";
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int parentId;
	String parentTask;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="parentTask")
    /*@JoinTable(
            name = "PARENTTASK_TASK",
            joinColumns = @JoinColumn(name = "parentId"),
            inverseJoinColumns = @JoinColumn(name = "taskId")
    )*/
	Set<Task> tasks;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParentTask() {
		return parentTask;
	}

	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	
	
}
