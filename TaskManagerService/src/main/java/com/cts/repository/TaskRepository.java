package com.cts.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.entity.ParentTask;
import com.cts.entity.Task;

@Repository
public class TaskRepository {

	/*@PersistenceContext
	EntityManager em;*/
	static SessionFactory sessionFactory;

	static {
		sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	@Override
	protected void finalize(){
		HibernateUtil.closeSessionFactory();
	}

	public List<Task> findTasks() {
		// Physical connection to perform db operations
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Task> tasks = null;
				
		Query queryResult = session.createNamedQuery("findTasks", Task.class);
		tasks = queryResult.list();
		/*System.out.println(tasks);
		for (int i = 0; i < tasks.size(); i++) {
		   Task task = (Book) tasks.get(i);
			System.out.println("Task-->" + task);
		}*/
		
		tx.commit();
		session.close();
		return tasks;
	}


	public List<Task> findTask(int taskId) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Task> tasks = new ArrayList();
		
		Task task = session.get(Task.class, taskId);
		tasks.add(task);
		
		tx.commit();
		session.close();
		return tasks;
	}
	

	public List<Task> findById(int taskId) {
		// Physical connection to perform db operations
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Task> tasks = new ArrayList();
				
		Query queryResult = session.createNamedQuery("findById", Task.class);
		queryResult.setParameter("taskId", taskId);
		tasks = queryResult.list();
		/*System.out.println(tasks);
		for (int i = 0; i < tasks.size(); i++) {
		   Task task = (Book) tasks.get(i);
			System.out.println("Task-->" + task);
		}*/
		
		tx.commit();
		session.close();
		return tasks;
	}

	
//	@Transactional
	public void removeTask(int taskId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Task task = session.get(Task.class, taskId);
		System.out.println("---->"+task);
		System.out.println("---->"+task.getParentTask());
		/*List<Task> tasks = new ArrayList();
		Task task = null;
		Query queryResult = session.createNamedQuery("findById", Task.class);
		queryResult.setParameter("taskId", taskId);
		tasks = queryResult.list();
		if(tasks.size() > 0) {
		    task = (Task) tasks.get(0);
		}*/
		session.remove(task.getParentTask());
		session.remove(task);
		
		tx.commit();
		session.close();
	}
	

	public void addParentTask(ParentTask pt) {
		// Physical connection to perform db operations
		Session session = sessionFactory.openSession();

		// Manual transactions
		Transaction tx = session.beginTransaction();
		session.save(pt);

		tx.commit();
		session.close();
		
	}
	
	
	public void updateTask(ParentTask newPt, int taskId) {

		System.out.println("Task-->" + taskId);
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		Task task = session.get(Task.class, taskId);
		System.out.println("Old Task-->" + task);

		Set<Task> newTasks = newPt.getTasks();
		ParentTask oldPt = task.getParentTask();

		//oldPt.setParentId(newPt.getParentId());
		oldPt.setParentTask(newPt.getParentTask());
		for(Task newTask : newTasks) {
			task.setTask(newTask.getTask());
			task.setStartDate(newTask.getStartDate());
			task.setEndDate(newTask.getEndDate());
			task.setPriority(newTask.getPriority());
			task.setParentTask(oldPt);
		}
		System.out.println("New Task-->" + task);

		tx.commit();
		session.close();
	}
	

	/*public ParentTask findPTById(int id) {
		TypedQuery<ParentTask> query = em.createNamedQuery("findPTById", ParentTask.class);
		query.setParameter("parentId", id);
		ParentTask parentTask = query.getSingleResult();
		System.out.println(parentTask);
		return parentTask;
	}

	
	
	public  List<ParentTask> findParentTasks() {
		TypedQuery<ParentTask> query = em.createNamedQuery("findParentTasks", ParentTask.class);
		List<ParentTask> pTasks = query.getResultList();
		System.out.println(pTasks);
		return pTasks;
	}


	@Transactional
	public void addTask(Task task) {
		em.persist(task);
	}

	@Transactional
	public void addParentTask(ParentTask pt) {
		em.persist(pt);
	}
	


	@Transactional
	public void updateTask(Task task) {
		Task taskObj = em.find(Task.class, task.getTaskId());
		taskObj.setParentId(task.getParentId());
		taskObj.setTask(task.getTask());
		taskObj.setStartDate(task.getStartDate());
		taskObj.setEndDate(task.getEndDate());
		taskObj.setPriority(task.getPriority());
		System.out.println(taskObj);
	}

	@Transactional
	public void updateParentTask(ParentTask pt) {
		ParentTask pTaskObj = em.find(ParentTask.class, pt.getParentId());
		pTaskObj.setParentTask(pt.getParentTask());
		System.out.println(pTaskObj);
	}

	
	


	@Transactional
	public void removeTask(int id) {
		Task emp = em.find(Task.class, id);
		em.remove(emp);
	}

	@Transactional
	public void removeParentTast(int id) {
		ParentTask emp = em.find(ParentTask.class, id);
		em.remove(emp);
	}
*/



}
