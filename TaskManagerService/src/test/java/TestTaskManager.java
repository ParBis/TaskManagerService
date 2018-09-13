

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		Task task = new Task("Task", getSqlDate("12-12-2012"), getSqlDate("12-12-2016"), 1);
		
		task.setParentTask(pt);
		Set<Task> tasks = new HashSet<>();
		tasks.add(task); 
		
		pt.setTasks(tasks);
		
		repo.addParentTask(pt);
	}

	//@Test
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
			allTasks.add(allTask);
		}
		System.out.println("allTasks-->" + allTasks);
		
	}
	
	//@Test
	public void testRemovingTask() {
		repo.removeTask(2);
	}


	@Test
	public void testUpdateTask() {
		AllTask allTask = new AllTask();
		allTask.setTaskId(2);
		allTask.setTask("TaskNew");
		allTask.setStStartDate("12-12-2013");
		allTask.setStEndDate("12-12-2017");
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
		java.sql.Date sqlStartDate = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
		java.util.Date date;
		try {
			if(null != stDate) {
				date = sdf1.parse(stDate);
				sqlStartDate = new java.sql.Date(date.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqlStartDate;
	}
	

	public String getStringdate(Date sqlDate) {
		String stDate = "";
		
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");  
		stDate = df.format(sqlDate); 
		
		return stDate;
	}
	
	/*@Autowired
	EmployeeRepository repo;
	
	@Test
	public void testInsertingEmployee() {
		Employee emp = new Employee("Arun");
		emp.setSalary(20000d);
		repo.addEmployee(emp);
	
	}
//	@Test
	public void testFindingEmployee() {
		repo.findEmployee(2);
	}
//	@Test
	public void testRemovingEmployee() {
		repo.removeEmployee(2);
	}
	
//	@Test
	public void testUpdatingEmployeeSalary() {
		//repo.updateEmployee(1, 30000d);
	}
//	@Test
	public void testFindingEmployeeByName() {
		Employee emp = repo.findByName("Arun");
		assertEquals("should return one emp with name", emp.getName(), "Arun");
	}
//	@Test
	public void testFindingEmployeesBySalary() {
		List<Employee> employees = repo.findBySalary(25000d);
		assertEquals("should return one emp with name", employees.size(), 1);
	}
	@Test
	public void testFindingEmployees() {
		List<Employee> employees = repo.findEmployees();
//		assertEquals("should return one emp with name", employees.size(), 1);
	}
	
*/
}
