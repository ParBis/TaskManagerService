

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.entity.AllTask;
import com.cts.entity.ParentTask;
import com.cts.entity.Task;
import com.cts.repository.TaskRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class TestTaskManager {
	
	@Autowired
	TaskRepository repo;
	
	//@Test
	public void testInsertingTask() {
		ParentTask pt = new ParentTask("1");
		Task task = new Task("Partha", getSqlDate("12-12-2012"), getSqlDate("12-12-2016"), 1);
		task.setTaskStatus(1);
		task.setParentTask(pt);
		Set<Task> tasks = new HashSet<>();
		tasks.add(task); 
		
		pt.setTasks(tasks);
		
		repo.addParentTask(pt);
	}

	@Test
	public void testFindingTask() {
		List<Task> tasks = repo.findTasks();
		System.out.println("tasks-->" + tasks);
		AllTask allTask = new AllTask();
		List<AllTask> allTasks = new ArrayList<AllTask>();
		for (Task task : tasks) {
			//System.out.println(task);
			//System.out.println(task.getParentTask());
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
		System.out.println("allTasks-->" + allTasks);
		
	}
	
	//@Test
	public void testRemovingTask() {
		repo.removeTask(2);
	}


	//@Test
	public void testUpdateTask() {
		AllTask allTask = new AllTask();
		allTask.setTaskId(20);
		allTask.setTask("TaskNew");
		allTask.setStStartDate("2009-09-10");
		allTask.setStEndDate("2015-09-10");
		allTask.setPriority(2);
		allTask.setParentId(1);
		allTask.setParentTask("ParentTask");
		
		ParentTask pt = new ParentTask(allTask.getParentTask());
		pt.setParentId(allTask.getParentId());
		Task task = new Task(allTask.getTask(), getSqlDate(allTask.getStStartDate()), getSqlDate(allTask.getStEndDate()),
				allTask.getPriority());
		task.setTaskId(allTask.getTaskId());
		task.setParentTask(pt);
		
		Set<Task> tasks = new HashSet<>();
		tasks.add(task); 
		pt.setTasks(tasks);
		System.out.println("Parent Task-->" + pt.getTasks());
		repo.updateTask(pt, allTask.getTaskId());
	}
	

	public Date getSqlDate(String stDate) {
		//String startDate="12-31-2014";
		System.out.println("---------------------------------------" + stDate);
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
			e.printStackTrace();
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
	

	public String getStringdate(Date sqlDate) {
		String stDate = "";

		if(null != sqlDate) {
			//DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			stDate = df.format(sqlDate); 
		} 
		
		return stDate;
	}
	
}
