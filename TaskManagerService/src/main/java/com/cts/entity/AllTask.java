package com.cts.entity;

public class AllTask {
	
	public AllTask(){}

	int taskId;

	int parentId;
	
	String task;
	
	String stStartDate;
	
	String stEndDate;
	
	int priority;
	
	String parentTask;
	
	int taskStatus; // 0 - Inactive, 1 - Active


	
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}


	/**
	 * @return the stStartDate
	 */
	public String getStStartDate() {
		return stStartDate;
	}

	/**
	 * @param stStartDate the stStartDate to set
	 */
	public void setStStartDate(String stStartDate) {
		this.stStartDate = stStartDate;
	}

	/**
	 * @return the stEndDate
	 */
	public String getStEndDate() {
		return stEndDate;
	}

	/**
	 * @param stEndDate the stEndDate to set
	 */
	public void setStEndDate(String stEndDate) {
		this.stEndDate = stEndDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the taskStatus
	 */
	public int getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus the taskStatus to set
	 */
	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getParentTask() {
		return parentTask;
	}

	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", parentId=" + parentId + ", task=" + task + ", stStartDate=" + stStartDate
				+ ", stEndDate=" + stEndDate + ", priority=" + priority + ", parentTask=" + parentTask + ", taskStatus=" + taskStatus +"]";
	}

	public AllTask(int parentId, String task, String stStartDate, String stEndDate, int priority, String parentTask,
			int taskStatus) {
		super();
		this.parentId = parentId;
		this.task = task;
		this.stStartDate = stStartDate;
		this.stEndDate = stEndDate;
		this.priority = priority;
		this.parentTask = parentTask;
		this.taskStatus = taskStatus;
	}

	
	/////////////////
	


	/*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "parentId", insertable = false, updatable = false)
	private ParentTask parentTask;

	public ParentTask getParentTask() {
		return parentTask;
	}

	public void setParentTask(ParentTask parentTask) {
		this.parentTask = parentTask;
	}*/

	
	
}
