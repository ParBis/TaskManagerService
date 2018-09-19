package com.cts.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.AllTask;
import com.cts.entity.ParentTask;
import com.cts.entity.Task;
import com.cts.repository.TaskRepository;

@RestController
@RequestMapping("/taskapi")
@CrossOrigin("*")
public class TaskRestController {
	
	
	@Autowired
	TaskRepository taskRepository;
	
	/**
	 * This method is used to get all the task list from database
	 * @return
	 */
	@RequestMapping(path="/tasks", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<AllTask>>fetchTasks(){
		
		List<Task> tasks = taskRepository.findTasks();
		System.out.println("Tasks the REST controller");
		AllTask allTask = new AllTask();
		List<AllTask> allTasks = new ArrayList<AllTask>();
		for (Task task : tasks) {
			System.out.println(task);
			System.out.println(task.getParentTask());
			allTask = new AllTask();
			allTask.setTaskId(task.getTaskId());
			allTask.setTask(task.getTask());
			allTask.setStStartDate(getStringdate(task.getStartDate()));
			allTask.setStEndDate(getStringdate(task.getEndDate()));
			allTask.setPriority(task.getPriority());
			allTask.setParentId(task.getParentTask().getParentId());
			allTask.setParentTask(task.getParentTask().getParentTask());
			allTask.setTaskStatus(task.getTaskStatus());
			allTasks.add(allTask);
		}
		
		return new ResponseEntity<List<AllTask>>(allTasks, HttpStatus.OK);
	}
	

	/**
	 * This method id used to fetch a particular task from database 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/task/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<AllTask>fetchTask(@PathVariable("id") int id){
		List<Task> tasks = taskRepository.findTask(id);
		Task task = null;
		if(tasks.size() > 0) {
			task = tasks.get(0);
		}
		System.out.println("Task the REST controller");
		System.out.println(task);

		AllTask allTask = new AllTask();
		allTask.setTaskId(task.getTaskId());
		allTask.setTask(task.getTask());
		
		allTask.setStStartDate(getStringdate(task.getStartDate()));
		allTask.setStEndDate(getStringdate(task.getEndDate()));
		allTask.setPriority(task.getPriority());
		allTask.setParentId(task.getParentTask().getParentId());
		allTask.setParentTask(task.getParentTask().getParentTask());
		allTask.setTaskStatus(task.getTaskStatus());
		return new ResponseEntity<AllTask>(allTask, HttpStatus.OK);
	    // return employees;
	}
	

	/**
	 * This method is used to delete task from database
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id){
		System.out.println("ID recieved : " + id);
		taskRepository.removeTask(id);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}
	

	/**
	 * This method is used to create new task
	 * @param allTask
	 * @return
	 */
	@RequestMapping(value = "/task", method = RequestMethod.POST)
	public ResponseEntity<Void> createTask(@RequestBody AllTask allTask) {
		System.out.println("Creating Task " + allTask);
		
		try{
			List<Task> oldTasks = taskRepository.findById(allTask.getTaskId());
			if(oldTasks.size() > 0){
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			else{
				ParentTask pt = new ParentTask(allTask.getParentTask());
				Task task = new Task(allTask.getTask(), getSqlDate(allTask.getStStartDate()), getSqlDate(allTask.getStEndDate()),
						allTask.getPriority());

				task.setTaskStatus(1);
				task.setParentTask(pt);
				Set<Task> tasks = new HashSet<>();
				tasks.add(task); 
				pt.setTasks(tasks);
				
				taskRepository.addParentTask(pt);
				return new ResponseEntity<Void>(HttpStatus.CREATED);

			}
		}
		catch(Exception nre){
			ParentTask pt = new ParentTask(allTask.getParentTask());
			Task task = new Task(allTask.getTask(), getSqlDate(allTask.getStStartDate()), getSqlDate(allTask.getStEndDate()),
					allTask.getPriority());
			
			task.setParentTask(pt);
			Set<Task> tasks = new HashSet<>();
			tasks.add(task); 
			pt.setTasks(tasks);
			
			taskRepository.addParentTask(pt);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}	
	}

	
	/**
	 * This method is used to convert String date to SQL date
	 * @param stDate
	 * @return
	 */
	public Date getSqlDate(String stDate) {
		//String startDate="12-31-2014";
		java.sql.Date sqlStartDate = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date date;
		boolean isNotParse = false;
		try {
			if(null != stDate) {
				date = sdf1.parse(stDate);
				sqlStartDate = new java.sql.Date(date.getTime());
			}
		} catch (ParseException e) {
			isNotParse = true;
			System.out.println("Error occured while parsing first time--> " + e.getMessage());
		}

		if(isNotParse){
			try{
				SimpleDateFormat sdf11 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date11 = sdf11.parse(stDate);
	
				SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
				String date1 = sdf2.format(date11);
				
	
				java.util.Date date2 = sdf2.parse(date1);
				
				sqlStartDate = new java.sql.Date(date2.getTime());
				System.out.println("Parse diff format--> " + sqlStartDate);
				}
			catch(ParseException e) {
				isNotParse = true;
				e.printStackTrace();
			}
		}
		return sqlStartDate;
	}

	
	/**
	 * This method is used to get String date from SQL date
	 * @param sqlDate
	 * @return
	 */
	public String getStringdate(Date sqlDate) {
		System.out.println("sqlDate-->"+sqlDate);
		String stDate = "";
		if(null != sqlDate) {
			//DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			stDate = df.format(sqlDate); 
		}
		System.out.println("stDate-->"+stDate);
		return stDate;
	}


	/**
	 * This method is used to update a task on the basis of task id
	 * @param allTask
	 * @return
	 */
	@RequestMapping(value = "/task", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateTask(@RequestBody AllTask allTask) {
		System.out.println("Update Task " + allTask);
		
		try{
			ParentTask pt = new ParentTask(allTask.getParentTask());
			pt.setParentId(allTask.getParentId());
			Task task = new Task(allTask.getTask(), getSqlDate(allTask.getStStartDate()), getSqlDate(allTask.getStEndDate()),
					allTask.getPriority());
			task.setTaskId(allTask.getTaskId());
			task.setParentTask(pt);
			
			Set<Task> tasks = new HashSet<>();
			tasks.add(task); 
			pt.setTasks(tasks);
			
			taskRepository.updateTask(pt, allTask.getTaskId());

			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		}
		catch(Exception nre){
			System.out.println("In Catch-- Update");
			
			ParentTask pt = new ParentTask(allTask.getParentTask());
			Task task = new Task(allTask.getTask(), getSqlDate(allTask.getStStartDate()), getSqlDate(allTask.getStEndDate()),
					allTask.getPriority());
			
			task.setParentTask(pt);
			Set<Task> tasks = new HashSet<>();
			tasks.add(task); 
			pt.setTasks(tasks);
			
			taskRepository.addParentTask(pt);
			
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}	
	}
	

	/**
	 * This method is used to update task status to inactive
	 * @param allTask
	 * @return
	 */
	@RequestMapping(value = "/taskStatus/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> updateTaskStatus(@PathVariable("id") int id) {
		System.out.println("Update Task Status to Inactive, Id " + id);
		
		try{
			taskRepository.updateTaskStatus(id);
		}
		catch(Exception nre){
			System.out.println("In Catch-- Update Status to Inactive");
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	
}
